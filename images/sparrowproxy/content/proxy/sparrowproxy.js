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
	
	var webpage = {
		'url': href,
		'html': ''
	};
	
	isPageInElasticsearch(webpage);
	
	// get the page from origin
	http.get(href, function (originRes) {
		
		console.log("received from origin: " + originRes.statusCode);
		originRes.pipe(res);
		console.log("piped back");
	});
}).listen(process.argv[2]);
console.log("Sparrowproxy, listening on " + process.argv[2]);


function isPageInElasticsearch(webpage) {
	
	var webpageText = JSON.stringify(webpage);
	var docId = '';
	
//	sparrowhawk.search.headers = { 
//		'Content-Type': 'application/json',
//		'Content-Length': webpageText.length
//	};
	
	console.log("sending request to endpoint: " + sparrowhawk.search.path);
	console.log("page: " + webpageText);
	
	var searchReq = http.request(sparrowhawk.search, function (searchRes) {
		
		searchRes.on('data', function (chunk) {
			docId += chunk;
		});
		searchRes.on('end', function (err) {
			if (err) {
				console.log("Error received from search endpoint");
				return;
			}
			console.log("docId: " + docId);

			// if no, we need to fetch it from origin (or 404)
			if (docId === 'miss') {
				indexPage(webpage);
				//console.log('miss');
			}
			else if (/\w+/.test(docId)) {
				//filterPage(webpage);
				console.log('hit');
			}
		});
	});
	
	searchReq.on('error', function (e) {
		console.log('problem with request to search endpoint: ' + e);
		console.log('msg: ' + e.message);
	});
	searchReq.write(webpageText);
	searchReq.end();
}

function indexPage(webpage) {

	var uri = webpage.url;
	var page;
	
	// get page from origin
	
	http.get(uri, function (getRes) {
		console.log("retrieving page from origin...");
		
		getRes.on('data', function (chunk) {
			page += chunk;
		});
		
		getRes.on('end', function () {
			webpage.html = page;
			console.log('finished building page');
		
			// index page into ES
			
			var webpageText = JSON.stringify(webpage);
			//console.log("webpageText: " + webpageText);
			
			var indexReq = http.request(sparrowhawk.index, function (indexRes) {
				indexRes.on('end', function () {
					console.log("debug: invoking filter page");
					//filterPage(webpage);
				});
			});
			console.log('Sending page '+ webpageText.length +' to elasticsearch...');
			indexReq.write(webpageText);
			indexReq.end();
			console.log('Finished sending page')
		});
	});
}