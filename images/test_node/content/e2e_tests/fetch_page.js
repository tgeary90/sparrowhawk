var chai = require('chai');
var chaiHttp = require('chai-http');
var expect = chai.expect;
var helper = require('../common_test/test_helper');


chai.use(chaiHttp);
chai.request.Request = chai.request.Test;
require('superagent-proxy')(chai.request);

var site = 'local-server';
//var host = 'http://172.17.0.1:8091';
var proxy = 'http://sparrowproxy:8091';

var subscriberJson = '{"id":"f45a1143-a917-4bda-861b-79473f28238b","name":"sparrowhawk","license":"CUSTOMER"}';
//var testpage = '{"url": "www.test.local", "html":""}';

describe('End-End', function() {
	
	before('before', function (done) {
		//helper.subscribe(done, subscriberJson);
		done();
	});
	
	after('after', function (done) {
		//helper.clean(done, 'sparrowhawk');
		done();
	});
	
	// mimicks a browser
	it('Fetch an allowed page', function (done) {
		this.timeout(30000);
		var sleepy = 5000;
		
		chai.request(site)
		.get('/')
		.proxy(proxy)
		.end(function (err, res) {
			console.log("sleep for 5 secs...");
			setTimeout(function () {
				console.log("body: " + res.body);
				console.log("txt: " + res.text);
				expect(res).to.have.status(200);
				done();
			}, sleepy);
		});
	});
});
