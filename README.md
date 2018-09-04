How to build image
--------------------------------------------------------------------------------

git clone git@gitlab.webdev.prusa3d.com:22443/hw/a64/oe
cd oe
./oe-layertool-setup.sh
cd build
source conf/setenv
bitbake sla-image