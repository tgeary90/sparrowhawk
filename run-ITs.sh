#!/bin/bash

cd images/sparrowhawk/content
mvn clean verify -Des.tcp.port=9301 -Des.http.port=9201 -Des.dockerWait=5000
