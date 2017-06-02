
#!/bin/bash

# simple shutdown script for elasticsearch 
# primarily for use with sparrowhawk

stop_mode=$1

if ! `docker ps|grep -q sparrowsearch`; then
	echo "sparrowsearch is not running"; exit 0
elif [ ! -z $stop_mode ] && [ $stop_mode = "hard" ]; then
	docker kill sparrowsearch
	docker rm sparrowsearch
	docker kill kib
	docker rm kib
else
	echo "sparrowsearch is running"
	docker stop sparrowsearch
	docker stop kib
fi
