doctor-patient-rest-api-spring-boot-MySQL
-------------------------------------------
> Import project into your local system

> docker-compose.yml file contains MySQL configurations

> Go to root level of this project and run below command

> To run below command you must have Docker up and running in your machine

> doctor-patient-crud-rest-api-spring-boot-mysql> docker-compose up 

Go to Main class and run the main class as SpringBoot application

Import postman collections which was attached in the project(DoctorPatient - RestAPI.postman_collection.json) in postman and execute end points 

Or else use below curls for each end points:

Get All Doctors:
--------------
curl --location --request GET 'http://localhost:8085/api/doctors/list'

Get Doctor By ID:
---------------
curl --location --request GET 'http://localhost:8085/api/doctors/5'

Save Doctor:
------------
curl --location --request POST 'http://localhost:8085/api/doctors/add' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Dr. Simha"
}'

Update Doctor Name By ID:
--------------------------
curl --location --request PUT 'http://localhost:8085/api/doctors/1?name=Dr.Reddy'

Delete Doctor By ID:
------------------
curl --location --request DELETE 'http://localhost:8085/api/doctors/2'

Add Patient Under Doctor:
-------------------------
curl --location --request POST 'http://localhost:8085/api/doctors/2/patient' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Suresh",
"disease": "Knee Pain",
"dateOfBirth": "2002-09-09"
}'

Discharge Patient:
-------------------
curl --location --request PUT 'http://localhost:8085/api/doctors/2/patient/1'

Get All Patients:
----------------
curl --location --request GET 'http://localhost:8085/api/patients/list'

Get Patient By Id:
------------------
curl --location --request GET 'http://localhost:8085/api/patients/1'

Save Patient:
------------
curl --location --request POST 'http://localhost:8085/api/patients/add' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Smith",
"disease": "Knee Pain",
"dateOfBirth": "2000-09-09"
}'

Update Patient By id:
---------------------
curl --location --request PUT 'http://localhost:8085/api/patients/1' \
--header 'Content-Type: application/json' \
--data-raw '{
"name": "Ramesh",
"disease": "Fever",
"dateOfBirth": "2002-09-09"
}
'

Delete Patient By ID:
--------------------
curl --location --request DELETE 'http://localhost:8085/api/patients/1'

Assign Doctor to Patient:
------------------------
curl --location --request PUT 'http://localhost:8085/api/patients/1/doctor/1'

-----
Note:-
----
> If you face testcase failures , please check MySQL server is running or not in your local, if not please make sure its up and running.

> else you can skip testcases using below mvn command.

> mvn clean install -DskipTests=true









