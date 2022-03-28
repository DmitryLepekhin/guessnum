# Guess number game

#### The small project is an exercise of coding a backend for a WebSocket application without using STOMP. 


### Game process:
1) The server starts a round of the game and gives 10 seconds to place a bet from the players on numbers from 1 to 10 with the amount of the bet
2) After the time expires, the server generates a random number from 1 to 10
3) If the player guesses the number, a message is sent to him that he won with a winnings of 9.9 times the stake
4) If the player loses receives a message about the loss
5) All players receive a message with a list of winning players in which there is a nickname and the amount of winnings
6) The process is repeated

### Requirements:

* Java 11, Gradle, Spring Boot
* Unit and integration test coverage
* Communication between players and the server via WebSocket
* STOMP is not used
* Validation of submitted data
