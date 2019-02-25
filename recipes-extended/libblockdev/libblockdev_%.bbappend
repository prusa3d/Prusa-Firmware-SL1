#PACKAGECONFIG_remove = "nvdimm python3 lvm-dbus lvm dm dmraid crypto kmod escrow btrfs kbd mpath"

#PACKAGECONFIG[python2] = "--with-python2, --without-python2,,python"
PACKAGECONFIG = "fs parted" 
#fuck u(disks) 
PACKAGECONFIG += "mdraid crypto " 

DEPENDS += "kmod udev"

