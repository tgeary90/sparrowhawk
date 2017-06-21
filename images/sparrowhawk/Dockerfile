FROM sdorra/oracle-java-8
MAINTAINER tgeary90

COPY content/target/sparrowhawk-0.0.1-SNAPSHOT.jar /
COPY *.sh /
COPY content/src/main/resources/webService.properties /

RUN apt-get update && \
 apt-get upgrade -y && \
 apt-get install -y procps

ENV HOSTNAME=sparrowhawk

EXPOSE 8090

CMD ["/bin/bash", "/start_sparrowhawk.sh", "prod", "0.0.1"]
