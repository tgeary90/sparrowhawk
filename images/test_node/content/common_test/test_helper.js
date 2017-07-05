var expect = require('chai').expect;
var chai = require('chai'), chaiHttp = require('chai-http');

chai.use(chaiHttp);

var host = 'sparrowhawk';
//var host = '172.17.0.1';

module.exports.clean = function (done, subscriber) {
  chai.request('http://sparrowsearch:9200')
    .delete('/' + subscriber)
    .end(function (err, res) {
      done();
    });
};

module.exports.subscribe = function (done, json) {
	console.log("host: " + host);
  chai.request('http://' + host + ':8090')
    .post('/subscribers')
    .set('Content-Type', 'application/json')
    .send(json)
    .end(function (err, res) {
      expect(res).to.have.status(200);
      done();
    });
};

