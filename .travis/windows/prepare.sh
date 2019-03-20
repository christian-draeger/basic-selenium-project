#!/bin/bash
set -e

export JAVA_HOME=${JAVA_HOME:-/c/basic-selenium-project_opt/jdk}
export PATH=${JAVA_HOME}/bin

rm -rf /c/basic-selenium-project_opt
mkdir -p /c/basic-selenium-project_opt