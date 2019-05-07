#!/usr/bin/env bash

set -o errexit
set -o pipefail

docker run \
    -it \
    --rm \
    -v `pwd`:/opt/maven-runner \
    -w /opt/maven-runner \
    maven:3.5.0-jdk-8 mvn exec:java -Dexec.mainClass="$1"
