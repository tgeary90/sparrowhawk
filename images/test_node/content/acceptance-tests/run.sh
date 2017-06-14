#!/bin/bash

cd $HOME/src/sparrowhawk/acceptance-tests
docker run -it -v $HOME/src/sparrowhawk/acceptance-tests:/tests --link sparrowsearch --rm test_node:1 /tests/acceptance-tests.sh
