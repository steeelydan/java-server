#!/bin/bash

function log(){
  echo [status] $(date +%x-%X) $@
}

log install
mvn clean install || exit 1
log copy deps
mvn dependency:copy-dependencies || exit 1
log ready
inotifywait $(find ./src/main/java -type f) -m -e close_write | while read file op; do
    log compiling $file
    # $JAVA_HOME/bin/javac -g -cp target/classes/:target/dependency/* -d target/classes $file && log ok
    mvn exec:java -Dexec.mainClass=com.steeelydan.server.Server
done