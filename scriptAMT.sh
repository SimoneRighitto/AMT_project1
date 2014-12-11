#!/bin/bash
#
#Description:
#  This script in a first part will setup a mysql database.
#  Then it will create a domain, a jdbc-resource and a connection-pool related to the created database for the glassfish server
#
#Authors:
#   Simone Righitto, Stéphane Maillard

#definition of useful variables
DB_NAME=AMTDatabase
DB_TECHNICAL_USER=AMTTechnicalUser
DB_TECHNICAL_USER_PASSWORD=go
JDBC_CONNECTION_POOL_NAME="$DB_NAME"_pool
JDBC_JNDI_NAME=JDBC/"$DB_NAME"
DOMAIN_NAME=domainAMT 
#Here we have to specify the path where the mysql .jar connector is located. As an example we give the path of our local clone of the github project, where we can find the mysql-connector-java-5.1.33-bin.jar connector
JAR_CONNECTOR="C:\Users\RigHitZ\Documents\Heig-VD\5semestre\AMT\AMT_project1\mysql-connector-java-5.1.33-bin.jar"
#Here we can specify where is the galssfish bin directory
GLASSFISH_PATH="C:\Program Files\glassfish-4.1\glassfish\bin"

# Partie MySQL

cd "'C:\Program Files\wamp\bin\mysql\mysql5.6.17\bin'"

./mysql -u root << EOF
DROP DATABASE IF EXISTS $DB_NAME;
CREATE DATABASE $DB_NAME;

GRANT USAGE ON *.* TO '$DB_TECHNICAL_USER'@'localhost';
GRANT USAGE ON *.* TO '$DB_TECHNICAL_USER'@'%';

DROP USER '$DB_TECHNICAL_USER'@'localhost';
DROP USER '$DB_TECHNICAL_USER'@'%';

CREATE USER '$DB_TECHNICAL_USER'@'localhost' IDENTIFIED BY '$DB_TECHNICAL_USER_PASSWORD';
CREATE USER '$DB_TECHNICAL_USER'@'%' IDENTIFIED BY '$DB_TECHNICAL_USER_PASSWORD';
 
GRANT ALL PRIVILEGES ON $DB_NAME.* TO '$DB_TECHNICAL_USER'@'localhost';
GRANT ALL PRIVILEGES ON $DB_NAME.* TO '$DB_TECHNICAL_USER'@'%'; 

USE $DB_NAME;

 
EOF


# Partie Glassfish
cd $GLASSFISH_PATH


./asadmin.bat stop-domain $DOMAIN_NAME || true
./asadmin.bat delete-domain $DOMAIN_NAME || true

./asadmin.bat create-domain --nopassword=true $DOMAIN_NAME


cp $JAR_CONNECTOR ../domains/$DOMAIN_NAME/lib

./asadmin.bat start-domain $DOMAIN_NAME


./asadmin.bat create-jdbc-connection-pool \
--restype=javax.sql.XADataSource \
--datasourceclassname=com.mysql.jdbc.jdbc2.optional.MysqlXADataSource \
--property User=$DB_TECHNICAL_USER:Password=$DB_TECHNICAL_USER_PASSWORD:serverName=localhost:portNumber=3306:databaseName=$DB_NAME $JDBC_CONNECTION_POOL_NAME





./asadmin.bat create-jdbc-resource --connectionpoolid $JDBC_CONNECTION_POOL_NAME $JDBC_JNDI_NAME

./asadmin.bat ping-connection-pool $JDBC_CONNECTION_POOL_NAME

exit 0
