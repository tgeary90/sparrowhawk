#!/bin/bash

cd $HOME/src/sparrowhawk/acceptance-tests
if [ ! -d node_modules ]; then 
    npm install
fi
docker run -it -v $HOME/src/sparrowhawk/acceptance-tests:/tests test_node:1 mocha -R spec /tests/register_subscriber.js
