
// create_subscriber.js - script to create a new subscriber

var sparrowhawk = require('./config');
var http = require('http');
var uuidV1 = require('uuid/v1');

var sub = process.argv[2];

if (process.argv.length != 3) {
	console.log('need name of the subscriber');
	process.exit();
}

console.log('Name: ' + sub);
var postData = {
	"id": uuidV1(),
	"name": sub,
	"license":"CUSTOMER"
};
sparrowhawk.subscribe.headers = {'Content-Length': Buffer.byteLength(postData), 'Content-Type': 'application/json'};

console.log('Subscriber: ' + JSON.stringify(postData));
console.log('Endpoint: ' + JSON.stringify(sparrowhawk.subscribe));

var req = http.request(sparrowhawk.subscribe, function (res) {
	res.on('data', function (chunk) {
		console.log(chunk.toString());
	});
	console.log("Subscriber " + sub + " created");
});

req.on('error', function (e) {
	console.error('Request failed ' + e.message);
});

req.write(JSON.stringify(postData));
req.end();


