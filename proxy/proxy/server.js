
// server.js - sparrow-proxy server module

var http = require('http');
var url = require('url');
var sparrowhawk = require('./config');


var proxy = http.createServer(function (req, res) {

	var requestUrl = url.parse(req.url);
	
	console.log("hostname: " + requestUrl.hostname);	
	console.log("pathname: " + requestUrl.pathname);
	console.log("port: " + requestUrl.port);
	console.log("path: " + requestUrl.path);
	console.log("method: " + req.method);
	for (var hdr in req.headers) {
		console.log("\t" + hdr + ": " + req.headers[hdr]);
	}
	
	// DEBUG
	//process.exit(1);
	
	var target = requestUrl.hostname + requestUrl.path;
	console.log('Request for URL received: ' + target);
	
	var docId;
	var webpage = {
			'url': target,
			'html': ""
	};
	var page;
	
	isPageInElasticsearch(webpage);
	
}).listen(process.argv[2]);
console.log('sparrow-proxy listening on ' + process.argv[2] + '...');


/**
 * @param webpage
 */
function isPageInElasticsearch(webpage) {
	var sparrowReq = http.request(sparrowhawk.search, function (err, sparrowRes) {
		if (err) {
			console.error("Could not reach sparrowhawk search endpoint");
		}
		console.log("Requesting page from sparrowhawk: "+ target);
		
		sparrowRes.on('data', function (data) {
			docId += data;
		});
		sparrowRes.on('end', function () {
			console.log("docId: " + docId);

			// if no, we need to fetch it from origin (or 404)
			if (docId === '') {
				getPageFromOrigin(requestUrl);
			}
			else {
			// TODO: should be regex test on ID
				filterPage(webpage);
			}
		});
	});
	
	sparrowReq.write(JSON.stringify(webpage));
	sparrowReq.end();
}

/**
 * @param requestUrl
 */
function getPageFromOrigin(requestUrl, indexPage) {
	var pageReq = http.request(requestUrl, function (err, res) {
		if (err) {
			console.log("Could not connect to origin server at: " + req.hostname);
		}
		
		res.on('connect', function () {
			console.log('Connected to origin');
			//console.log('headers: ' + JSON.stringify(res.headers));
		});
		res.on('data', function (chunk) {
			page += chunk;
		});
		res.on('end', function () {
			console.log('Indexing page');
			
			// and index it
			webpage.html = page;
			sparrowhawk.index.headers = {'Content-Length': webpage.length};
			indexPage(webpage);
		});
	});
	pageReq.end();
}

/**
 * @param webpage
 */
function indexPage(webpage) {
	var indexReq = http.request(sparrowhawk.index, function (err, res) {
		if (err) {
			console.error('Index request failed ' + e.message);
		}
		
		res.on('end', function () {
			console.log('Indexed page ' + requestUrl);
			filterPage(webpage);
		})
	});
	indexReq.write(webpage);
	indexReq.end();
}

/**
 * @param webpage
 */
function filterPage(webpage) {
	var filterReq = http.request(sparrowhawk.filter, function (res) {
		
		var decision;
		res.on('data', function (chunk) {
			decision += chunk;
		});
		
		res.on('end', function () {
			console.log('Filtered page ' + requestUrl);
			
			if (decision === 'ALLOW') {
				// TODO : allow action - read out to res. headers and webpage
				if (webpage.html === '') {
					getPageFromOrigin(requestUrl, function (requestUrl) {
						consol.log("Retrieving " + requestUrl.hostname + requestUrl.path);
					});
				}
			}
			else if (decision === 'BLOCK') {
				// TODO : block action - blockpage.
			}
			else {
				console.log('ERROR: sparrowhawk policy neither ALLOW or BLOCK: ' + decision);
			}
		});
	});
	filterReq.on('error', function (e) {
		console.error('Filter request failed ' + e.message);
	});
	filterReq.write(webpage);
	filterReq.end();
}