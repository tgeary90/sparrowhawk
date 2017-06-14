var expect = require('chai').expect;
var chai = require('chai'), chaiHttp = require('chai-http');

chai.use(chaiHttp);

var clean_elasticsearch = function clean(done) {
  chai.request('http://sparrowsearch:9200')
    .delete('/test')
    .end(function (err, res) {
      done();
    });
};

var subscribe = function subscribe(done, json) {
  chai.request('http://172.17.0.1:8090')
    .post('/subscribers')
    .set('Content-Type', 'application/json')
    .send(json)
    .end(function (err, res) {
      expect(res).to.have.status(200);
      done();
    });
};

module.exports.clean_elasticsearch = clean_elasticsearch;
module.exports.subscribe = subscribe;
