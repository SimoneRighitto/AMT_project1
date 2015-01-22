/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 * Authors: Simone Righitto, Stéphane Maillard
 */

var Client = require('node-rest-client').Client;
var client = new Client();
var async = require('async');
var Chance = require('chance');
var chance = new Chance();

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

var workingSensorId = 0;

function getSensorId(callback) {
	console.log("\n\n==========================================");
    console.log("GETing sensor id command.");
    console.log("------------------------------------------");
	var requestData = {
		headers:{
			"Accept": "application/json"
		}
	};
	client.get("http://localhost:8080/AMT_API_project/api/v1/sensors", requestData, function(data, response) {
		workingSensorId = data[0].id;
		console.log("working ID : " +workingSensorId);
		console.log("GETSENSORID response status code: " + response.statusCode);
		callback(null, "The GETSENSORID operation has been processed (status code: " + response.statusCode + ")");
	});
}


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
	//console.log("orig key : " + key);
	var factStats = stats[key] || {
		sourceSensorId: observation.sourceSensorId,
		numberOfObservations: 0,
		averageValue : 0,
		minValue : observation.value,
		maxValue : observation.value
		
		};
	factStats.numberOfObservations += 1;
	factStats.averageValue += observation.value;
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
			//randomized data
			data: {
				'time' : chance.date().toJSON(),
				'value': Math.floor((Math.random() * 200) - 50),
				'sourceSensorId' : workingSensorId 
			}
		};
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

for (var fact=1; fact<=1; fact++) {
	for (var observation=0; observation<1; observation++) {
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
	client.post("http://localhost:8080/AMT_API_project/api/v1/data/reset", function(data, response) {
		console.log("RESET response status code: " + response.statusCode);
		callback(null, "The RESET operation has been processed (status code: " + response.statusCode + ")");
	});
};

function generateData(callback){
	console.log("\n\n==========================================");
	console.log("Get to GENERATE command.");
	console.log("------------------------------------------");
	client.get("http://localhost:8080/AMT_API_project/api/v1/data/generate", function(data, response) {
		console.log("GENERATE response status code: " + response.statusCode);
		callback(null, "The GENERATE operation has been processed (status code: " + response.statusCode + ")");
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
				var serverSideNumberOfObservations = factInfo[0];
				var clientSideNumberOfObservations = processedStats[factSourceSensorId].numberOfObservations;
				if (serverSideNumberOfObservations !== clientSideNumberOfObservations) {
				numberOfErrors++;
				console.log("CounterFact problem: Sensor " + factSourceSensorId + " --> Server/Client number of observations: " + serverSideNumberOfObservations + "/" + clientSideNumberOfObservations + "  X");
				} else {
				//console.log("Sensor " + factSourceSensorId + " --> Server/Client number of observations: " + serverSideNumberOfObservations + "/" + clientSideNumberOfObservations");
				console.log("serverSideNumberOfObservations equals to clientSideNumberOfObservations");
				}
			}
			else if(factType == "daily"){
			
			var key = "k" + factSourceSensorId +":"+ factDayDate.substring(0,10);
				
				var serverSideMinValue=factInfo[0];
				var serverSideMaxValue= factInfo[1];
				var serverSideAvgValue= factInfo[2];
			
				
				console.log("Min sur le client : "+ (processedDailyStats[key]).minValue + " et sur le serveur : " + factInfo[0]);
				console.log("Max sur le client : "+ (processedDailyStats[key]).maxValue + " et sur le serveur : " + factInfo[1]);
				
				
				console.log("Avg sur le client : "+ ((processedDailyStats[key]).averageValue) / (processedDailyStats[key].numberOfObservations) + " et sur le serveur : " + factInfo[2]);
			
			}
			else{
				consol.log("Error : unknown fact type");
			}
			
		
			
		}
		
		callback(null, "The client side and server side values have been compared. Number of corrupted facts: " + numberOfErrors);
	});
}

async.series([
	resetServerState,
	generateData,
	getSensorId,
	postObservationRequestsInParallel,
	checkValues
], function(err, results) {
	console.log("\n\n==========================================");
	console.log("Summary");
	console.log("------------------------------------------");
	console.log(err);
	console.log(results);
});



	