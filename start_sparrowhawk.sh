#!/bin/bash

exec -a sparrowhawk java -Xdebug -Xrunjdwp:transport=dt_socket,server=y,address=9080,suspend=n \
 -jar target/sparrowhawk-0.0.1-SNAPSHOT.jar \
 --spring.config.location=classpath:/webService.properties,classpath:/override.properties &
