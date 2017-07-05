
// server.js - sparrow-proxy server module

var http = require('http');
var url = require('url');
var sparrowhawk = require('./config');


var proxy = http.createServer(function (req, res) {

	console.log("Received request from " + req.connection.remoteAddress);
	console.log("URL: " + req.url);
	console.log("Method: " + req.method);
	
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
    var isFullUrl = /\//.text(target);
    if (! isFullUrl) {
    	target += '/index.html';
    }
    console.log('Request for URL received: ' + target);

    var docId;
    var webpage = {
	    'url': target,
	    'html': ""
    };
	
}).listen(process.argv[2]);
console.log('sparrow-proxy listening on ' + process.argv[2] + '...');


/**
 * @param webpage
 */
function isPageInElasticsearch(webpage) {
	
	var webpageText = JSON.stringify(webpage);
	var docId = '';
	
	sparrowhawk.search.headers = { 
		'Content-Type': 'application/json',
	};
	var sparrowReq = http.request(sparrowhawk.search, function (err, sparrowRes) {
		if (err) {
			console.log("Webpage length is: " +webpageText.length);
			console.log("Webpage is: " + webpageText);
			console.error("Failure at sparrowhawk search endpoint " + err.message);
			return;
		}
		
		sparrowRes.on('data', function (chunk) {
			docId += chunk;
		});
		sparrowRes.on('end', function () {
			console.log("docId: " + docId);

			// if no, we need to fetch it from origin (or 404)
			if (docId === '') {
				getPageFromOrigin(requestUrl);
			}
			else if (/\w+/.test(docId)) {
				filterPage(webpage);
			}
		});
	});
	
	sparrowReq.write(webpageText);
	sparrowReq.end();
}

/**
 * @param requestUrl
 */
function getPageFromOrigin(requestUrl, indexPage) {
	var pageReq = http.request(requestUrl, function (err, sparrowRes) {
		if (err) {
			console.log("Could not connect to origin server at: " + req.hostname);
		}
		
		sparrowRes.on('connect', function () {
			console.log('Connected to origin');
			//console.log('headers: ' + JSON.stringify(sparrowRes.headers));
		});
		sparrowRes.on('data', function (chunk) {
			page += chunk;
		});
		sparrowRes.on('end', function () {
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
	var indexReq = http.request(sparrowhawk.index, function (err, sparrowRes) {
		if (err) {
			console.error('Index request failed ' + e.message);
		}
		
		sparrowRes.on('end', function () {
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
	var filterReq = http.request(sparrowhawk.filter, function (sparrowRes) {
		
		var decision;
		sparrowRes.on('data', function (chunk) {
			decision += chunk;
		});
		
		sparrowRes.on('end', function () {
			console.log('Filtered page ' + requestUrl);
			
			if (decision === 'ALLOW') {
				if (webpage.html === '') {
					getPageFromOrigin(requestUrl, function (requestUrl) {
						console.log("Retrieving " + requestUrl.hostname + requestUrl.path);
					});
				}
				res.writeHead(sparrowRes.statusCode, {'Content-Type': 'application/json'});
				res.write(webpage);
				res.end();
			}
			else if (decision === 'BLOCK') {
				res.writeHead(sparrowRes.statusCode, {'Content-Type': 'text/plain'});
				res.write('BLOCKED');
				res.end();
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