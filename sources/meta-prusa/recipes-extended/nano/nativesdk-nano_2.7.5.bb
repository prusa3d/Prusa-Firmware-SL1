include nano.inc
inherit nativesdk

EXTRA_OECONF = "\
        --enable-utf8 \
        --enable-tiny \
        --enable-nanorc \
        --enable-color \
        --disable-justify \
        --enable-help \
        --disable-browser \
        --disable-mouse \
        --enable-speller \
        --enable-tabcomp \
        --enable-wordcomp \
        --disable-wrapping \
        --disable-wrapping-as-root \
        --enable-libmagic \
"

SRC_URI[md5sum] = "002703e368e07882f75e304c8860d83d"
SRC_URI[sha256sum] = "a64d24e6bc4fc448376d038f9a755af77f8e748c9051b6f45bf85e783a7e67e4"


