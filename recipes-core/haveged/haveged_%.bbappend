do_install_append() {
    # Fix data size, not properly deteted on our CPU
    if ${@bb.utils.contains('DISTRO_FEATURES', 'systemd', 'true', 'false', d)}; then
        sed -i '/ExecStart/s/$/ --data=16/' ${D}${systemd_system_unitdir}/haveged.service
    fi
}
