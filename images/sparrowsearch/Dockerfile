FROM elasticsearch:5.1
MAINTAINER tgeary90

RUN apt-get update && \
 apt-get upgrade -y

ENV HOSTNAME=sparrowsearch
CMD ["elasticsearch", "-Ecluster.name=sparrowhawk", "-Enode.name=sparrowsearch"]
