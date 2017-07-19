// http post client
// search endpoint

var options = {
		host: 'sparrowhawk',
		port: 8090,
		method: 'POST',
		path : '/pages/sparrowhawk/search',
		headers: {
			'Content-Type': 'application/json'
		}
}
var bbcpage = '{"url": "www.bbc.co.uk", "html":""}';
var http = require('http');
var req = http.request(options, function (res) {
		res.on('data', function (chunk) {
			console.log(chunk.toString());
		});
});

req.on('error', function(e) {
	console.log('req fail' + e.message);
});

req.write(bbcpage);
req.end();