#!/bin/bash

mvn -f ./core/pom.xml install
mvn -f ./zxcommons/pom.xml install
mvn -DskipTests -f ./RSyntaxTextArea/pom.xml install
mvn -f ./microsha/pom.xml package
mvn -f ./zxspectrum/pom.xml package
cd ./ide
bash ./build-all.sh