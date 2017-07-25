#!/bin/bash

docker run -itd --name local-server -h local-server -v ${PWD}/images/test_node/content:/content --net sparrowhawk_default test-node /bin/bash -c 'cd /content/e2e_tests; node local_server.js'
docker run -it --rm -v ${PWD}/images/test_node/content:/content --net sparrowhawk_default test-node /bin/bash -c 'cd /content/e2e_tests; mocha -R spec fetch_page.js' 


sleep 10

docker kill local-server
docker rm local-server
