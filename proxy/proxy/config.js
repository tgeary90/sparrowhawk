
// config.js - config for connections to sparrowhawk web filter

var sparrowhawkGet = {
	host: '127.0.0.1',
	port: 8090,
	path: '/',
	method: 'GET'
};

var subscribe = {
		host: '127.0.0.1',
		port: 8090,
		path: '/subscribers',
		method: 'POST'
	};

module.exports.sparrowhawkGet = sparrowhawkGet;
module.exports.subscribe = subscribe;