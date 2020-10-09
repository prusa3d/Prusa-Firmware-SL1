#!/bin/sh

# Bail on the first occurence of an uncaught error
set -e

# Run the command relative to this script location
BASE=$(dirname $0)

VERSION=$(git -C ${BASE} describe --abbrev=0)

# Branch is either current branch name or CI branch name if defined
# CI uses custom branches to run the build, we do not want to show names of those branches
if [ -z "${CI_COMMIT_REF_NAME}" ]; then
	BRANCH=$(git -C ${BASE} rev-parse --abbrev-ref HEAD)
else
	BRANCH=${CI_COMMIT_REF_NAME}
fi

COMMITS_FROM_VERSION=$(git -C ${BASE} log --oneline ${VERSION}..HEAD|wc -l)
SHORT_HASH=$(git -C ${BASE} rev-parse --short HEAD)
if [ -z "$(git -C ${BASE} status --porcelain)" ]; then DIRTY=""; else DIRTY="-dirty"; fi

case "$VERSION" in
	*-*)
		VERSION_SEP=.;;
	*)
		VERSION_SEP=-;;
esac

(
	if [ "$COMMITS_FROM_VERSION" = "0" ]; then
		echo ${VERSION}${DIRTY}
	else
		echo ${VERSION}${VERSION_SEP}${BRANCH}.${COMMITS_FROM_VERSION}+${SHORT_HASH}${DIRTY}
	fi
) | sed "s/[^a-zA-Z0-9\.+-]/-/g"
