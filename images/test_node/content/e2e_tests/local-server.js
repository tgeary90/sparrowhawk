// local-server.js serves up resources for end-end test

var fs = require('fs');
var http = require('http');
var url = require('url');

var server = http.createServer(function (req, res) {
	var parsedUrl = url.parse(req.url, true);
	var resource = parsedUrl.pathname;
	if (/bbc/.test(req.url)) {
		res.writeHead(200, { 'Content-Type': 'application/json' });
		console.log("fetching: " + resource);
		var file = fs.createReadStream(resource);
		file.pipe(res);
	}
})

module.exports.server = server;