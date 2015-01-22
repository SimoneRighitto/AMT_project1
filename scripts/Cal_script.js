/*
 * Developped for study purposes at Heig-VD.ch
 * Created: 20-nov-2014
 * Authors: Calixte Melly & Fr?d?ric Saam

 
 /!\ Ne pas oublier de lancer l'entrée /amtProject/v1/api/generate AVANT de lancer ce script.
 
 */

var Client = require('node-rest-client').Client;
var client = new Client();
var async = require('async');

// This map keeps track of the observations posted by the client, 
// even if they result in an error (for instance if two parallel requests try to create a new fact).
// In this case, the client is informed that the observation has failed and it would be his responsibility
// to retry.
var submittedStats = {};
var submittedDailyStats = {};

// This map keeps track of the observations posted by the client, but only if the server has confirmed
// their processing with a successful status code. 
// In this case, the client can assume that the observation has been successfully processed.
var processedStats = {};
var processedDailyStats = {};

var sensorIdGlobal = 0;

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
		sensorIdGlobal = data[0].id;
		console.log("GETSENSORID response status code: " + response.statusCode);
		callback(null, "The GETSENSORID operation has been processed (status code: " + response.statusCode + ")");
	});
}

function logObservation(stats, observation) {
	var factStats = stats[observation.sensorId] || {
		sensorId: observation.sensorId,
		numberOfObservations: 0
		};
	factStats.numberOfObservations += 1;
	stats[observation.sensorId] = factStats;
}

function logDailyObservation(stats, observation) {
	var key = "k" + observation.sensorId +":"+ observation.time.substring(0,10); 
	//console.log(key);
	var factStats = stats[key] || {
		sensorId: observation.sensorId,
		numberOfObservations: 0,
		averageValue : 0,
		minValue : 0,
		maxValue : 0
		
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
function getObservationPOSTRequestFunction(sensorId) {
		
	return function(callback) {
		var requestData = {
			headers:{
				"Content-Type": "application/json"
			},
			data: {
				'time' : new Date().toJSON(),
		
				'value': Math.floor((Math.random() * 200) - 50),
				'sensorId' :  sensorIdGlobal
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

for (var fact=1; fact<=2; fact++) {
	for (var observation=0; observation<60; observation++) {
		requests.push(
			getObservationPOSTRequestFunction(fact)
		);
	}
};

/*
 * Reset server side - this will delete all data
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

/*
 * generate server side - this will generate all data
 */
function generateServerState(callback) {
    console.log("\n\n==========================================");
    console.log("GETing GENERATE command.");
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
				logDailyObservation(processedDailyStats,results[i].requestData.data);
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



		console.log("Number of facts on the client side: " + clientSideFacts);
		console.log("Number of facts on the server side: " + serverSideFacts);
		if ( clientSideFacts !== serverSideFacts) {
			numberOfErrors++;
		}
		
		for (var i=0; i<data.length; i++) {
	
			var factSensorId = data[i].sensorId;
			var factType = data[i].type;
			var factDayDate = data[i].time;
			var factInfo = data[i].info;
			
			if(factType === "counter"){
				var serverSideNumberOfObservations = (factInfo[0])-1; // Nous enlevons 1 car /amtProject/v1/api/generate créée une observation avant ce script
				var clientSideNumberOfObservations = processedStats[factSensorId];
				console.log("Number of observation on the client side: " + clientSideNumberOfObservations.numberOfObservations);
				console.log("Number of observation on the server side: " + serverSideNumberOfObservations);
				if (serverSideNumberOfObservations !== clientSideNumberOfObservations.numberOfObservations) {
					numberOfErrors++;
					console.log("Sensor " + factSensorId + " --> Server/Client number of observations: " + serverSideNumberOfObservations + "/" + clientSideNumberOfObservations.numberOfObservations + "  X");
				} 
			}
			else if(factType === "daily"){


				var key = "k" + factSensorId +":"+ factDayDate.substring(0,10);

				console.log("Min sur le client : "+ (processedDailyStats[key]).minValue + " et sur le serveur : " + factInfo[0]);
				console.log("Max sur le client : "+ (processedDailyStats[key]).maxValue + " et sur le serveur : " + factInfo[1]);
				
				// Les valeurs ajoutées sont présentes car une observation est déjà sur le serveur au moment de lancer ce script
				console.log("Avg sur le client : "+ ((processedDailyStats[key]).averageValue + 25) / (processedDailyStats[key].numberOfObservations + 1) + " et sur le serveur : " + factInfo[2]);
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
    generateServerState,
	getSensorId,
	postObservationRequestsInParallel,
	checkValues
], function(err, results) {
	console.log("\n\n==========================================");
	console.log("Summary");
	console.log("------------------------------------------");
	//console.log(err);
	console.log(results);
});



	