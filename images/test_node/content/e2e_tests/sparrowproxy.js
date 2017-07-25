var http = require('http');
var url = require('url');
var sparrowhawk = require('./config');

var targetUrl

http.createServer(function (req, res) {
	
	targetUrl = url.parse(req.url); 
	console.log("Request " + req.url + " from origin");
	
	//res.writeHead(200, {'Content-Type': 'text/html'});
	http.get('http://local-server:808/index.html', function (originRes) {
		
		console.log("received from origin: " + originRes.statusCode);
		originRes.pipe(res);
		console.log("piped back");
	});
}).listen(8091);
console.log("Sparrowproxy, listening on 8091...");