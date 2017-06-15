
// config.js - config for connections to sparrowhawk web filter

var getPage = {
	host : 'sparrowhawk',
	port : 8090,
	path : '/pages/sparrowhawk/',
	method : 'GET'
};

var subscribe = {
	host : 'sparrowhawk',
	port : 8090,
	path : '/subscribers',
	method : 'POST'
};

var search = {
	host : 'sparrowhawk',
	port : 8090,
	path : '/pages/sparrowhawk/search',
	method : 'POST'
};

var index = {
	host : 'sparrowhawk',
	port : 8090,
	path : '/pages/sparrowhawk/index',
	method : 'POST'
};

var filter = {
	host: 'sparrowhawk',
	port: 8090,
	path: '/pages/sparrowhawk/filter',
	method: 'POST'
};

module.exports.getPage = getPage;
module.exports.subscribe = subscribe;
module.exports.search = search;
module.exports.filter = filter;
module.exports.index = index;