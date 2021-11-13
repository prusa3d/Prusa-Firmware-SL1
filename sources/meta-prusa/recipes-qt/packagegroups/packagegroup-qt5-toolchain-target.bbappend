RDEPENDS:${PN}:remove += " \
    qttranslations-qt \
"

#remove qtwebkit
RDEPENDS:${PN}:remove = "qtwebkit-dev qtwebkit-mkspecs qtwebkit-qmlplugins"
RDEPENDS:${PN}:remove = "qtcharts-dev qtcharts-mkspecs qtcharts-qmlplugins"
RDEPENDS:${PN}:remove = "qttools-dev qttools-mkspecs qttools-staticdev"

