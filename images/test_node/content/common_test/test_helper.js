var expect = require('chai').expect;
var chai = require('chai'), chaiHttp = require('chai-http');

chai.use(chaiHttp);

module.exports.clean = function (done) {
  chai.request('http://sparrowsearch:9200')
    .delete('/test')
    .end(function (err, res) {
      done();
    });
};

module.exports.subscribe = function (done, json) {
  chai.request('http://sparrowhawk:8090')
    .post('/subscribers')
    .set('Content-Type', 'application/json')
    .send(json)
    .end(function (err, res) {
      expect(res).to.have.status(200);
      done();
    });
};

