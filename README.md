Heig-VD AMT project1 : REST-API
===============================

The main project goal is to offer a Software-as-a-Service (SaaS) platform to organizations.

Our API is designed for organizations that use sensors to capture data.
The organization using our API can manage itself (name, person of contact, ...) but also the resources that are disponible in the organization like the users and the sensors.
Organization sensors will generate observations that are also managed via the API.
We have decided to generate some kinde of facts (daily facts : to find out statiscit of the daily work of a specific sensor, or counter fact : that are facts following the number of observation provided by a sensor) based on observations.

#Installation
  * git clone https://github.com/SimoneRighitto/AMT_project1.git
  * Install Glassfish
  * Install NetBeans
  * Install Wamp (if you are running Windows) or the version for you OS (Linux : Lamp, Mac : Mamp)
  * Run the setup.sh script to create a glassfish domain (domainAMT), a "ConnectionPool" and a "JDBC" resource to "connect" the database with the domainAMT previously created. WARNING ! Before running the script you will have to modify some path variable inside the script. Open it up with a text editor and modify the path you need to modify.
  * Start the domain with asadmin (inside glassfish/bin directory run ./asadmin start-domain domainAMT)
  * Start Netbeans, import the project and run it !
 
# Test Data
  * The application contains already some data, so you can test and try the API
  * If you go to : localhost:8080/amtProject/v1/api/generate, the data will be generetad for you.
  * You can populate the "Observations" with the script populateAPI.js and Node.js, it will create X observations. You can directly modify X and others parameters in the file.

#Documentation
Go on http://HEROKUAPP HEREEEE !!!!!!!!!!!!!!!!!! to see our API documentation

# TO DO
Here a list of what we are goingo to implement in a future:
  * We will performs security checks to ensure that the observation is valid

#Authors : 
 * Simone Righitto
 * Stéphane Maillard
