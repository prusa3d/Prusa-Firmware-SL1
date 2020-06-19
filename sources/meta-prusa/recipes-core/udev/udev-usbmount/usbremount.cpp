#include <sys/mount.h>
#include <errno.h>
#include <systemd/sd-journal.h>

#include <iostream>
#include <string>
#include <vector>
#include <filesystem>

const std::string USB_MOUNT_ROOT_XXX = "/run/media/root/";
const std::filesystem::path USB_MOUNT_ROOT = std::filesystem::path("/run/media/root");

int main(int argc, char **argv) {
	std::vector<std::string> args(argv, argv + argc);
	if(args.size() != 2) {
		std::cerr << "Pass path as the only argument" << std::endl;
		sd_journal_print(LOG_ERR, "Pass path as the only argument");
		exit(-1);
	}

	auto required = std::filesystem::path(args[1]);

	if(required.string().find(USB_MOUNT_ROOT.string()) != 0) {
		sd_journal_print(LOG_ERR, "Path is not the usb mount path");
		exit(-2);
	}

	// Add first component of the relative path to the USB mount root
	auto mountPoint = USB_MOUNT_ROOT / *std::filesystem::relative(required, USB_MOUNT_ROOT).begin();

	// Just access the path to force automount
	std::filesystem::exists(mountPoint);

	sd_journal_print(LOG_DEBUG, ("Attempting rw remount for path: " + required.string() + " mount: " + mountPoint.string()).c_str());
	if(mount("", mountPoint.c_str(), "", MS_REMOUNT, "rw")) {
		sd_journal_print(LOG_DEBUG, (std::string("Remount rw failed with: ") + strerror(errno)).c_str());
		exit(-3);
	}
}
