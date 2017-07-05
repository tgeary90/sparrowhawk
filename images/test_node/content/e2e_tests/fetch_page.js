var chai = require('chai');
var chaiHttp = require('chai-http');
var expect = chai.expect;
var helper = require('../common_test/test_helper');


chai.use(chaiHttp);
//chai.request.Request = chai.request.Test;
//require('superagent-proxy')(chai.request);

var host = 'http://sparrowproxy:8091';
//var host = 'http://172.17.0.1:8091';
var subscriberJson = '{"id":"f45a1143-a917-4bda-861b-79473f28238b","name":"sparrowhawk","license":"CUSTOMER"}';
var bbcpage = '{"url": "www.bbc.co.uk", "html":""}';

describe('End-End', function() {
	
	before('before', function (done) {
		helper.subscribe(done, subscriberJson);
	});
	
	after('after', function (done) {
		helper.clean(done, 'sparrowhawk');
	});
	
	// mimicks a browser
	it('Fetch an allowed page', function (done) {
		chai.request(host)
		.get('www.bbc.co.uk')
		.end(function (err, res) {
			console.log("body: " + res.body);
			console.log("txt: " + res.text);
			expect(res).to.have.status(200);
			done();
		});
	});
});
