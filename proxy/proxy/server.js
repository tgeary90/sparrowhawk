
// server.js - sparrow-proxy server module

var http = require('http');
var url = require('url');
var config = require('./config');


var server = http.createServer(function (req, res) {

	var myUrl = url.parse(req.url);
	var target = myUrl.pathname;
	
	console.log('Request for URL received: ' + target);
	
	// Do we have this page?
	var docId;
	var sparrowReq = http.request(config.search, function (sparrowRes) {
		console.log("Requesting page from sparrowhawk: "+ target);
		
		sparrowRes.on('data', function (data) {
			docId += data;
		});
		sparrowRes.on('end', function () {
			console.log("docId: " + docId);
		});
	});
	
	var webpage = {
		'url': myUrl,
		'html': ""
	};
	sparrowReq.write(JSON.stringify(webpage));
	sparrowReq.end();
	
	// if no, we need to fetch it from origin (or 404)
	
	// and index it
	
	// INVARIANT - we have a page
	
	// filter the page
}).listen(8091);
console.log('sparrow-proxy listening on 8091...');