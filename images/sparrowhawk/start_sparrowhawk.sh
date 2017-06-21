#!/bin/bash

set -eu

function instructions() {
	echo "$1 <dev|prod> <version>"
	echo "eg. $1 dev 0.0.1"
}
echo "args: $@"

env=$1
version=$2

if [[ $env != "dev" && $env != "prod" ]] || [[ $# -ne 2 ]]; then
	instructions $0
	exit 1
fi

if [ "$env" = "prod" ]; then
	exec -a sparrowhawk java -jar sparrowhawk-${version}-SNAPSHOT.jar \
	 --spring.config.location="file:./webService.properties"
elif [ "$env" = "dev" ]; then
	exec -a sparrowhawk java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=9080,suspend=n \
	 -jar sparrowhawk-${version}-SNAPSHOT.jar \
	 --spring.config.location="file:./webService.properties"
else
	instructions $0; exit 1
fi
