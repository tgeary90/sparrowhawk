var expect = require('chai').expect;
var chai = require('chai'), chaiHttp = require('chai-http');

var subscriberJson = '{"id":"f45a1143-a917-4bda-861b-79473f28238b","name":"test","license":"CUSTOMER"}';

chai.use(chaiHttp);

var clean = function clean(done) {
    chai.request('http://sparrowsearch:9200')
      .delete('/test')
      .end(function (err, res) {
        console.log('Cleaning index \'test\'');
        done();
      });
};

describe('Subscription', function() {

  before('before', function (done) {
    clean(done);
  });

  after('after', function (done) {
    clean(done);
  });
  
  it('Subscribe to Sparrowhawk', function (done) {
    console.log('beginning subscription test');
    chai.request('http://172.17.0.1:8090')
      .post('/subscribers')
      .set('Content-Type', 'application/json')
      .send(subscriberJson)
      .end(function (err, res) {
        expect(res).to.have.status(200);
        done();
      });
  });
});


