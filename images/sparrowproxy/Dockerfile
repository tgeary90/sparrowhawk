FROM node
MAINTAINER tgeary90

COPY ./content /

RUN apt-get update \
 && apt-get install -y --force-yes vim tree \
 && rm -rf /var/lib/apt/lists/* \
 && apt-get clean \

ENV HOSTNAME=sparrowproxy
ENTRYPOINT ["node", "/proxy/proxy.js"]
CMD ["8091"]



 
 
