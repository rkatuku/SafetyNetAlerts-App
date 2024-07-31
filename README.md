**SafetyNet Alerts Application**

SafetyNet Alerts App sends emergency information to emergency responders. For instance, if
there is a fire, we need SafetyNet Alerts to bring up information about the people in the house
that is on fire, such as their phone numbers. Another example is if there is a severe storm
warning, we want SafetyNet Alerts to notify all the people in the area of the storm via text
message. To do this we need SafetyNet Alerts to get the phone numbers of every resident who
lives in homes near the storm area.

In the event of a flood alert, we want to dispatch responders with specialized information about
everyone in the area. We need to know every person who could be in the flood, their ages, and
any specific medical information about them, such as their medications and allergies.
With SafetyNet Alerts, we hope to dispatch more prepared and informed first responders.
SafetyNet Alerts is developed using Spring boot and endpoints that yield information about its
status.

It reads the data file containing the names and addresses and produce JSON output from the
corresponding URLs below.

**Endpoints**

* This URL should return a list of people serviced by the corresponding fire station. So if station
number = 1, it should return the people serviced by station number 1. The list of people should
include these specific pieces of information: first name, last name, address, phone number. As
well, it should provide a summary of the number of adults in the service area and the number of
children (anyone aged 18 or younger).
```
http://localhost:8080/firestation?stationNumber=<station_number>
```

* This URL should return a list of children (anyone under the age of 18) at that address. The list
should include the first and last name of each child, the child’s age, and a list of other persons
living at that address. If there are no children at the address, then this URL can return an empty
string.
```
http://localhost:8080/childAlert?address=<address>
```

* This URL should return a list of phone numbers of each person within the fire station’s
jurisdiction.We’ll use this to send emergency text messages to specific households.
```
http://localhost:8080/phoneAlert?firestation=<firestation_number>
```

* This URL should return the fire station number that services the provided address as well as a list
of all the people living at the address. This list should include each person’s name, phone number,
age, medications with dosage, and allergies.
```
http://localhost:8080/fire?address=<address>
```

* This should return a list of all the households in each fire station’s jurisdiction. This list needs to
group people by household address, include name, phone number, and age of each person, and
any medications (with dosages) and allergies beside each person’s name.
http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
This should return the person’s name, address, age, email, list of medications with dosages and
allergies. If there is more than one person with the same name, this URL should return all of
them.
```
http://localhost:8080/flood/stations?stations=<a_list_of_station_numbers>
```
* This will return the email addresses of all of the people in the city.
```
http://localhost:8080/communityEmail?city=<city>
```

In addition to those endpoints, below endpoints are used to update the data.
*This endpoint will provide the following via Http Post/Put/Delete:
Add a new person.
Update an existing person (at this time, assume that firstName and lastName do not change, but
other
fields can be modified).
Delete a person. (Use a combination of firstName and lastName as a unique identifier)
```
http://localhost:8080/person
```
* This endpoint will provide the following via Http Post/Put/Delete:
Add a firestation/address mapping.
Update an address’ firestation number.
Delete a firestation/address mapping.
```
http://localhost:8080/firestation
```

* This endpoint will provide the following via Http Post/Put/Delete:
Add a medical record.
Update an existing medical record (as above, assume that firstName and lastName do not
change).
Delete a medical record. (Use a combination of firstName and lastName as a unique identifier)
Sample request and response JSON:
Get endpoints:
```
http://localhost:8080/medicalRecord
```

**Sample Request and Response**
```
http://localhost:8080/firestation?stationNumber=1
```
``` JSON
{
"personsStationList": [
{
"firstName": "Peter",
"lastName": "Duncan",
"address": "644 Gershwin Cir",
"phoneNumber": "841-874-6512"
},
{
"firstName": "Reginold",
"lastName": "Walker",
"address": "908 73rd St",
"phoneNumber": "841-874-8547"
},
{
"firstName": "Jamie",
"lastName": "Peters",
"address": "908 73rd St",
"phoneNumber": "841-874-7462"
},
{
"firstName": "Brian",
"lastName": "Stelzer",
"address": "947 E. Rose Dr",
"phoneNumber": "841-874-7784"
},
{
"firstName": "Shawna",
"lastName": "Stelzer",
"address": "947 E. Rose Dr",
"phoneNumber": "841-874-7784"
},
{
"firstName": "Kendrik",
"lastName": "Stelzer",
"address": "947 E. Rose Dr",
"phoneNumber": "841-874-7784"
}
],
"totalAdultsNumber": 5,
"totalChildrenNumber": 1
}
```

```
http://localhost:8080/childAlert?address=947%20E.%20Rose%20Dr
```
``` JSON
[
{
"firstName": "Brian",
"lastName": "Stelzer",
"age": 48
},
{
"firstName": "Shawna",
"lastName": "Stelzer",
"age": 43
},
{
"firstName": "Kendrik",
"lastName": "Stelzer",
"age": 10
}
]
```
```
http://localhost:8080/phoneAlert?firestation=1
```
``` JSON
[
"841-874-6512",
"841-874-8547",
"841-874-7462",
"841-874-7784",
"841-874-7784",
"841-874-7784"
]
```
```
http://localhost:8080/fire?address=1509%20Culver%20St
```
``` JSON
[
{
"stationNumber": "3",
"firstName": "John",
"lastName": "Boyd",
"age": 40,
"phoneNumber": "841-874-6512",
"medications": [
"aznol:350mg",
"hydrapermazol:100mg"
],
"allergies": [
"nillacilan"
]
},
{
"stationNumber": "3",
"firstName": "Jacob",
"lastName": "Boyd",
"age": 35,
"phoneNumber": "841-874-6513",
"medications": [
"pharmacol:5000mg",
"terazine:10mg",
"noznazol:250mg"
],
"allergies": []
},
{
"stationNumber": "3",
"firstName": "Tenley",
"lastName": "Boyd",
"age": 12,
"phoneNumber": "841-874-6512",
"medications": [],
"allergies": [
"peanut"
]
},
{
"stationNumber": "3",
"firstName": "Roger",
"lastName": "Boyd",
"age": 6,
"phoneNumber": "841-874-6512",
"medications": [],
"allergies": []
},
{
"stationNumber": "3",
"firstName": "Felicia",
"lastName": "Boyd",
"age": 38,
"phoneNumber": "841-874-6544",
"medications": [
"tetracyclaz:650mg"
],
"allergies": [
"xilliathal"
]
}
]
```
```
http://localhost:8080/flood/stations?stations=2
```
``` JSON
[
{
"station": "2",
"householdsList": [
{
"address": {
"address": "29 15th St",
"city": "Culver",
"zip": "97451"
},
"personsList": [
{
"firstName": "Jonathan",
"lastName": "Marrack",
"phone": "841-874-6513",
"age": 35,
"medications": [],
"allergies": []
}
]
},
{
"address": {
"address": "951 LoneTree Rd",
"city": "Culver",
"zip": "97451"
},
"personsList": [
{
"firstName": "Eric",
"lastName": "Cadigan",
"phone": "841-874-7458",
"age": 78,
"medications": [
"tradoxidine:400mg"
],
"allergies": []
}
]
},
{
"address": {
"address": "892 Downing Ct",
"city": "Culver",
"zip": "97451"
},
"personsList": [
{
"firstName": "Sophia",
"lastName": "Zemicks",
"phone": "841-874-7878",
"age": 36,
"medications": [
"aznol:60mg",
"hydrapermazol:900mg",
"pharmacol:5000mg",
"terazine:500mg"
],
"allergies": [
"peanut",
"shellfish",
"aznol"
]
},
{
"firstName": "Warren",
"lastName": "Zemicks",
"phone": "841-874-7512",
"age": 39,
"medications": [],
"allergies": []
},
{
"firstName": "Zach",
"lastName": "Zemicks",
"phone": "841-874-7512",
"age": 7,
"medications": [],
"allergies": []
}
]
}
],
"stationNumber": "2"
}
]
```
```
http://localhost:8080/personInfo?firstName=Jacob&lastName=Boyd
```
``` JSON
[
{
"firstName": "John",
"lastName": "Boyd",
"age": 40,
"address": "1509 Culver St",
"city": "Culver",
"zip": "97451",
"email": "jaboyd@email.com",
"medications": [
"aznol:350mg",
"hydrapermazol:100mg"
],
"allergies": [
"nillacilan"
]
},
{
"firstName": "Jacob",
"lastName": "Boyd",
"age": 35,
"address": "1509 Culver St",
"city": "Culver",
"zip": "97451",
"email": "drk@email.com",
"medications": [
"pharmacol:5000mg",
"terazine:10mg",
"noznazol:250mg"
],
"allergies": []
},
{
"firstName": "Tenley",
"lastName": "Boyd",
"age": 12,
"address": "1509 Culver St",
"city": "Culver",
"zip": "97451",
"email": "tenz@email.com",
"medications": [],
"allergies": [
"peanut"
]
},
{
"firstName": "Roger",
"lastName": "Boyd",
"age": 6,
"address": "1509 Culver St",
"city": "Culver",
"zip": "97451",
"email": "jaboyd@email.com",
"medications": [],
"allergies": []
},
{
"firstName": "Felicia",
"lastName": "Boyd",
"age": 38,
"address": "1509 Culver St",
"city": "Culver",
"zip": "97451",
"email": "jaboyd@email.com",
"medications": [
"tetracyclaz:650mg"
],
"allergies": [
"xilliathal"
]
},
{
"firstName": "Allison",
"lastName": "Boyd",
"age": 59,
"address": "112 Steppes Pl",
"city": "Culver",
"zip": "97451",
"email": "aly@imail.com",
"medications": [
"aznol:200mg"
],
"allergies": [
"nillacilan"
]
}
]
```
```
http://localhost:8080/communityEmail?city=Culver
```
``` JSON
[
"jaboyd@email.com",
"drk@email.com",
"tenz@email.com",
"jaboyd@email.com",
"jaboyd@email.com",
"drk@email.com",
"tenz@email.com",
"jaboyd@email.com",
"jaboyd@email.com",
"tcoop@ymail.com",
"lily@email.com",
"soph@email.com",
"ward@email.com",
"zarc@email.com",
"reg@email.com",
"jpeter@email.com",
"jpeter@email.com",
"aly@imail.com",
"bstel@email.com",
"ssanw@email.com",
"bstel@email.com",
"clivfd@ymail.com",
"gramps@email.com"
]
```
