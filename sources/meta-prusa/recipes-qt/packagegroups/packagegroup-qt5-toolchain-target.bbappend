RDEPENDS_${PN}_remove += " \
    qttranslations-qt \
"

#remove qtwebkit
RDEPENDS_${PN}_remove = "qtwebkit-dev qtwebkit-mkspecs qtwebkit-qmlplugins"
RDEPENDS_${PN}_remove = "qtcharts-dev qtcharts-mkspecs qtcharts-qmlplugins"
RDEPENDS_${PN}_remove = "qttools-dev qttools-mkspecs qttools-staticdev"

