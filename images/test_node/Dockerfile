FROM node
MAINTAINER tgeary90

COPY ./content /

RUN  apt-get update \
 && apt-get install -y --force-yes vim tree \
 && rm -rf /var/lib/apt/lists/* \
 && apt-get clean 
RUN cd /acceptance_tests \
 && npm config set registry http://registry.npmjs.org \
 && npm install \
 && npm install -g mocha \
 && npm install chai-http
RUN cd /common_test \
 && npm install chai \
 && npm install chai-http
RUN cd /e2e_tests \
 && npm install 

ENV HOSTNAME=test-node
CMD ["/bin/bash", "-c", "/acceptance_tests/acceptance_tests.sh"]



 
 
