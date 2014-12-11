// We need this to build our post string
var querystring = require('querystring');
var http = require('http');
var fs = require('fs');

var doPost = function() {
  // Build the post string from an object
  var post_data = "{'name' = 'aaaaaa'}";

  // An object of options to indicate where to post to
  var post_options = {
      host: 'localhost',
      port: '8080',
      path: '/AMT_API_project/api/organizations',
      method: 'POST',
      headers: {
          'Content-Type': 'application/json'
      }
  };

  // Set up the request
  var post_req = http.request(post_options, function(res) {
      res.setEncoding('utf8');
      res.on('data', function (chunk) {
          console.log('Response: ' + chunk);
      });
  });

  // post the data
  post_req.write(post_data);
  post_req.end();

}

doPost();