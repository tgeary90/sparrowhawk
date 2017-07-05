var expect = require('chai').expect;
var chai = require('chai'), chaiHttp = require('chai-http');
var helper = require('../common_test/test_helper');

chai.use(chaiHttp);

var host = 'http://sparrowhawk:8090';
//var host = 'http://172.17.0.1:8090';


var page = '{"url": "www.test.local/text.txt", "html":""}';
var page2 = '{"url": "www.google.local/index.html", "html":""}';
var subscriberJson = '{"id":"f45a1143-a917-4bda-861b-79473f28238b","name":"test","license":"CUSTOMER"}';

describe('Webpage', function() {

	before('before', function (done) {
		helper.subscribe(done, subscriberJson);
	});

	after('after', function (done) {
		helper.clean(done, 'test');
	});

	it('Index a webpage', function(done) {
		chai.request(host)
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
		
		chai.request(host)
		.post('/pages/test/index')
		.set('Content-Type', 'application/json').send(page)
		.end(function (err, res) {
			
			docId = res.text;
			chai.request(host)
			.get('/pages/test/index/' + docId)
			.end(function (err, res) {
			
				expect(res.body).to.have.property('url');
				expect(res.body).to.have.property('html');
				expect(res).to.have.status(200);
				done();
			});
		});
	});
	
	it('Search for a webpage', function(done) {
		var timeout = 1000;
		
		chai.request(host)
		.post('/pages/test/index')
		.set('Content-Type', 'application/json').send(page2)
		.end(function (err, res) {
			
			setTimeout(function () {
				chai.request(host)
				.post('/pages/test/search/')
				.set('Content-Type', 'application/json').send(page2)
				.end(function (err, res) {
					
					expect(res.text).to.be.a('string');
					expect(res).to.have.status(200);
					done();
				});
			}, timeout);
		});
	});
});
