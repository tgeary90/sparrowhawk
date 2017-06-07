
// config.js - config for connections to sparrowhawk web filter

var getPage = {
	host : '127.0.0.1',
	port : 8090,
	path : '/pages/sparrowhawk/',
	method : 'GET'
};

var subscribe = {
	host : '127.0.0.1',
	port : 8090,
	path : '/subscribers',
	method : 'POST'
};

var search = {
	host : '127.0.0.1',
	port : 8090,
	path : '/pages/sparrowhawk/search',
	method : 'POST'
};

module.exports.getPage = getPage;
module.exports.subscribe = subscribe;
module.exports.search = search;