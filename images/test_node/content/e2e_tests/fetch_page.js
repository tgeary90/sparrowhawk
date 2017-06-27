var chai = require('chai');
var chaiHttp = require('chai-http');
var expect = chai.expect;
var helper = require('../common_test/test_helper');

var proxy = 'http://sparrowproxy:8091';


chai.use(chaiHttp);
chai.request.Request = chai.request.Test;
require('superagent-proxy')(chai.request);

var subscriberJson = '{"id":"f45a1143-a917-4bda-861b-79473f28238b","name":"test","license":"CUSTOMER"}';

describe('End-End', function() {
	
	before('before', function (done) {
		helper.subscribe(done, subscriberJson);
	});
	
	after('after', function(done) {
		helper.clean(done);
	});
	
	it('Fetch an allowed page', function (done) {
		chai.request('http://www.bbc.co.uk')
		.get('/')
		.proxy(proxy)
		.end(function (res) {
			expect(res).to.have.status(200);
			done();
		});
	});
});