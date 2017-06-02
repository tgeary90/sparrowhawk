#!/bin/bash

cd $HOME/src/sparrowhawk/acceptance-tests
docker run -it -v $HOME/src/sparrowhawk/acceptance-tests:/tests test_node:1 mocha -R spec /tests/register_subscriber.js
