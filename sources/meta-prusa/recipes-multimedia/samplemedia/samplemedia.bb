LICENSE = "CLOSED"

SRC_URI = " \
	http://www.prusa3d.com/downloads/others/press_prusament-sla.zip;name=sla \
	http://10.24.10.12/media-test-video/Original_Prusa_i3_MK3_guide_for_a_new_user.short.mp4;name=guide \
"
SRC_URI[sla.md5sum] = "4f78aa858b09a04e470b3ec6b67c733e"
SRC_URI[sla.sha256sum] = "9f976f61bbd4e12fb33e9509248cc9987da5d7908e7bf4a8a85cf793f7dd0361"
SRC_URI[guide.md5sum] = "afa6d0fe9f79d1fd2f249b83a1decdcb"
SRC_URI[guide.sha256sum] = "6bcf9d8ea8d25153ed1565373735ace0eb962b3f0e1c44781f53741b187fb860"


FILES_${PN} = "\
	${datadir}/media/ \
"

do_install() {
	install -d ${D}${datadir}/media
	
	install "${WORKDIR}/Original Prusa SL1/Main.00_03_49_19.Still005.jpg" ${D}${datadir}/media/
	install "${WORKDIR}/Original Prusa SL1/Main.00_05_34_10.Still010.jpg" ${D}${datadir}/media/
	install "${WORKDIR}/Original Prusa SL1/Main.00_07_36_00.Still014.jpg" ${D}${datadir}/media/
	install "${WORKDIR}/Original Prusa SL1/Main.00_08_47_15.Still016.jpg" ${D}${datadir}/media/
	install "${WORKDIR}/Original Prusa SL1/Main.00_09_23_12.Still018.jpg" ${D}${datadir}/media/
	install "${WORKDIR}/Original Prusa SL1/P1155307.jpg" ${D}${datadir}/media/
	install "${WORKDIR}/Original Prusa SL1/P1155308.jpg" ${D}${datadir}/media/
	install "${WORKDIR}/Original Prusa SL1/P1155310.jpg" ${D}${datadir}/media/
	install "${WORKDIR}/Original Prusa SL1/P1155315.jpg" ${D}${datadir}/media/
	install "${WORKDIR}/Original Prusa SL1/P1155323.jpg" ${D}${datadir}/media/
	install "${WORKDIR}/Original Prusa SL1/P1155325.jpg" ${D}${datadir}/media/
	install "${WORKDIR}/Original Prusa SL1/P1155399-Recovered.jpg" ${D}${datadir}/media/
	install "${WORKDIR}/Original Prusa SL1/SLA.png" ${D}${datadir}/media/
	
	install "${WORKDIR}/Original_Prusa_i3_MK3_guide_for_a_new_user.short.mp4" ${D}${datadir}/media/
}
