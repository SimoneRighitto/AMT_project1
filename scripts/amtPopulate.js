// We need this to build our post string
var querystring = require('querystring');
var http = require('http');
var fs = require('fs');

// An object of options to indicate where to post to
var post_options_observations = {
  host: 'localhost',
  port: '8080',
  path: '/AMT_API_project/api/observations/',
  method: 'POST',
  headers: {
	  'Content-Type': 'application/json'
  }
};

// Set up the request

var insertObservation = function(value) {
	var post_req = http.request(post_options_observations, function(res) {
	  res.setEncoding('utf8');
	  res.on('data', function (chunk) {
		  console.log('Response: ' + chunk);
	  });
	});
	var data = '{"time" : "' + new Date().toJSON() + '", "value" : "' + value + '"}';
	post_req.write(data);
	post_req.end(); 
}

for (var i = 1; i < 100; i++) {
	insertObservation(Math.floor((Math.random() * 100) + 1), 3);
}