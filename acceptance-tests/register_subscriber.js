var http = require('http');
var subscriberJson = '{"id":"9559346a-8f69-4b7c-8879-2d983dec015a","name":"jeromes-food-ltd","license":"CUSTOMER"}';

var options = {
 hostname: '172.17.0.1',
 port: 8090,
 path: '/subscribers',
 method: 'POST',
 headers: {
  'Content-Length': subscriberJson.length,
  'Content-Type': 'application/json'
 }
};

function sendReq() {
  var req = http.request(options, function(res) {
   res.on("data", function(chunk) {
     console.log(chunk.toString());
    });
  });
  req.on("error", function(e) {
    console.error('request failed ' + e.message);
  });
  req.write(subscriberJson);
  req.end();
}

sendReq();
sendReq();
