#!/bin/sh

cp cert.pem ../sources/meta-prusa/recipes-core/rauc/files/ca.cert.pem
cp cert.key ../sources/meta-prusa/recipes-core/rauc/files/ca.key.pem
cp cert.pem ../sources/meta-prusa/files/ca.cert.pem
cp cert.key ../sources/meta-prusa/files/ca.key.pem
cp ca.pem ../sources/meta-prusa/files/dev-ca.pem

