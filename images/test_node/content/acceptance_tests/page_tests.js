var expect = require('chai').expect;
var chai = require('chai'), chaiHttp = require('chai-http');
var helper = require('../common_test/test_helper');

chai.use(chaiHttp);

var page = '{"url": "www.test.local/text.txt", "html":""}';
var subscriberJson = '{"id":"f45a1143-a917-4bda-861b-79473f28238b","name":"test","license":"CUSTOMER"}';

describe('Webpage', function() {

	before('before', function(done) {
		helper.subscribe(done, subscriberJson);
	});

	after('after', function(done) {
		helper.clean(done);
	});

	it('Index a webpage', function(done) {
		chai.request('http://172.17.0.1:8090')
			.post('/pages/test/index')
			.set('Content-Type', 'application/json').send(page)
			.end(function (err, res) {
				expect(res).to.have.status(201);
				expect(res.text).to.be.a('string');
				done();
			});
	});
	
	it('Get a webpage', function(done) {
		
		var docId;
		
		chai.request('http://172.17.0.1:8090')
		.post('/pages/test/index')
		.set('Content-Type', 'application/json').send(page)
		.end(function (err, res) {
			docId = res.text;

			chai.request('http://172.17.0.1:8090')
			.get('/pages/test/index/' + docId)
			.end(function (err, res) {
				expect(res.body).to.have.property('url');
				expect(res.body).to.have.property('html');
				expect(res).to.have.status(200);
				done();
			});
		});
	});
});
