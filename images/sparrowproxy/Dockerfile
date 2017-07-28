FROM node
MAINTAINER tgeary90

COPY ./content /

RUN apt-get update \
 && apt-get install -y --force-yes vim tree \
 && rm -rf /var/lib/apt/lists/* \
 && apt-get clean 

ENV HOSTNAME=sparrowproxy
CMD ["node", "/proxy/sparrowproxy.js", "8091"]



 
 
