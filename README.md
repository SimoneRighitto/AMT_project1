Heig-VD AMT project1 : REST-API
===============================

The main project goal is to offer a Software-as-a-Service (SaaS) platform to organizations.

Our API is designed for organizations that use sensors to capture data. The organization using our API can manage itself (name, person of contact, ...) but also the resources that are available in the organization like the users and the sensors. Organization sensors will generate observations that are also managed via the API. We have decided to generate some kind of facts (daily facts : to find out statistic of the daily work of a specific sensor, or counter fact : that are facts following the number of observation provided by a sensor) based on observations

#Installation
  * git clone https://github.com/SimoneRighitto/AMT_project1.git
  * Install Glassfish
  * Install NetBeans
  * Install Wamp (if you are running Windows) or the version for you OS (Linux : Lamp, Mac : Mamp)
  * Run the setup.sh script to create a glassfish domain (domainAMT), a "ConnectionPool" and a "JDBC" resource to "connect" the database with the domainAMT previously created. WARNING ! Before running the script you will have to modify some path variable inside the script. Open it up with a text editor and modify the path you need to modify.
  * Start the domain with asadmin (inside glassfish/bin directory run ./asadmin start-domain domainAMT)
  * Start Netbeans, import the project and run it !
 
# Play with the API
  * If you go to : http://localhost:8080/AMT_API_project/api/v1/generate, some basic data (organizations,users,sensors, and also some test observations) will be generated for you.
  * You can populate the "Observations" with the script amtPopulate.js and Node.js. This script will simulate a sensor sending out observation data.
  * You can modify the script to generate some personal test observation in order to get familiar with the API

#Documentation
Go on https://hidden-savannah-5396.herokuapp.com/ to see our API documentation

# TO DO
Here a list of what we are goingo to implement in a future:
  * We will performs security checks to ensure that the observation is valid
  * We have to implement the visibility of private/public resources (right now everyone can see private things)
  * We want to add Pagination
  * We will add OAuth 2.0 for authorization and access delegation

#Authors : 
 * Simone Righitto
 * Stéphane Maillard
