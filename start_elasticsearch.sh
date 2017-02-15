#!/bin/bash

# simple boot script for elasticsearch 
# primarily for use with sparrowhawk

if ! `docker ps|grep -q es`; then
  	# elastic search 5 usess mmapfs to store indices. needs to be increased.
	sudo sysctl -w vm.max_map_count=262144
	echo "docker is not running"
	if `docker ps -a|grep -q es`; then
		echo "docker elasticsearch container exists, starting..."
		docker start es
	else
		echo -e "docker container does not exist\n"
		docker run -d --name es -p 9200:9200 -p 9300:9300 \
		 -v /home/tomg/es/data:/usr/share/elasticsearch/data \
		 sparrow/es -Ecluster.name="sparrowhawk" -Enode.name="es1"
	fi
else
	echo es is running
fi


if ! `docker ps|grep -q kib`; then
	echo kibana is running
	if `docker ps -a|grep -q kib`; then
		echo "docker kibana container exists, starting..."
		docker start kib
	else
		echo -e "docker kibana container does not exist \n"
		docker run --name kib -e ELASTICSEARCH_URL=http://es:9200 -p 5601:5601 \
		 --link es:elasticsearch -d kibana:5.1
	fi	
else
	echo kib is running
fi 

