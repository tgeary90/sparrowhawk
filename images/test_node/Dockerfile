FROM node
MAINTAINER tgeary90

COPY ./content /

RUN npm install -g mocha \
 && apt-get update \
 && apt-get install -y --force-yes vim tree \
 && rm -rf /var/lib/apt/lists/* \
 && apt-get clean \
 && cd /acceptance-tests && npm install \
 && npm install chai-http

ENV HOSTNAME=test_node
CMD ["/acceptance-tests/acceptance-tests.sh"]



 
 
