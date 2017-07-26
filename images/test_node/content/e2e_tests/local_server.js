// local-server.js serves up resources for end-end test

var fs = require('fs');
var http = require('http');
var url = require('url');

http.createServer(function (req, res) {
	console.log("Request received: " + req.url);
	var parsedUrl = url.parse(req.url, true);
	var resource = parsedUrl.path;
	resource = resource.substring(1);
	if (/index/.test(req.url)) {
		res.writeHead(200, { 'Content-Type': 'text/html' });
		console.log("fetching: " + resource);
		var file = fs.createReadStream(resource);
		file.pipe(res);
	}
}).listen(80);

console.log("local-server: listening on port 80...");