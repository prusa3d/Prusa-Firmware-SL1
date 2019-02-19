#include <iostream>
#include <fstream>
#include <gpiod.h>
#include <unistd.h>

/*
 * This is a simple program using a gpiod library to wait for power panic signal.
 * Once the signal is detected the actionas are performed to minimize damage to
 * the system.
 */

// Power panic GPIO specification
static const int PORT_WIDTH = 32;
static const int PORT = 6;
static const int PIN = 8;
static const int DEBUG_PORT = 4;
static const int DEBUG_PIN = 5;


int main() {
	std::cout << "Initializing panic GPIO" << std::endl;

	// Get GPIO chip
	gpiod_chip *chip = gpiod_chip_open("/dev/gpiochip1");
	if(!chip) {
		std::cerr << "Failed to open GPIO chip" << std::endl;
		exit(-1);
	}
	
	// Get event GPIO Line
	gpiod_line *event_line = gpiod_chip_get_line(chip, PORT * PORT_WIDTH + PIN);
	if(!event_line) {
		std::cerr << "Failed to get GPIO event line" << std::endl;
		gpiod_chip_close(chip);
		exit(-1);
	}
	
	// Get debug GPIO Line
	gpiod_line *debug_line = gpiod_chip_get_line(chip, DEBUG_PORT * PORT_WIDTH + DEBUG_PIN);
	if(!debug_line) {
		std::cerr << "Failed to get GPIO debug line" << std::endl;
		gpiod_chip_close(chip);
		exit(-1);
	}

	// Configure event line as event
	if(gpiod_line_request_falling_edge_events(event_line, "power-panic-react")) {
		std::cerr << "Failed to request falling edge on event line" << std::endl;
		gpiod_chip_close(chip);
		exit(-3);
	}
	
	// Configure debug line as out
	if(gpiod_line_request_output(debug_line, "power-panic-react", 0)) {
		std::cerr << "Failed to request output on debug line" << std::endl;
		gpiod_chip_close(chip);
		exit(-3);
	}
	
	
	// Wait for event
	struct timespec ts = { 1, 0 };
	struct gpiod_line_event event;
	
	std::cout << "Waiting for panic" << std::endl;
	int wait_ret = 0;
	do {
		wait_ret = gpiod_line_event_wait(event_line, &ts);
	} while(wait_ret <= 0);
	
	// Fire response actions
	gpiod_line_set_value(debug_line, 1);
	std::cout << "PANIC! PANIC! PANIC! Power is running out" << std::endl;
	
	std::cout << "Trigering emergency system poweroff (o)" << std::endl;
	std::ofstream sysrq ("/proc/sysrq-trigger");
	sysrq << "o";
	sysrq.close();
	
	// Print something as long as possible
	int cnt = 0;
	while(true) {
		usleep(10000);
		cnt += 10;
		std::cout << "T+" << cnt << "ms" << std::endl;
	}

	// This point should not be reached. For sake of completness let's deinitilized GPIO
	gpiod_line_release(event_line);
	gpiod_line_release(debug_line);
	gpiod_chip_close(chip);

	return 0;
}
