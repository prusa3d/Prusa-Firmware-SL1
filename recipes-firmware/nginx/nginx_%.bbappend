FILESEXTRAPATHS_prepend := "${THISDIR}/nginx:"

SRC_URI += "\
	git://github.com/atomx/nginx-http-auth-digest.git;destsuffix=git/digest \
	file://nginx.conf \
"
SRCREV = "cd8641886c873cf543255aeda20d23e4cd603d05"

DIGEST_PATH = "${WORKDIR}/git/digest"

EXTRA_OECONF += " --add-module=${DIGEST_PATH}"
