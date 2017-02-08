
#!/bin/bash

# simple shutdown script for elasticsearch 
# primarily for use with sparrowhawk

stop_mode=$1

if ! `docker ps|grep -q es`; then
	echo "es is not running"; exit 0
elif [ ! -z $stop_mode ] && [ $stop_mode = "hard" ]; then
	docker kill es
	docker rm es
	docker kill kib
	docker rm kib
else
	echo "es is running"
	docker stop es
	docker stop kib
fi
