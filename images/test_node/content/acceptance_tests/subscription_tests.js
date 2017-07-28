var expect = require('chai').expect;
var chai = require('chai'), chaiHttp = require('chai-http');
var helper = require('../common_test/test_helper');

var subscriberJson = '{"id":"f45a1143-a917-4bda-861b-79473f28238b","name":"test","license":"CUSTOMER"}';

//var host = 'sparrowhawk';
var host = '172.17.0.1';

chai.use(chaiHttp);

describe('Subscription', function() {

  before('before', function (done) {
    console.log('before');
    helper.clean(done, 'test');
  });

  after('after', function (done) {
    //helper.clean(done, 'test');
	  done();
  });
  
  it('Subscribe to Sparrowhawk', function (done) {
    chai.request('http://' + host + ':8090')
      .post('/subscribers')
      .set('Content-Type', 'application/json')
      .send(subscriberJson)
      .end(function (err, res) {
        expect(res).to.have.status(200);
        done();
      });
  });
});


