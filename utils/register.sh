#!/bin/bash

#'{"id":"9559346a-8f69-4b7c-8879-2d983dec015a","name":"jeromes-food-ltd","license":"CUSTOMER"}';

if [ $# -ne 2 ]; then
    echo "$0 <name> <CUSTOMER|TRIAL>"; exit 1
fi

uuid=$(cat /proc/sys/kernel/random/uuid)
name=$1
license=$2

curl -XPOST -H 'Content-Type: application/json' localhost:8090/subscribers \
-d "{\"id\":\"$uuid\",\"name\":\"$name\",\"license\":\"$license\"}" 
