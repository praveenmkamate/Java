# PKs Journey
`Subject: COMP20300 Java Programming Name:Praveen Kamate Student ID: 22200326`<br /><br />
This is a game built on idea of Simon's game.

This game has a board, obstacles and players. The player who reaches the finishing line wins the race.

There are mainly five obstacles in the game: <br />
1. Pillar: The players cannot jump or pass through this obstacle and has to change the direction if he encounters the pillar.<br />
2. Ice: The players who step on this obstacle miss the next turn. <br />
3. Danger: The players who step on this obstacle start the game again from the cell they started initially. <br />
4. Santa: This is a score multiplier. The players who step on this obstacle multiply their existing score with the dice value.
5. Pirate: This is a score reducer. The players who step on this obstacle divide their existing score with the dice value.

## Instructions to Run the Game
1. This game requires Java 17 and maven to run the game.
2. To build the game using maven, navigate to root folder and execute the below command. <br />
`mvn clean install -DskipTests`
3. To start the game run view -> WelcomeScreenController or execute the below command. <br />
`mvn javafx:run`
4. To run the test cases for the project execute the below command.
<br />`mvn test`