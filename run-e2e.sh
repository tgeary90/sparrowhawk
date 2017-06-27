#!/bin/bash

docker run -it --rm -v ${PWD}/images/test_node/content/e2e_tests:/e2e_tests --net sparrowhawk_default test-node /bin/bash -c 'cd /e2e_tests; node local_server.js'
docker run -it --rm -v ${PWD}/images/test_node/content/e2e_tests:/e2e_tests --net sparrowhawk_default test-node /bin/bash -c 'cd /e2e_tests; mocha -R spec fetch_page.js' 
