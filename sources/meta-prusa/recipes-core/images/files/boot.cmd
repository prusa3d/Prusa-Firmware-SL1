#=uEnv
boot_img_md5sum=9a0d65bbf167df684c7582c1fb320b98
root_img_md5sum=8c66b28dd065e6886469aa3c9ab74945
mmc_partition=0:1
env_blkcount=0x400
env_blkoffset=0x400
chip_blkcount=0x734000
bootimg_blkcount=0x800
rootimg_blkcount=0x180000
etcimg_blkcount=0x10000
factory_blkcount=0x20000
var_blkcount=0x3e8000
rootfs0_blkoffset=0x2000
rootfs1_blkoffset=0x182000
etcfs0_blkoffset=0x302000
etcfs1_blkoffset=0x312000
factory_blkoffset=0x322000
var_blkoffset=0x342000

uuid_gpt_system=b921b045-1df0-41c3-af44-4c6f280d3fae
uuid_gpt_other=0fc63daf-8483-4772-8e79-3d69d8477de4
uuid_gpt_environment=bc13c2ff-59e6-4262-a352-b275fd6f7172

# Physical memory area starts at 0x40000000 and is 0x40000000 (1 GB) long.
# U-boot's reserved memory region is pushed to the end of the area at 0x79f5a900.
# Factory (64 MB), etc (32 MB), and U-Boot (1 MB) images total in 97 MB of space.
# Thus, starting at 0x46100000, rootfs image is left with 830 MB.

# Shall it ever require more than that, we have 2 options (that I can think of):
# a) overwrite the smaller images to get the entire 927 MiB block or,
# b) flash it 'per partes'

factoryimg_buf_offset=0x40000000
etcimg_buf_offset=0x44000000
bootimg_buf_offset=0x46000000
rootimg_buf_offset=0x46100000

setenv partitions "name=environment,start=512K,size=512K,type=${uuid_gpt_environment};name=rootfs.0,start=4M,size=768M,type=${uuid_gpt_system};name=rootfs.1,start=772M,size=768M,type=${uuid_gpt_system};name=etc.0,start=1540M,size=32M,type=${uuid_gpt_other};name=etc.1,start=1572M,size=32M,type=${uuid_gpt_other};name=var,start=1668M,size=2000M,type=${uuid_gpt_other};name=factory,start=1604M,size=64M,type=${uuid_gpt_other};"

setenv load_bootimg 'echo "load_bootimg"; fatload mmc ${mmc_partition} ${bootimg_buf_offset} boot.img'
setenv load_rootimg 'echo "load_rootimg"; fatload mmc ${mmc_partition} ${rootimg_buf_offset} root.img'
setenv load_etcimg 'echo "load_etcimg"; fatload mmc ${mmc_partition} ${etcimg_buf_offset} etc.img'
setenv load_factoryimg 'echo "load_factoryimg"; fatload mmc ${mmc_partition} ${factoryimg_buf_offset} factory.img'
setenv write_default_gpt 'echo "write_default_gpt"; gpt write mmc 1 $partitions'
setenv erase_environment 'echo "erase_environment"; mmc dev 1 0; mmc erase ${env_blkoffset} ${env_blkcount}'
setenv write_boot0 'echo "write_boot0"; mmc dev 1 1; mmc write ${bootimg_buf_offset} 0 ${bootimg_blkcount}'
setenv write_boot1 'echo "write_boot1"; mmc dev 1 2; mmc write ${bootimg_buf_offset} 0 ${bootimg_blkcount}'
setenv write_rootfs0 'echo "write_rootfs0"; mmc dev 1 0; mmc write ${rootimg_buf_offset} ${rootfs0_blkoffset} ${rootimg_blkcount}'
setenv write_rootfs1 'echo "write_rootfs1"; mmc dev 1 0; mmc write ${rootimg_buf_offset} ${rootfs1_blkoffset} ${rootimg_blkcount}'

setenv write_etcfs0 'echo "write_etcfs0"; mmc dev 1 0; mmc write ${etcimg_buf_offset} ${etcfs0_blkoffset} ${etcimg_blkcount}'
setenv write_etcfs1 'echo "write_etcfs1"; mmc dev 1 0; mmc write ${etcimg_buf_offset} ${etcfs1_blkoffset} ${etcimg_blkcount}'
setenv write_factory 'echo "write_factory"; mmc dev 1 0; mmc write ${factoryimg_buf_offset} ${factory_blkoffset} ${factory_blkcount}'

setenv erase_var 'echo "erase var"; mmc dev 1 0; mmc erase ${var_blkoffset} ${var_blkcount}'
setenv erase_all 'echo "chip erase"; mmc dev 1; mmc erase 0 ${chip_blkcount}'

setenv setup_bootbus 'echo "setup_bootbus"; mmc bootbus 1 1 0 0'
setenv setup_bootpart 'echo "setup_bootpart"; mmc partconf 1 1 1 0'

echo "START"
run load_bootimg 
run load_etcimg
run load_rootimg
run load_factoryimg
gpio set PE17
run erase_all
run write_default_gpt 
run erase_environment 
run write_boot0 
run write_boot1
run write_etcfs0
run write_etcfs1
run write_factory
run write_rootfs0
run write_rootfs1
run setup_bootbus
run setup_bootpart
echo "DONE"
while true; do
	gpio set PE17;
	sleep 1;
	gpio clear PE17;
	sleep 1;
done;

