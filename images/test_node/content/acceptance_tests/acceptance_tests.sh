#!/bin/bash

cd /acceptance_tests
mocha -R spec subscription_tests.js
mocha -R spec page_tests.js
