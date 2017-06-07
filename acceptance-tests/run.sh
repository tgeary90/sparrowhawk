#!/bin/bash

cd $HOME/src/sparrowhawk/acceptance-tests
docker run -it -v $HOME/src/sparrowhawk/acceptance-tests:/tests --rm test_node:1 mocha /tests/register_subscriber.js
#docker run -it -v $HOME/src/sparrowhawk/acceptance-tests:/tests --rm test_node:1 mocha /tests/index_page.js
