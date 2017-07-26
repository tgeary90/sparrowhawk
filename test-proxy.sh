#!/bin/bash

docker run -itd --name local-server  -h local-server -v ${PWD}/images/test_node/content:/content test-node /bin/bash -c 'cd /content/e2e_tests; node local_server.js'
docker run -it --name proxy-test -h proxy-test --link local-server -v ${PWD}/images/test_node/content:/content test-node /bin/bash -c 'node /content/e2e_tests/sparrowproxy.js'

docker kill local-server
docker rm local-server
docker kill proxy-test
docker rm proxy-test
