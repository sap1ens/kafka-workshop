#!/usr/bin/env bash

set -o errexit
set -o pipefail

docker run \
    -it \
    --rm \
    --name maven-build \
    -v `pwd`:/opt/maven-build \
    -w /opt/maven-build \
    maven:3.5.0-jdk-8 mvn exec:java -Dexec.mainClass="$1"
