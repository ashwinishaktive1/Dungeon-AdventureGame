<!-- PROJECT LOGO -->
<br />
<h1 align="center"> Project 5 - Adventure Game </h1>

<div align="center">
<h3> Author : Ashwini Shaktivel Kumar </h3>
</br>
    <img src="https://static.tvtropes.org/pmwiki/pub/images/maze_runner.jpg" alt="Maze Runner" width="370" height="450">
    <br />
    <br />

  <p align = "left">
    <h2> Find your way out!</h2>
    Welcome to the confusion land, be brave and find your way out of the maze while collecting maximum amount of treasures. Now play with a friend to race out on killing the monster. It's you who decides how you win!
    <br />
    <br />
  </p>
</div>

<!-- Overview -->
## Overview
The world for our game consists of a dungeon, a network of tunnels and caves that are interconnected so that the player can explore the entire world by travelling from cave to cave through the tunnels that connect them. The dungeons may host monsters who are hungry for flesh and can eat the player. Use arrows to kill the monsters! Try your luck in the these dungeons that are generated at random following some set of constraints resulting in a different network each time the application is asked for a new game. Few features of the game includes:
<ol>
<li>
The maze is a 2-D grid with the player starting from the "Start" location and ending at the "End" location. Beware! A monster's always present in the end cave. Fight to win!
</li>
<li>
The dungeon locations may also "wrap" to the one on the other side of the grid. For example, moving to the north from the top row in the grid moves the player to the location in the same column in the bottom row.
<li>
Each location in the grid represents a location in the dungeon where a player can explore and can be connected to at most four (4) other locations: one to the north, one to the east, one to the south, and one to the west.
</li>
<li>
A location can further be classified as tunnel (which has exactly 2 entrances) or a cave (which has 1, 3 or 4 entrances).
</li>
<li>
The player may also collect treasures available in the caves. The types of treasures available include DIAMONDS, RUBY and SAPPHIRES.
</li>
<li>
Player can encounter monsters in caves, so be safe. The monsters can be avoided by tracking their smell. Kill the monsters by successfully shooting them with arrow twice.
<li>
The player wins the game upon reaching the end cave.
</li>
<li>
The player looses the game in the event they are killed by the monster. In two-player mode, the other player can continue playing till they reach end or get killed.</li>
</ol>

## Dungeon specifications
The dungeons are generated at disposal of the following specifications:
<li> The game can be played as a single player or with a friend. The aim remains the same, kill the monster and reach end. 
<li> There's always a path from every cave in the dungeon to every other cave in the dungeon.
<li> Each dungeon can be constructed with a degree of interconnectivity. We define an interconnectivity = 0 when there is exactly one path from every cave in the dungeon to every other cave in the dungeon. Increasing the degree of interconnectivity increases the number of paths between cave

<li> Not all dungeons "wrap" from one side to the other.

<li> One cave is randomly selected as the start and one cave is randomly selected to be the end. The path between the start and the end locations should be at least of length 5.
<br/>
<br/>

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- List of features -->
## List of features
For this model, the player is not very smart and may take a long route to the end by selecting random directions. You may later apply algorithms to find the fastest path to the end.
<br/>

For now, the game allows the user to use the following features:
<ol>
<li>Decide the specifications of the dungeon. This includes:
<ul>
<li>The number of players playing the game. (1 or 2)
<li><b>Size of the dungeon:</b> 
<br/>The dungeon dimension is equal to number of rows * number of columns. A 5*6 sized dungeon will have 30 locations in the dungeon.</li>
<li><b>Interconnectivity:</b>
<br/>An interconnectivity = 0 allows exactly one path from every cave in the dungeon to every other cave in the dungeon. Increasing the degree of interconnectivity increases the number of paths between caves. </i></li>
<li><b>Whether the dungeon is wrapped?</b>
</br>Select "true" if the dungeon is required to be wrapped.</li>
<li><b>Treasure spread:</b>
</br>Treasure to be added to a specified percentage of caves. For example, the client should be able to ask the model to add treasure to 20% of the caves and the model should add a random treasure to at least 20% of the caves in the dungeon. A cave can have more than one treasure. The same percentage of locations (caves and tunnels) contain extra arrows that the player can use to shoot monsters.</li>
<li><b>Difficulty:</b></br>
Difficulty is decided based on the number of monsters present in the dungeon. Set the difficulty according to your comfort.
</ul>
</li>
<li>
The player enters the dungeon at the start.
<li>
The model allows request for current game status, player description and the available moves from the current location. 
</li>
<li> Player can pick up treasure and arrows that is located in their same location.
</ol>
Now that we know, how a player gets ready for the dungeon maze. Let's discuss further about how the adventure game runs.

<p align="right">(<a href="#top">back to top</a>)</p>

## How to kill the Monsters?

To give the player the ability to slay the Otyugh, you will automatically be equipped with a bow that uses crooked arrows.
<li>Player starts with 3 crooked arrows but can find additional arrows in the dungeon with the same frequency as treasure. Arrows and treasure can be, but are not always, found together. Furthermore, arrows can be found in both caves and tunnels.</li>
<li>Monsters can be detected by their smell. In general, the player can detect two levels of smell:
<ul>
<li>
a less pungent smell can be detected when there is a single Otyugh 2 positions from the player's current location</li>
<li>detecting a more pungent smell either means that there is a single Otyugh 1 position from the player's current location or that there are multiple Otyughs within 2 positions from the player's current location</li>
</ul>
</li>
<li>A player that has arrows, can attempt to slay an Otyugh by specifying a direction and distance in which to shoot their crooked arrow. Distance is defined as the number of caves (but not tunnels) that an arrow travels. Arrows travel freely down tunnels (even crooked ones) but only travel in a straight line through a cave. For example,
<ul>
<li>A tunnel that has exits to the west and south can have an arrow enter the tunnel from the west and exit the tunnel to the south, or vice-versa (this is the reason the arrow is called a crooked arrow)</li>
<li>A cave that has exits to the east, south, and west will allow an arrow to enter from the east and exit to the west, or vice-versa; but an arrow that enters from the south would be stopped since there is no exit to the north</li>
</ul>
</li>
<li>Distances must be exact. For example, if you shoot an arrow a distance of 3 to the east and the Otyugh is at a distance of 2, you miss the Otyugh.</li>
<li>It takes 2 hits to kill an Otyugh. Players has a 50% chance of escaping the Otyugh if they enter a cave of an injured Otyugh that has been hit by a single crooked arrow. This means, you live by luck.</li>
</br>

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- Two player -->
## Two-Player mode
<li>In the two-player mode, players take turns making moves or shooting arrows as they race to be the first to kill the Otyugh at the exit. 
<li>In this mode, the rules of the game remain the same as before. 
The game ends when one player has killed the Otyugh that is at the exit, or both players have died. 
<li>Arrows shot by the players miraculously miss the other player.
</br>
</br>

<!-- How To Run? -->
## How To Run?

To run the JAR file packed, execute the follow command to use text-based game:
   ```sh
   java -jar project05_AdventureGameUI.jar --text <numberOfPlayers> <numberOfRows> <numberOfColumns> <interconnectivity> <should maze be wrapped?> <treasure spread percentage> <difficulty:number of monsters>
   ```
   Use the following command to use GUI-based game:
   ```sh
   java -jar project05_AdventureGameUI.jar --gui 
   ```
<p align="right">(<a href="#top">back to top</a>)</p>

<!--How to Play? -->
## How to play? (GUI Cheatsheet):

<b>Move:</b> </br>
Click on the neighboring locations (1 step away) using mouse or use the following keys to move in required direction:</br>
UP arrow - North</br>
DOWN arrow - South</br>
LEFT arrow - West</br>
RIGHT arrow - East</br>
</br>
<b>Shoot arrow:</b></br>
Give the distance to shoot in as an integer using the number pad and then press any one of the following key to decide on the direction:</br>
W - North</br>
S - South</br>
A - West</br>
D - East</br>
Example: 2 D -- shoot two caves away in East direction</br>
</br>
<b>Pickup treasure in cave:</b></br>
Select the treasure you want to pick up using any one of the keys on number pad below:</br>
1 - Diamond</br>
2 - Ruby</br>
3 - Sapphire</br>
Then press T on the keyboard to collect the treasure.</br>
</br>
<b>Pickup arrows in any location:</b></br>
Press P on the keyboard to pick up the arrows.</br>
</br>
At any given time view the current player status on the right panel. You will notice each action's result on the top panel. The player's turn in displayed on the bottom of the view frame.
<p align="right">(<a href="#top">back to top</a>)</p>
<!-- How to Use the Program? -->
## How to Use the Program?

<ul>
    <li>Source files (src folder) - Run the Driver file. Use Main method with command line arguments including number of rows, number of columns, interconnectivity, dungeon wrapping, the treasure spread, and difficulty (number of monsters in dungeon) to run a static listed run of the adventure game. Other java files include:
    <ol>
    <li> AdventureGameController Interface
    <li> AdventureGameConsoleController Class
    <li> AdventureGameViewController Class
    <li> ReadonlyGameModel Interface
    <li> Game Interface
    <li> AdventureGame Class
    <li> Dungeons Interface
    <li> MazeDungeon Class
    <li> Players Interface
    <li> Player Class
    <li> Monsters Interface
    <li> Otyugh Class
    <li> Location Interface
    <ul>
    <li> AbstractLocation Abstract Class
    <ul>
    <li> Cave Class
    <li> Tunnel Class
    </ul>
    <li> Treasure ENUM
    <li> Direction ENUM
    </ul>
    <li>GameView Interface
    <li> GameViewImpl Class
    <ul>
    <li> HomePagePanel Class
    <li> GameStatusPanel Class
    <li> GamePanel Class
    </ul>
    <li> RandomNumberGenerator Interface
    <ul>
    <li>RandomNumberGeneratorDev Class
    <li>RandomNumberGeneratorTest Class
    </ul>
    </ol>
    </li>
    <li>Test Files - Run test file including AdventureGameControllerTest, GameTest, DungeonsTest, PlayersTest, MonstersTest, and LocationTest for JUnit tests of each component class. The test files are executed from outside the dungeon package (in the test package). FailingAppendable java is a helper file to test IOException for the controller operation.</li>
    <li>The final design document is available in the res directory for reference. 
    <li>JAR file (res directory) - 
    <ul>
    To run the java file for a <b><i>text-based game</i></b>, use the following command on command line with required arguments: 
    <br/> 
    <ul>
    <b> java -jar project05-AdventureGameUI.jar --text</b>
    </ul>
      Required additional arguments include number of players (1 or 2), number of rows, number of columns, interconnectivity, dungeon wrapping ("true" or "false"), the treasure spread percentage across the caves, and the difficulty specified by the number of monsters in the dungeon.java.
      </br>
      </br>
      To run the java file for a <b><i>Graphic Used Interface-based game</i></b>, use only the following command on command line:
    <ul>
    <b> java -jar project05-AdventureGameUI.jar --gui</b>
    </ul>
    </li>
</ul>
</ul>

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- Description of Examples -->
## Description of Examples
Driver program description:
<ol> 
<li> Run the Driver.java class file. </li>
<li> To play the game using graphical user interface, add <b> --gui </b> command line argument.
<li> To play the game in text-based format use <b> --text </b> followed by the required values to set the dungeon specifications. The arguments must follow the following sequence: </br>
<i> Number of players (integer - 1 or 2), Number of rows (integer), number of columns (integer), interconnectivity (integer), dungeon wrapping ("true" or "false"), treasure spread percentage (decimal) across the caves, and the difficulty or number of monsters in the cave (integer)</i>
<li> Initialize random number generator object. </li>
<li> Initialize the Game model. </li>
<li> Introduce the dungeon specifications requested by the user. </li>
<li> Setup readable by creating an instance of InputStreamReader. </li>
<li> Setup appendable to append content to the console. </li>
<li> Create an instance of the AdventureGameConsoleController using the readable and appendable parameters.</li>
<li> Play game using the controller.</li>
</ol>
<br>
Additional features in GUI based game include: (You may use these at any given time)
<ul>
<li> "Restart game" to start from the initial cave again. 
<li> "New game" to re-specify dungeon specifications and try your luck on a new setting.
<li> "Quit" to end the game.
</ul>
</ul>
<p> View the screenshots in the res\Sample runs folder to observe the design aspects of the user interface. The example run involves starting a single-player game while setting the id, playing, and getting killed by the monster. I then created a new game using the menu, now a two-player game, with scrollable dimensions. The players race to find the end monster and one wins the game. </p>
<p align="right">(<a href="#top">back to top</a>)</p>

<!-- Design/Model Changes -->
## Design/Model Changes

<p> My model and controller did not involve any design changes for the text-based game run. The following two points describe the implementation changes made. 
<li>I changed the model constructor to additionally take an argument for the user's wish on the number of players playing the game. To accomodate a two player game setting, I decided on adding a method to the Model interface asking for player 2 setup details. This further rised a need for keeping a list of players active in the game. This allowed me to add getter methods for the players which were then accessible by the View in later stages.</li>

<li>To switch the player turns I created a custom method for switching turns (until one player is dead) which is called after each successful shoot or move made by the players. </li>
</br>

<p>For the development of Graphical User Interface, I created a second implementation of the AdventureGameController. This controller communicated between the View and Model of the MVC design. I further created a ReadonlyGame model, which only comprises of the getter methods. This is created to restrict the range of access View has on the Model, and to prevent illegal assignments. The Readonly model is now extended by our previous model interface.

<p> The View was implemented using a set of JFrames and JPanels. The View starts with the GameView interface which is then implemented by the GameViewImpl class defining the structure and design of the UI. This element directly communicated with the controller. Sub-panels may communicate with the read-only model to get current status of the game elements.

<p align="right">(<a href="#top">back to top</a>)</p>

<!-- Assumptions -->
## Assumptions

<ul>
<li>It's assumed that the number of rows and columns must be greater than 3 to always achieve a maze for successful implementation and game completion. The number of rows and columns must be non-negative integers.</li>
<li> Every cave is connected to every other cave with atleast one path.</li>
<li>Interconnectivity must be non-negative integer.</li>
<li>If the requested interconnectivity is more than the leftover paths in the leftover set, a minimum between interconnectivity and size of leftover set is selected to add more paths to the dungeon.
<li>A cave can have 1 to 3 gems of each type, diamonds, rubies and sapphires in the required percentage of caves. A tunnel cannot have any treasure.</li>
<li> Treasure is spread only in the required percentage of caves in the dungeon. The requested spread cannot be less than 0 or greater than 100.
<li> In case of two-player mode, both the players always start from the same Start cave. The player ID's must be in the range of 0 and 9. In case of a bigger value given by user, it is defaulted to either 1 or 2 depending on the sequence of initialization.
<li> Player starts with 3 arrows.
<li> Arrows are distributed in the same percentage as the treasure.</li>
<li>Player can collect any number of treasure available in the cave at the point of arrival.</li>
<li>Player can only move in one of the available move directions, including North, South, East and West.</li>
<li>One monster is always present in the end cave.</li>
<li>No two monsters can stay in the same cave, they live in solitary.</li>
<li>Player may smell "strongly" when a monster is 1 position away or multiple monsters are 2 positions away, and may smell "slightly" when a single monster is 2 positions away.</li>
<li>Player can shoot a monster only when the direction and estimated distance is correct for the monster.</li>
<li>Monster dies after two arrow shots.</li>
<li>Caves and tunnels can be assigned with arrows multiple times, while the initialization.</li>
<li>Game ends upon reaching the end or if the player is eaten by the monster.</li>
</ul>

</br>
<p align="right">(<a href="#top">back to top</a>)</p>

<!-- LIMITATIONS -->
## Limitations

<ul>
<li>For this model, the user input is not taken to make the player move. The game is initialized only based on the command line arguments received from the user while running the application.</li>
<li>In case of small dungeons, search of start and end caves with a distance of 5 may lead to infinite loops.</li>
<li> The game cannot be started without setting up the player(s) first.</li>
<li>Only one or two player(s) plays the game. The player wins the game only by reaching the end.</li>
<li> In two-player mode the turns are switched alternatively after each move and arrow shoot.
<li>Supports only fixed kinds of treasures.</li>
<li>Monster does not move and stays at the same location. Monsters can only live in caves. Only one type of monster present.</li>
<li>Only one type of weapon designed, needs further development to give the weapons qualities.</li>
<li>Escaping from monster is based on luck. </li>
</ul>

</br>

<!-- CITATIONS -->
## Citations

<ul>
<li><a href="https://www.markdownguide.org/getting-started/">Markdown guide</a></li>
<li><a href="https://www.geeksforgeeks.org/find-paths-given-source-destination/">Depth first search for path finding</a></li>
<li><a href="https://www.baeldung.com/java-random-list-element">Get Random Item From A List</a></li>
<li><a href="https://www.geeksforgeeks.org/iterate-map-java/">How to iterate any Map in Java</a></li>
<li><a href="https://www.geeksforgeeks.org/builder-pattern-in-java/">Builder methods in java</a></li>
<li><a href="https://docs.oracle.com/javase/tutorial/java/nutsandbolts/branch.html">Branching statements</a></li>
<li><a href = "https://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html#:~:text=GridBagLayout%20is%20one%20of%20the,necessarily%20have%20the%20same%20height.">Grid Bag Layout for View</a></li>
</ul>

<p align="right">(<a href="#top">back to top</a>)</p>
