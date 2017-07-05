#!/bin/bash

#!/bin/bash

docker run -it -v ${PWD}/images/test_node/content:/content --net sparrowhawk_default test-node /bin/bash -c 'cd /content/acceptance_tests && ./acceptance_tests.sh' 
