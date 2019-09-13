#!/bin/sh

# Create CA
openssl genrsa -out ca.key 4096
openssl req -new -x509 -key ca.key -days 36500 -out ca.pem -subj "/"

# Create cers signed with CA
openssl req -new -newkey rsa:4096 -nodes -keyout cert.key -days 36500 -out cert.csr -subj "/"
openssl x509 -req -in cert.csr -CA ca.pem -CAkey ca.key -CAcreateserial -out cert.pem

