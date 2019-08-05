#include <stdio.h>
#include <stdint.h>
#include <unistd.h>
#include <fcntl.h>

int main() {
	uint8_t c = 0;
	uint16_t sum = 0;
	for (int i = 0; i < 0x7f; i++) {
		read(0, (void*)&c, 1);
		sum += c;
	}
	uint8_t result = 0x100 - (sum & 0xff);
	printf("%x\n", result);
	return 0;
}
