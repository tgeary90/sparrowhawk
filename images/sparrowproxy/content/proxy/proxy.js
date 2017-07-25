
// server.js - sparrow-proxy server module

var http = require('http');
var url = require('url');
var sparrowhawk = require('./config');

var requestUrl;
var target;

var proxy = http.createServer(function (req, res) {

	console.log("Received request from " + req.connection.remoteAddress);
	console.log("URL: " + req.url);
	console.log("Method: " + req.method);
	
    requestUrl = url.parse(req.url);

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

    target = requestUrl.hostname + requestUrl.path;
    console.log("raw target: " + target);
    var isFullUrl = /\//.test(target);
    if (! isFullUrl) {
    	target += '/';
    }
    target = target.replace(/\/$/, '/index.html');
    
    console.log('full target: ' + target);

    var docId;
    var webpage = {
	    'url': target,
	    'html': ""
    };
	
    isPageInElasticsearch(webpage);
    
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
		'Content-Length': webpageText.length
	};
	console.log("sending request to endpoint: " + sparrowhawk.search.path);
	console.log("page: " + webpageText);

	var sparrowReq = http.request(sparrowhawk.search, function (sparrowRes) {
//		for (var h in sparrowhawk.search.headers) {
//			console.log("header: " + sparrowhawk.search.headers[h]);
//		}
		
//		if (err) {
//			console.log("Webpage length is: " +webpageText.length);
//			console.log("Webpage is: " + webpageText);
//			console.error("Failure at sparrowhawk search endpoint " + err);
//			return;
//		}
		
		sparrowRes.on('data', function (chunk) {
			console.log(chunk);
			docId += chunk;
		});
		sparrowRes.on('end', function (err) {
			if (err) {
				console.log("Error received from search endpoint");
				return;
			}
			console.log("docId: " + docId);

			// if no, we need to fetch it from origin (or 404)
			if (docId === 'miss') {
				getPageFromOrigin(webpage);
			}
			else if (/\w+/.test(docId)) {
				filterPage(webpage);
			}
		});
	});
	
	sparrowReq.on('error', function (e) {
		console.log('problem with request to search endpoint: ' + e);
		console.log('msg: ' + e.message);
	});
	sparrowReq.write(webpageText);
	sparrowReq.end();
}

function getPageFromOrigin(webpage) {
	
	var page;
	var reqTarget = "http://" + target;
	console.log("reqTarget: " + reqTarget);
	console.log("requestUrl: " + requestUrl);
	
	http.get(reqTarget, function (sparrowRes) {
//		if (err) {
//			console.log("Could not get " + reqTarget + " from origin server");
//			return;
//		}
		sparrowRes.setEncoding("utf8");
		//sparrowRes.on('connect', function () {
		//	console.log('Connected to origin');
			//console.log('headers: ' + JSON.stringify(sparrowRes.headers));
		//});
		sparrowRes.on('data', function (chunk) {
			page += chunk;
		});
		sparrowRes.on('end', function () {
			console.log('Indexing page');
			
			// and index it
			webpage.html = page;
			//sparrowhawk.index.headers = {'Content-Length': webpage.length};
			indexPage(webpage);
		});
	});
	//pageReq.end();
}

/**
 * @param webpage
 */
function indexPage(webpage) {
	var webpageText = JSON.stringify(webpage);
	console.log("indexing page: " + webpage.url);
	
	sparrowhawk.index.headers = { 
		'Content-Type': 'application/json',
		'Content-Length': webpageText.length
	};
	
	var indexReq = http.request(sparrowhawk.index, function (sparrowRes) {
		sparrowRes.on('end', function () {
			console.log('Indexed page ' + webpage.url);
			filterPage(webpage);
		})
	});
	indexReq.write(webpageText);
	indexReq.end();
}

/**
 * @param webpage
 */
function filterPage(webpage) {
	var webpageText = JSON.stringify(webpage);
	console.log("filtering page: " + webpage.url);
	
	sparrowhawk.filter.headers = { 
		'Content-Type': 'application/json',
		'Content-Length': webpageText.length
	};
	
	var filterReq = http.request(sparrowhawk.filter, function (sparrowRes) {
		
		var decision;
		sparrowRes.on('data', function (chunk) {
			decision += chunk;
		});
		
		sparrowRes.on('end', function () {
			if (decision === 'ALLOW') {
				console.log('allow');
				if (webpage.html === '') {
					getPageFromOrigin(webpage);
				}
				res.writeHead(sparrowRes.statusCode, {'Content-Type': 'application/json'});
				res.write(webpage);
				res.end();
			}
			else if (decision === 'BLOCK') {
				console.log('block');
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
	filterReq.write(JSON.stringify(webpage));
	filterReq.end();
}