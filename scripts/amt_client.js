/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 * Authors: Simone Righitto, Stéphane Maillard
 
 fact == account 
 observation == transaction  //sur les observation il y a pas de concurrence
 */

var Client = require('node-rest-client').Client;
var client = new Client();
var async = require('async');

// This map keeps track of the observations posted by the client, 
// even if they result in an error (for instance if two parallel requests try to create a new fact).
// In this case, the client is informed that the observation has failed and it would be his responsibility
// to retry.
var submittedStats = {}

// This map keeps track of the observations posted by the client, but only if the server has confirmed
// their processing with a successful status code. 
// In this case, the client can assume that the observation has been successfully processed.
var processedStats = {};

function logObservation(stats, observation) {
	var factStats = stats[observation.sourceSensorId] || {
		sourceSensorId: observation.sourceSensorId,
		numberOfObservations: 0,
		averageValue : 0
		};
	factStats.numberOfObservations += 1;
	factStats.averageValue += observation.value;   // check if its right....divide ???
	stats[observation.sourceSensorId] = factStats;
}


/*
 * This function returns a function that we can use with the async.js library. 
 */ 
function getObservationPOSTRequestFunction(sourceSensorId) {
		
	return function(callback) {
		var requestData = {
			headers:{
				"Content-Type": "application/json"
			},
			data: {
				'time' : new Date().toJSON(),
				'value': 0,
				'sourceSensorId' : 1 
			}
		};
		
		requestData.data.value = Math.floor((Math.random() * 200) - 50);
		logObservation(submittedStats, requestData.data);
		
		client.post("http://localhost:8080/AMT_API_project/api/v1/observations", requestData, function(data, response) {
			var error = null;
			var result = {
				requestData: requestData,
				data: data, 
				response: response
			};
			callback(error, result);
		});
	}
}

/*
 * Prepare an array of HTTP request functions. We will pass these to async.parallel
 */
var requests = [];

for (var fact=1; fact<=20; fact++) {
	for (var observation=0; observation<60; observation++) {
		requests.push(
			getObservationPOSTRequestFunction(fact)
		);
	}
};


/*
 * Reset server side - this will delete all facts
 */
function resetServerState(callback) {
	console.log("\n\n==========================================");
	console.log("POSTing RESET command.");
	console.log("------------------------------------------");
	client.post("http://localhost:8080/ConcurrentUpdateDemo/api/operations/reset", function(data, response) {
		console.log("RESET response status code: " + response.statusCode);
		callback(null, "The RESET operation has been processed (status code: " + response.statusCode + ")");
	});
};

/*
 * POST observation requests in parallel
 */
function postObservationRequestsInParallel(callback) {
	console.log("\n\n==========================================");
	console.log("POSTing observation requests in parallel");
	console.log("------------------------------------------");
	var numberOfUnsuccessfulResponses = 0;
	async.parallel(requests, function(err, results) {
		for (var i=0; i<results.length; i++) {
			if (results[i].response.statusCode < 200 || results[i].response.statusCode >= 300) {
				console.log("Result " + i + ": " + results[i].response.statusCode);
				numberOfUnsuccessfulResponses++;
			} else {
				logObservation(processedStats, results[i].requestData.data);
			}
		}
		callback(null, results.length + " observation POSTs have been sent. " + numberOfUnsuccessfulResponses + " have failed.");
	});
}

/*
 * Fetch server-side state (list of facts). The list of facts is passed to the callback function.
 */
function checkValues(callback) {
	console.log("\n\n==========================================");
	console.log("Comparing client-side and server-side stats");
	console.log("------------------------------------------");
	var requestData = {
		headers:{
			"Accept": "application/json"
		}
	};
	client.get("http://localhost:8080/ConcurrentUpdateDemo/api/facts", requestData, function(data, response) {
		var numberOfErrors = 0;
		var clientSideAccounts = Object.keys(submittedStats).length;
		var serverSideAccounts = data.length;
		console.log("Number of facts on the client side: " + clientSideAccounts);
		console.log("Number of facts on the server side: " + serverSideAccounts);
		if (clientSideAccounts !== serverSideAccounts) {
			numberOfErrors++;
		}
		
		for (var i=0; i<data.length; i++) {
			var sourceSensorId = data[i].id;
			var serverSideValue = data[i].balance;
			var clientSideValue = processedStats[sourceSensorId].balance;
			if (clientSideValue !== serverSideValue) {
				numberOfErrors++;
				console.log("Account " + sourceSensorId + " --> Server/Client balance: " + serverSideValue + "/" + clientSideValue + "  X");
			} else {
				//console.log("Account " + sourceSensorId + " --> Server/Client balance: " + serverSideValue + "/" + clientSideValue);				
			}
			
		}
		
		callback(null, "The client side and server side values have been compared. Number of corrupted facts: " + numberOfErrors);
	});
}

async.series([
	resetServerState,
	postObservationRequestsInParallel,
	checkValues
], function(err, results) {
	console.log("\n\n==========================================");
	console.log("Summary");
	console.log("------------------------------------------");
	//console.log(err);
	console.log(results);
});
	