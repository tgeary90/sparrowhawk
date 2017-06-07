var chai = require('chai'), chaiHttp = require('chai-http');
var expect = require('chai').expect;

chai.use(chaiHttp);

var page = '{"url": "www.test.local/text.txt", "html":""}';
var subscriberJson = '{"id":"f45a1143-a917-4bda-861b-79473f28238b","name":"test","license":"CUSTOMER"}';

describe('Webpage', function() {
 
  before('Subscribe to Sparrowhawk', function(done) {
    chai.request('http://172.17.0.1:8090')
      .post('/subscribers')
      .send(subscriberJson)
      .end(function () {
        done();
      });
  });

  it('Index a webpage', function (done) {
    chai.request('http://172.17.0.1:8090')
      .post('/pages/test/index')
      .send(page)
      .end(function (err, res) {
       console.log(res.body);
        expect(res).to.have.status(200);
        expect(res.body).should.be.a('String');
        done();
      });
  });
  
//  after('Delete test index', function (done) {
//    chai.request('http://172.17.0.1:8090')
//      .delete('/test')
//      .end(function (err, res) {
//        console.log('Cleaning index \'/test\'');
//	done();
//      });
//  });

});
