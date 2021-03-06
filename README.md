# Kalah application

This is backend side application for kalah game. You must install **java** and **maven** to run it.

To run application type in command line:

**mvn spring-boot:run**

# Kalah game API manual

1. Creation of the game should be possible with the command:
```
curl --header "Content-Type: application/json" \
--request POST \
http://SERVER_HOST:SERVER_PORT/games
```
Response:
```
HTTP code: 201
Response Body: { "id": "1234", "uri": "http://SERVER_HOST:SERVER_PORT/games/1234" }
```            
* id: unique identifier of a game
* url: link to the game created

1. Make a move:
``` 
curl --header "Content-Type: application/json" \
--request PUT \
http://SERVER_HOST:SERVER_PORT/games/{gameId}/pits/{pitId}
```
* gameId: unique identifier of a game
* pitId: id of the pit selected to make a move. Pits are numbered from 1 to 14 where 7 and 14 are the kalah (or house) of each player
Response:
```
HTTP code: 200
Response Body:
{"id":"1234","url":"http://SERVER_HOST:SERVER_PORT/games/1234","status":{"1":"4","2":"4","3":"4","4":"4","5":"4","6":"4","7":"0","8":"4","
9":"4","10":"4","11":"4","12":"4","13":"4","14":"0"}}
```
* status: json object key-value, where key is the pitId and value is the number of stones in the pit
