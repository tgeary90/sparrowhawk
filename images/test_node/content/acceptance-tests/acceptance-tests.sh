#!/bin/bash

mocha -R spec /acceptance-tests/subscription_tests.js
mocha -R spec /acceptance-tests/page_tests.js
