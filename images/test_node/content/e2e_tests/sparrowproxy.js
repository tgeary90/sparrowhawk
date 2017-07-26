var http = require('http');
var url = require('url');
var sparrowhawk = require('./config');

var href;

http.createServer(function (req, res) {
	
	var targetUrl = url.parse(req.url); 
	rawHost = req.headers["host"];
	host = rawHost.substring(0, rawHost.search(/:/));
	port = rawHost.substring(rawHost.search(/:/) + 1);
	
//	console.log("Request from: " + req.connection.remoteAddress);
//	console.log("host: " + host);
	console.log("path: " + targetUrl.pathname);
//	console.log("port: " + port);
	console.log(req.headers);
	
	href = 'http://' + host + targetUrl.pathname;
	// fully populate the target URL (href)
	var isFullUrl = /\//.test(href);
	if ( ! isFullUrl) {
		href += '/';
	}
	href = href.replace(/\/$/, '/index.html');
	console.log('href: ' + href);
	
	
	http.get(href, function (originRes) {
		
		console.log("received from origin: " + originRes.statusCode);
		originRes.pipe(res);
		console.log("piped back");
	});
}).listen(8091);
console.log("Sparrowproxy, listening on 8091...");