package dungeons;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A MazeDungeon is an implementation of the Dungeon with caves and tunnels. 
 * The Maze gets assigned with a start and end cave, the player starts from 
 * the start cave and ends at the end cave, while collecting treasures on 
 * the way. The maze may also contain monsters in some caves, by default there's 
 * at least one monster at the end of the maze. Along with treasures, the player 
 * can pick up arrows to attack the monster.
 * 
 * @author Ashwini Shaktivel Kumar
 *
 */
public class MazeDungeon implements Dungeons {
  
  private int numOfRows;
  private int numOfCols;
  private int interconnectivity;
  private boolean isWrapped;
  private double requiredTreasureSpread;
  private boolean areTreasuresAssigned;
  private boolean areArrowsAssigned;
  private List<List<Integer>> dungeonLayout;
  private List<List<List<Location>>> adjacency;
  private List<Set<Integer>> dungeonConnections;
  private List<Set<Integer>> finalDungeonPaths;
  private List<List<Location>> allLocations;
  private List<List<Location>> allPaths;
  private List<Location> allCaves;
  private List<Location> allTunnels;
  private List<Monsters> monstersInDungeon;
  private Location start;
  private Location end;
  private int noOfMonsters;
  private RandomNumberGenerator random;
  
  /**
   * Construct a MazeDungeon for the Adventure Game.
   * 
   * @param r 2D grid position row number
   * @param c 2D grid position column number
   * @param interconn interconnectivity in the dungeon
   * @param wrapping should the dungeon be wrapped
   * @param treasureSpread amount of caves the treasure must be present in
   * @param difficulty number of monsters
   * @param rand Random Number Generator object
   * @throws IllegalArgumentException if the number of rows or columns is less 
   *                                  than 0 or if the interconnectivity 
   *                                  is less than 0 or if the treasure spread 
   *                                  is less than 0 or more than 0 or if the 
   *                                  random number generator is a null object
   */
  public MazeDungeon(int r, int c, int interconn, 
      boolean wrapping, double treasureSpread, int difficulty,
      RandomNumberGenerator rand) 
      throws IllegalArgumentException {
    if (r < 1 || c < 1) {
      throw new IllegalArgumentException("The number of rows and "
          + "columns must be greater than 0. Illegal Arguments.");
    }
    if (interconn < 0) {
      throw new IllegalArgumentException("Interconnectivity "
          + "must be atleast 1.");
    }
    if (treasureSpread < 0 || treasureSpread > 100) {
      throw new IllegalArgumentException("Treasure spread must "
          + "be a percentage value ranging from 0 to 100.");
    }
    if (difficulty < 1) {
      throw new IllegalArgumentException("At least one Otyugh must be "
          + "present to make the game adventurous.");
    }
    if (rand == null) {
      throw new IllegalArgumentException("Random Number Generator "
          + "must be of type RandomNumberGenerator.");
    }
    
    this.numOfRows = r;
    this.numOfCols = c;
    this.interconnectivity = interconn;
    this.isWrapped = wrapping;
    this.requiredTreasureSpread = treasureSpread;
    this.noOfMonsters = difficulty;
    this.monstersInDungeon = new ArrayList<>();
    this.areTreasuresAssigned = false;
    this.areArrowsAssigned = false;
    this.dungeonLayout = new ArrayList<>();
    this.adjacency = new ArrayList<>();
    this.dungeonConnections = new ArrayList<>();
    this.allLocations = new ArrayList<>();
    this.allPaths = new ArrayList<>();
    this.allCaves = new ArrayList<>();
    this.allTunnels = new ArrayList<>();
    this.start = null;
    this.end = null;
    this.random = rand;
    
    this.createDungeon();
  }
  
  /**
   * Create a dungeon with caves and tunnels and distribute the expected 
   * treasure.
   */
  private void createDungeon() {
    int locationId = 0;
    for (int i = 0; i < this.numOfRows; i++) {
      List<Integer> locationRow = new ArrayList<>();
      for (int j = 0; j < this.numOfCols; j++) {
        locationRow.add(locationId);
        locationId += 1;
      }
      this.dungeonLayout.add(locationRow);
    }
    this.generatePossibleConnections();
    this.finalDungeonPaths = this.selectPossibleConnections();
    this.assignCavesAndTunnels();
    this.assignNeighbors();
    this.distributeTreasures();
    this.distributeArrows();
    this.createMonsters();
  }
  
  private void createMonsters() {
    for (int i = 0; i < this.noOfMonsters; i++) {
      this.monstersInDungeon.add(new Otyugh(i + 1));
    }
  }
  
  /**
   * Generate the edges between vertexes of the maze graph. The wrapped maze 
   * will have border vertices connected to the opposite border. 
   */
  private void generatePossibleConnections() {
    Set<Set<Integer>> allPathsSet = new HashSet<>();
    
    // Assign paths for border and non-border elements
    for (int i = 0; i <= this.numOfRows - 1; i++) {
      for (int j = 0; j <= this.numOfCols - 1; j++) {
        // non-corner coordinates
        if (!(i == this.numOfRows - 1 && j == this.numOfCols - 1)) {
          // if border rows, connect self and right neighbor
          // if border columns, connect self and bottom neighbor
          // if non-border coordinates, connect self with right and bottom neighbors
          if (i == this.numOfRows - 1) {
            // i, j + 1
            allPathsSet.add(new HashSet<>(Arrays.asList(
                this.dungeonLayout.get(i).get(j), 
                this.dungeonLayout.get(i).get(j + 1))));
          } else if (j == this.numOfCols - 1) {
            // i + 1, j
            allPathsSet.add(new HashSet<>(Arrays.asList(
                this.dungeonLayout.get(i).get(j), 
                this.dungeonLayout.get(i + 1).get(j))));
          } else {
            // i + 1, j
            allPathsSet.add(new HashSet<>(Arrays.asList(
                this.dungeonLayout.get(i).get(j), 
                this.dungeonLayout.get(i + 1).get(j))));
                      
            // i, j + 1
            allPathsSet.add(new HashSet<>(Arrays.asList(
                this.dungeonLayout.get(i).get(j), 
                this.dungeonLayout.get(i).get(j + 1))));
          }
        }
      }
    }
    
    // Applying wrapped paths
    // Add more connections to border and corner coordinates
    if (this.isWrapped) {
      for (int i = 0; i < this.numOfRows; i++) {
        allPathsSet.add(new HashSet<>(Arrays.asList(
            this.dungeonLayout.get(i).get(this.numOfCols - 1), 
            this.dungeonLayout.get(i).get(0))));
      }
      for (int i = 0; i < this.numOfCols; i++) {
        allPathsSet.add(new HashSet<>(Arrays.asList(
            this.dungeonLayout.get(this.numOfRows - 1).get(i), 
            this.dungeonLayout.get(0).get(i))));
      }
    }
    
    // Add to all connections
    this.dungeonConnections = new ArrayList<>(allPathsSet);
  }
    
  /**
   * Randomly select possible connections (edges) from the graph, and 
   * create a maze. Follows KRUSKAL's ALGORITHM. If the new connection 
   * is not present in the set, it is added to the confirmed set, else it is 
   * added to the leftOver set to be used while applying interconnectivity.
   */
  private List<Set<Integer>> selectPossibleConnections() {
    Set<Set<Integer>> leftOverConnectionSet = new HashSet<>();
    Set<Set<Integer>> confirmedConnectionSet = new HashSet<>();
    
    List<Set<Integer>> allVertexSetList = this.createVertexSets();
    
    while (this.dungeonConnections.size() > 0) {
      // Randomly select a connection
      int randomPathNum = random.getRandomNumber(0, this.dungeonConnections.size());
      
      Set<Integer> randomPath = this.dungeonConnections.get(randomPathNum); 
      
      // Set 1 with the first vertex of the connection 
      Set<Integer> toSet = this.findSet(allVertexSetList, 
          (Integer) randomPath.toArray()[0]);
      // Set 2 with the second vertex of the connection 
      Set<Integer> fromSet = this.findSet(allVertexSetList, 
          (Integer) randomPath.toArray()[1]);
      
      // Check if same set
      if (toSet.equals(fromSet)) {
        leftOverConnectionSet.add(randomPath);
      } else {
        confirmedConnectionSet.add(randomPath);
        // Merge the sets
        Set<Integer> mergedSet = new HashSet<>();
        mergedSet.addAll(fromSet);
        allVertexSetList.remove(fromSet);
        mergedSet.addAll(toSet);
        allVertexSetList.remove(toSet);
        allVertexSetList.add(mergedSet);
      }
      // remove the selected connection from all connection set
      this.dungeonConnections.remove(randomPath);
    }
    
    List<Set<Integer>> leftOverPaths = new ArrayList<>(leftOverConnectionSet);
    List<Set<Integer>> confirmedPaths = new ArrayList<>(confirmedConnectionSet);
    return this.applyInterconnectivity(confirmedPaths, leftOverPaths);
  }
  
  /**
   * Create sets of individual vertices. 
   * 
   * @return List of vertices sets
   */
  private List<Set<Integer>> createVertexSets() {
    List<Set<Integer>> allVertexSetList = new ArrayList<>();
    for (int i = 0; i < this.numOfRows; i++) {
      for (int j = 0; j < this.numOfCols; j++) {
        Set<Integer> individualSet = new HashSet<>();
        individualSet.add(this.dungeonLayout.get(i).get(j));
        allVertexSetList.add(individualSet);
      }
    }
    return allVertexSetList;
  }
  
  /**
   * Find the set with the required location.
   *  
   * @param vertexSets all vertex sets
   * @param locationId required location id
   * @return the set with the location
   * @throws IllegalArgumentException if the location is not 
   *                                  present in the dungeon
   */
  private Set<Integer> findSet(List<Set<Integer>> vertexSets, 
      Integer locationId) throws IllegalArgumentException {
    for (Set<Integer> each : vertexSets) {
      if (each.contains(locationId)) {
        return each;
      }
    }
    throw new IllegalArgumentException("Location Id not present "
        + "in dungeon.");
  }
  
  /**
   * Find the connections with the location. 
   * 
   * @param curr required location id
   * @return List of set with the required location
   */
  private List<Set<Integer>> findConnectionsWithLocation(int curr) {
    List<Set<Integer>> pathsWithLocation = new ArrayList<>();
    for (Set<Integer> set : finalDungeonPaths) {
      if (set.contains(curr)) {
        pathsWithLocation.add(set);
      }
    }
    return pathsWithLocation;
  }
  
  /**
   * Apply interconnectivity by adding connections from the leftover batch 
   * to the connected set.
   * 
   * @param confirmed connected set
   * @param leftOverPaths leftover connections set
   * @return list of updated connected sets
   */
  private List<Set<Integer>> applyInterconnectivity(List<Set<Integer>> confirmed, 
      List<Set<Integer>> leftOverPaths) {
    int interconn = Math.min(leftOverPaths.size(), this.interconnectivity);
    while (interconn > 0) {
      int randomPathNum = random.getRandomNumber(0, leftOverPaths.size());
      
      Set<Integer> randomPath = leftOverPaths.get(randomPathNum);
      
      // No need to check sets here
      confirmed.add(randomPath);
      leftOverPaths.remove(randomPath);
      interconn -= 1;
    }
    return confirmed;
  }
  
  /**
   * Assign caves to vertices with 1, 3, or 4 entrances and tunnels with 2 
   * entrances.
   */
  private void assignCavesAndTunnels() {
    for (int i = 0; i < this.numOfRows; i++) {
      List<Location> dungeonRow = new ArrayList<>();
      for (int j = 0; j < this.numOfCols; j++) {
        int curr = this.dungeonLayout.get(i).get(j);
        List<Set<Integer>> currInPaths = this.findConnectionsWithLocation(curr);
        if (currInPaths.size() != 2) {
          Location cave = new Cave(curr, i, j);
          this.allCaves.add(cave);
          dungeonRow.add(cave);
        } else {
          Location tunnel = new Tunnel(curr, i, j);
          this.allTunnels.add(tunnel);
          dungeonRow.add(tunnel);
        }
      }
      this.allLocations.add(dungeonRow);
    }
  }
  
  /**
   * Assign neighbors to each location object by referring to the location id.
   */
  private void assignNeighbors() {
    for (int i = 0; i < this.numOfRows; i++) {
      for (int j = 0; j < this.numOfCols; j++) {
        int curr = this.dungeonLayout.get(i).get(j);
        List<Set<Integer>> currInPaths = this.findConnectionsWithLocation(curr);
        Location self;
        self = this.getLocationObject(curr);
        for (Set<Integer> path : currInPaths) {
          Set<Integer> copy = new HashSet<>(path);
          copy.remove(curr);
          Integer neighborId = (Integer) copy.toArray()[0];
          Location neighbor = this.getLocationObject(neighborId);
          int selfRow = self.getPosition()[0];
          int selfCol = self.getPosition()[1];
          int neighRow = neighbor.getPosition()[0];
          int neighCol = neighbor.getPosition()[1];
          Direction neighborDirection = this.getDirectionOfNeighbor(
              selfRow, selfCol, neighRow, neighCol);
          self.addNeighbor(neighborDirection, neighbor);
        }
      }
    }
  }
  
  /**
   * Get the location object for given location id.
   * 
   * @param curr location id
   * @return location object
   */
  private Location getLocationObject(int curr) {
    for (List<Location> row : this.allLocations) {
      for (Location each : row) {
        if (each.getLocationNum() == curr) {
          return each;
        }
      }
    }
    return null;
  }
  
  /**
   * Get the direction of neighbor to current location.
   * 
   * @param selfRow current location row
   * @param selfCol current location column
   * @param neighborRow neighbor location row
   * @param neighborCol neighbor location column
   * @return direction of neighbor
   */
  private Direction getDirectionOfNeighbor(int selfRow, int selfCol, 
      int neighborRow, int neighborCol) {
    if (neighborCol == selfCol) {
      if (neighborRow == selfRow - 1) {
        return Direction.NORTH;
      } else if (neighborRow == selfRow + 1) {
        return Direction.SOUTH;
      }
    } 
    if (neighborRow == selfRow) {
      if (neighborCol == selfCol - 1) {
        return Direction.WEST;
      } else if (neighborCol == selfCol + 1) {
        return Direction.EAST;
      }
    }
    if (this.isWrapped) {
      if (neighborRow == selfRow) {
        if (selfCol == 0 && neighborCol == this.numOfCols - 1) {
          return Direction.WEST;
        } else if (selfCol == this.numOfCols - 1 && neighborCol == 0) {
          return Direction.EAST;
        }
      }
      if (neighborCol == selfCol) {
        if (selfRow == 0 && neighborRow == this.numOfRows - 1) {
          return Direction.NORTH;
        } else if (selfRow == this.numOfRows - 1 && neighborRow == 0) {
          return Direction.SOUTH;
        }
      }
    }
    return null;
  }
  
  /**
   * Distribute treasures to the required percentage of caves.
   */
  private void distributeTreasures() {
    if (!this.areTreasuresAssigned) {
      int numberOfCavesWithTreasure = (int) Math.round(
          (this.requiredTreasureSpread / 100) * this.allCaves.size());
       
      List<Integer> selectedCaves = random.getUniqueRandomNumbersList(
          numberOfCavesWithTreasure, 0, this.allCaves.size());
      
      for (int i = 0; i < selectedCaves.size(); i++) {
        Location current = this.allCaves.get(selectedCaves.get(i));
        current.distributeTreasures(this.getRandomTreasure());
      }
    }
    this.areTreasuresAssigned = true;
  }
  
  /**
   * Get a random treasure to assigned.
   * 
   * @return Map of treasures and their count
   */
  private Map<Treasure, Integer> getRandomTreasure() {
    Map<Treasure, Integer> treasures = new HashMap<>();
    int count = random.getRandomNumber(0, 3) + 1;
    treasures.put(Treasure.DIAMONDS, count);
    count = random.getRandomNumber(0, 3) + 1;
    treasures.put(Treasure.RUBIES, count);
    count = random.getRandomNumber(0, 3) + 1;
    treasures.put(Treasure.SAPPHIRE, count);
    
    return treasures;
  }
  
  /**
   * Assign arrows to random locations in the dungeon.
   */
  private void distributeArrows() {
    if (!this.areArrowsAssigned) {
      int numberOfLocationsWithArrows = (int) Math.round(
          (this.requiredTreasureSpread / 100) 
          * (this.allLocations.size() * this.allLocations.get(0).size()));
      
      for (int i = 0; i < numberOfLocationsWithArrows; i++) {
        int row = random.getRandomNumber(0, this.allLocations.size());
        int col = random.getRandomNumber(0, this.allLocations.get(row).size());
        Location current = this.allLocations.get(row).get(col);
        int randomArrowCount = random.getRandomNumber(0, 3) + 1;
        current.addArrows(randomArrowCount);
      }
      this.areArrowsAssigned = true;
    }
  }
  
  /**
   * Assign neighbors to the adjacency 2-D matrix.
   */
  private void getAdjacency() {
    for (int i = 0; i < this.numOfRows; i++) {
      List<List<Location>> adjacencyRow = new ArrayList<>();
      for (int j = 0; j < this.numOfCols; j++) {
        int curr = this.dungeonLayout.get(i).get(j);
        Location self = this.getLocationObject(curr);
        List<Location> neighbors = new ArrayList<>(self.getNeighbors().values());
        adjacencyRow.add(neighbors);
      }
      this.adjacency.add(adjacencyRow);
    }
  }
  
  /**
   * Get all possible paths from point A to point B. Implemented a DFS algorithm.
   * 
   * @param start start location
   * @param end end location
   */
  private void getAllPossiblePaths(
      Location startLoc, Location endLoc) {
    this.getAdjacency();
    Boolean[] visited = new Boolean[(this.numOfRows * this.numOfCols) + 1];
    Arrays.fill(visited, Boolean.FALSE);
    List<Location> availablePath = new ArrayList<>();
    availablePath.add(startLoc);
    this.getAllPossiblePathsRecur(startLoc, endLoc, visited, availablePath);
  }
  
  /**
   * Recursively find paths between start and end location using the DFS 
   * algorithm.
   * 
   * @param start start location
   * @param end end location
   * @param visited boolean array of not visits
   * @param availablePath identified paths between start and end
   */
  private void getAllPossiblePathsRecur(Location startLoc, Location endLoc, 
      Boolean[] visited, List<Location> availablePath) {
    if (startLoc.equals(endLoc)) {
      List<Location> path = new ArrayList<>(availablePath);
      this.allPaths.add(path);
    } else {
      visited[startLoc.getLocationNum()] = true;
      
      int startRow = startLoc.getPosition()[0];
      int startCol = startLoc.getPosition()[1];
      
      for (Location neighbor : this.adjacency.get(startRow).get(startCol)) {
        
        // if not visited
        if (!visited[neighbor.getLocationNum()]) {
          availablePath.add(neighbor);
          this.getAllPossiblePathsRecur(neighbor, endLoc, visited, availablePath);
          availablePath.remove(neighbor);
        }
      }
      visited[startLoc.getLocationNum()] = false;
    }
  }
  
  @Override
  public Location getStart() {
    return this.start;
  }

  @Override
  public Location getEnd() {   
    return this.end;
  }
  
  @Override
  public void assignStartAndEnd() {
    while (true) {
      List<Integer> choices = random.getUniqueRandomNumbersList(2, 0, this.allCaves.size());
      
      Location startCave = this.allCaves.get(choices.get(0));
      Location endCave = this.allCaves.get(choices.get(1));
           
      this.allPaths.clear();
      this.getAllPossiblePaths(startCave, endCave);
      boolean flag = true;
      for (List<Location> path : this.allPaths) {
        if (path.size() < 5) {
          flag = false;
        }
      }
      if (flag) {
        this.start = startCave;
        this.end = endCave;
        break;
      }
    }
    this.addMonsters();
  }
  
  /**
   * Add monsters to the randomly selected caves. Monster cannot 
   * reside in the start cave.
   */
  private void addMonsters() {
    this.addDefaultMonster();
    List<Location> availableCavesForMonsters = new ArrayList<>();
    availableCavesForMonsters = this.allCaves;
    // Remove end cave
    availableCavesForMonsters.remove(this.end);
    // Remove start cave
    availableCavesForMonsters.remove(this.start);
    // Available caves
    if (this.monstersInDungeon.size() > 1) {
      for (int i = 1; i < this.monstersInDungeon.size(); i++) {
        int cave = this.random.getRandomNumber(0, availableCavesForMonsters.size() - 1);
        availableCavesForMonsters.get(cave).addMonster(this.monstersInDungeon.get(i));
        availableCavesForMonsters.remove(cave);
      }
    }
  }
  
  /**
   * Add default monster to the end cave.
   */
  private void addDefaultMonster() {
    if (this.end == null) {
      throw new IllegalStateException("The start and end caves must be "
          + "decided.");
    }
    if (this.monstersInDungeon.size() == 0) {
      throw new IllegalStateException("Create monsters before adding "
          + "them to the dungeon.");
    }
    // Add the zeroth monster to the end cave.
    this.end.addMonster(this.monstersInDungeon.get(0));
  }
  
  @Override
  public List<List<Location>> getAllLocations() {
    return this.allLocations;
  }

  @Override
  public List<Location> getCaves() {
    return this.allCaves;
  }

  @Override
  public List<Location> getTunnels() {
    return this.allTunnels;
  }

  @Override
  public String printDungeonLayout() {
    String layout = "";
    for (int i = 0; i < this.numOfRows; i++) {
      for (int j = 0; j < this.numOfCols; j++) {
        layout += this.dungeonLayout.get(i).get(j) + ", ";
      }
      layout += "\n";
    }
    return layout;
  }

  @Override
  public List<List<List<Location>>> getAllAdjacency() {
    this.getAdjacency();
    return this.adjacency;
  }
}
