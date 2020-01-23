# API for an Online Banking System

## Access Database

Before running the project and testing the end points, you can get access to the database by creating a new MySQL connection as follows:

CREATE USER 'bank'@'localhost' IDENTIFIED BY 'api';  
CREATE SCHEMA bankapi;  
GRANT ALL PRIVILEGES ON bankapi.* TO 'bank'@'localhost

## Test End Points

The OnlineBankAPI.postman_collection.json file available here is a Postman collection to test all the end points of the API. Please note that some end points won't work on Postman Chrome and need to be tested on the Postman native app which can be downloaded here:

https://www.getpostman.com/apps
