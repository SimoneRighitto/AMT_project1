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
var submittedDailyStats = {}

// This map keeps track of the observations posted by the client, but only if the server has confirmed
// their processing with a successful status code. 
// In this case, the client can assume that the observation has been successfully processed.
var processedStats = {};
var processedDailyStats = {}


function logObservation(stats, observation) {
	var factStats = stats[observation.sourceSensorId] || {
		sourceSensorId: observation.sourceSensorId,
		numberOfObservations: 0,
		};
	factStats.numberOfObservations += 1;
	stats[observation.sourceSensorId] = factStats;
}

function logDailyObservation(stats, observation) {
	var key = "k" + observation.sourceSensorId +":"+ observation.time.substring(0,10); 
	//console.log(key);
	var factStats = stats[key] || {
		sourceSensorId: observation.sourceSensorId,
		numberOfObservations: 0,
		averageValue : 0,
		minValue : 0,
		maxValue : 0
		
		};
	factStats.numberOfObservations += 1;
	factStats.averageValue += observation.value;   // check if its right....divide ???
	if(observation.value < factStats.minValue){
		factStats.minValue= observation.value;
	}
	if(observation.value > factStats.maxValue){
		factStats.maxValue= observation.value;
	}
	stats[key] = factStats;
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
				'sourceSensorId' : 6 
			}
		};
		
		requestData.data.value = Math.floor((Math.random() * 200) - 50);
		//randomize data
		logObservation(submittedStats, requestData.data);
		logDailyObservation(submittedDailyStats, requestData.data);
		
		
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

for (var fact=1; fact<=2; fact++) {
	for (var observation=0; observation<60; observation++) {
		requests.push(
			getObservationPOSTRequestFunction(fact)
		);
	}
};


/*
 * Reset server side - this will delete all facts
 */
/* 
function resetServerState(callback) {
	console.log("\n\n==========================================");
	console.log("POSTing RESET command.");
	console.log("------------------------------------------");
	client.post("http://localhost:8080/ConcurrentUpdateDemo/api/operations/reset", function(data, response) {
		console.log("RESET response status code: " + response.statusCode);
		callback(null, "The RESET operation has been processed (status code: " + response.statusCode + ")");
	});
};
*/

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
				logDailyObservation(processedDailyStats, results[i].requestData.data);
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
	client.get("http://localhost:8080/AMT_API_project/api/v1/facts", requestData, function(data, response) {
		var numberOfErrors = 0;
		
		var clientSideCounterFacts = Object.keys(submittedStats).length;
		var clientSideDailyFacts = Object.keys(submittedDailyStats).length;
		var clientSideFacts = clientSideCounterFacts+clientSideDailyFacts;
		var serverSideFacts = data.length;
		var serverSideCounterFacts = 0;
		var serverSideDailyFacts = 0;
		
		console.log("Number of facts on the client side: " + clientSideFacts);
		console.log("Number of facts on the server side: " + serverSideFacts);
		if ( clientSideFacts !== serverSideFacts) {
			numberOfErrors++;
		}
		
		for (var i=0; i<data.length; i++) {
	
			var factSourceSensorId = data[i].sensorId;
			var factType = data[i].type;
			var factDayDate = data[i].dayDate;
			var factInfo = data[i].infos;
			
			if(factType == "counter"){
				var serverSideNumberOfObservations = factInfo["obsCounter"];
				var clientSideNumberOfObservations = processedStats[factSourceSensorId];
				if (serverSideNumberOfObservations !== clientSideNumberOfObservations) {
				numberOfErrors++;
				console.log("Sensor " + factSourceSensorId + " --> Server/Client number of observations: " + serverSideNumberOfObservations + "/" + clientSideNumberOfObservations + "  X");
				} else {
				//console.log("Sensor " + factSourceSensorId + " --> Server/Client number of observations: " + serverSideNumberOfObservations + "/" + clientSideNumberOfObservations");				
				}
			}
			else if(factType == "daily"){
			
			
			//
			//TO DO
			//
			//
			
			}
			else{
				consol.log("Error : unknown fact type");
			}
			
		
			
		}
		
		callback(null, "The client side and server side values have been compared. Number of corrupted facts: " + numberOfErrors);
	});
}

async.series([
	//resetServerState,
	postObservationRequestsInParallel,
	checkValues
], function(err, results) {
	console.log("\n\n==========================================");
	console.log("Summary");
	console.log("------------------------------------------");
	//console.log(err);
	console.log(results);
});



	