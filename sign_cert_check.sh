#!/bin/bash

# Bail on the first occurence of an uncaught error
set -e

if ! p11tool --export "$RAUC_CERT_FILE" --provider "$RAUC_PKCS11_MODULE" | openssl x509 -checkend $(($CERT_VALID_DAYS*86400)) -noout 1>/dev/null; then
    echo "ERROR: Signing certificate will expire in less than $CERT_VALID_DAYS days."
    exit 1
fi

exit 0