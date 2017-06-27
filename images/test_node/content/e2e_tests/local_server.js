// local-server.js serves up resources for end-end test

var fs = require('fs');
var http = require('http');
var url = require('url');

http.createServer(function (req, res) {
	var parsedUrl = url.parse(req.url, true);
	var resource = parsedUrl.pathname;
	if (/bbc/.test(req.url)) {
		res.writeHead(200, { 'Content-Type': 'application/json' });
		if (resource === '') {
			resource = "index.html";
		}
		console.log("fetching: " + resource);
		var file = fs.createReadStream(resource);
		file.pipe(res);
	}
}).listen(80);

console.log("local_server: listening on port 80...");