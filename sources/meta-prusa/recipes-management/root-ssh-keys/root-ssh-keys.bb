SUMMARY = "Deploy development root ssh keys"

LICENSE = "CLOSED"

SRC_URI = "file://authorized_keys"

PACKAGES = "${PN}"
FILES_${PN} = "/home/root/.ssh/authorized_keys"
 
do_install () {
	install --owner=root --group=root --mode=0700 --directory ${D}/home/root/.ssh
	install --owner=root --group=root --mode=0600 ${WORKDIR}/authorized_keys ${D}/home/root/.ssh/authorized_keys
}
