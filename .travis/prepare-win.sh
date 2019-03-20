#!/bin/bash

choco install zip
choco install unzip
choco install ie11
curl -s http://get.sdkman.io | bash
source "$HOME/.sdkman/bin/sdkman-init.sh"
sdk install java 8.0.202-zulu

java -version

./gradlew clean test -Dbrowser=ie
