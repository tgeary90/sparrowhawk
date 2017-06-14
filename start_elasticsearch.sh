#!/bin/bash

# simple boot script for elasticsearch 
# primarily for use with sparrowhawk

if ! `docker ps|grep -q 'sparrowsearch'`; then
  	# elastic search 5 uses mmapfs to store indices. needs to be increased.
	sudo sysctl -w vm.max_map_count=262144
	echo "elasticsearch is not running"
	if `docker ps -a|grep -q 'sparrowsearch'`; then
		echo "docker 'sparrowsearch' container exists, starting..."
		docker start sparrowsearch
	else
		echo -e "docker 'sparrowsearch' container does not exist\n"
		docker run -d --name sparrowsearch -p 9200:9200 -p 9300:9300 \
		 -v /home/tomg/es/data:/usr/share/elasticsearch/data \
		 sparrow/es -Ecluster.name="sparrowhawk" 
	fi
else
	echo sparrowsearch is running
fi


if ! `docker ps|grep -q kib`; then
	echo kibana is not running
	if `docker ps -a|grep -q kib`; then
		echo "docker kibana container exists, starting..."
		docker start kib
	else
		echo -e "docker kibana container does not exist \n"
		docker run --name kib -e ELASTICSEARCH_URL=http://sparrowsearch:9200 -p 5601:5601 \
		 --link sparrowsearch:elasticsearch -d kibana:5.1
	fi	
else
	echo kib is running
fi 

