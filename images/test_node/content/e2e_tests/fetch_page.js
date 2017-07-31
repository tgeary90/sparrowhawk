var chai = require('chai');
var chaiHttp = require('chai-http');
var expect = chai.expect;
var helper = require('../common_test/test_helper');


chai.use(chaiHttp);
chai.request.Request = chai.request.Test;
require('superagent-proxy')(chai.request);

var site = 'local-server';
var proxy = 'http://172.17.0.1:8091';
//var proxy = 'http://sparrowproxy:8091';

var subscriberJson = '{"id":"f45a1143-a917-4bda-861b-79473f28238b","name":"sparrowhawk","license":"CUSTOMER"}';
//var testpage = '{"url": "www.test.local", "html":""}';

describe('Fetch Page', function() {
	
	before('before', function (done) {
		helper.subscribe(done, subscriberJson);
		//done();
	});
	
	after('after', function (done) {
		helper.clean(done, 'sparrowhawk');
		//done();
	});
	
	// mimicks a browser
	it('Clean index - Allow', function (done) {
		this.timeout(10000);
		var sleepy = 5000;
		
		chai.request(site)
		.get('/')
		.proxy(proxy)
		.end(function (err, res) {
			setTimeout(function () {
				//console.log("body: " + res.body);
				//console.log("txt: " + res.text);
				expect(res).to.have.status(200);
				done();
			}, sleepy);
		});
	});
	
	// mimicks a browser
	it('Populated index - Allow', function (done) {
		this.timeout(10000);
		var sleepy = 5000;
		
		chai.request(site)
		.get('/')
		.proxy(proxy)
		.end(function (err, res) {
			setTimeout(function () {
				//console.log("body: " + res.body);
				//console.log("txt: " + res.text);
				expect(res).to.have.status(200);
				done();
			}, sleepy);
		});
	});
});
