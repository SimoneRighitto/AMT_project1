// We need this to build our post string
var querystring = require('querystring');
var http = require('http');
var fs = require('fs');

// An object of options to indicate where to post to
var post_options = {
  host: 'localhost',
  port: '8080',
  path: '/AMT_API_project/api/organizations/',
  method: 'POST',
  headers: {
	  'Content-Type': 'application/json'
  }
};

// Set up the request

var insertData = function(name) {
	var post_req = http.request(post_options, function(res) {
	  res.setEncoding('utf8');
	  res.on('data', function (chunk) {
		  console.log('Response: ' + chunk);
	  });
	});
	post_req.write('{"name" : "' + name + '"}');
	post_req.end(); 
}

for (var i = 1; i < 5000; i++) {
	insertData("organizations" + i);
}