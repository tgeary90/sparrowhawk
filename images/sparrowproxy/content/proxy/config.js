
// config.js - config for connections to sparrowhawk web filter

var host = 'sparrowhawk';
//var host = '172.17.0.1';

var getPage = {
	host : host,
	port : 8090,
	path : '/pages/sparrowhawk/',
	method : 'GET'
};

var subscribe = {
	host : host,
	port : 8090,
	path : '/subscribers',
	method : 'POST'
};

var search = {
	host : host,
	port : 8090,
	method : 'POST',
	path : '/pages/sparrowhawk/search',
	headers: {
		'Content-Type': 'application/json'
	}
};

var index = {
	host : host,
	port : 8090,
	path : '/pages/sparrowhawk/index',
	method : 'POST',
	headers: {
		'Content-Type': 'application/json'
	}
};

var filter = {
	host: host,
	port: 8090,
	path: '/pages/sparrowhawk/filter',
	method: 'POST',
	headers: {
		'Content-Type': 'application/json'
	}
};

module.exports.getPage = getPage;
module.exports.subscribe = subscribe;
module.exports.search = search;
module.exports.filter = filter;
module.exports.index = index;