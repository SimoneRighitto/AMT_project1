AMT_project1
=============

Heig-VD AMT_project1 : REST-API

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
  * Adapt the pathes in the script autoConfiScript.sh or you can create a "ConnectionPool" and a "JDBC with "asadmin" directly, you can find the names of all the elements in the script
  * Start the domain with asadmin
  * Start Netbeans and start the project
 
# Test Data
  * The application contains already some data, so you can test and try the API
  * If you go to : localhost:8080/amtProject/v1/api/generate, the data will be generetad for you.
  * You can populate the "Observations" with the script populateAPI.js and Node.js, it will create X observations. You can directly modify X and others parameters in the file.

#Documentation
You can find the detailled documentation about our API here : http://mellymello.github.io/AMT-ProjectREST

#Authors : 
 * Simone Righitto
 * Stéphane Maillard
