var http = require('http');
var expect = require('chai').expect;

var subscriberJson = '{"id":"9559346a-8f69-4b7c-8879-2d983dec015a","name":"jeromes-food-ltd","license":"CUSTOMER"}';

var subscribe = {
 hostname: '172.17.0.1',
 port: 8090,
 path: '/subscribers',
 method: 'POST',
 headers: {
  'Content-Length': subscriberJson.length,
  'Content-Type': 'application/json'
 }
};

var clean = {
  hostname: '172.17.0.1',
  port: 8090,
  path: '/9559346a-8f69-4b7c-8879-2d983dec015a',
  method: 'DELETE'  
};


describe('Subscription', function() {

  before(function() {
    var req = http.request(clean, function(res) {
      res.on('end', function () {
          console.log("cleaned index, status: " + res.statusCode);
      });
    });
    req.end();
  });

  it('Subscribe to Sparrowhawk', function() {
    var req = http.request(subscribe, function(err, res) {
      res.on('end', function (err) {
          expect(res.statusCode).to.equal(200);
          expect(res.body).to.equal('true');
      });
    });
    req.write(subscriberJson);
    req.end();
  });
});


