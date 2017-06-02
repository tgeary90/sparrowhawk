
// server.js - sparrow-proxy server module

var http = require('http');
var url = require('url');
var config = require('./config');


var server = http.createServer(function (req, res) {
	
	var endpoint = url.parse(req.url).pathname;
	
	// Do we have this page?
	var docId;
	var sparrowReq = http.request(config.sparrowhawkGet, function (sparrowRes) {
		console.log("Requesting page from sparrowhawk "+ endpoint);
		
		sparrowRes.on('data', function (data) {
			docId += data;
		});
		sparrowRes.on('end', function () {
			console("docId: " + docId);
		});
		
	});

	// if no, we need to fetch it from origin (or 404)
	
	// and index it
	
	// INVARIANT - we have a page
	
	// filter the page
}).listen(8091);
console.log('sparrow-proxy listening on 8091...');