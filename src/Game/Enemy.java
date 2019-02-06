package Game;

import java.awt.Point;
import java.util.ArrayList;
import java.util.PriorityQueue;

import Game.Objects.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * @brief The movement and behaviour of the system controlled units(Enemies of the player)
 * @author Eddie
 *
 */
public class Enemy extends Item{

	private Strategy strategy;
	private boolean playerFound;
	private ArrayList<Integer[]> walkable;
	private Point prevCo;
	/**
	 * Constructor for the class, initially, alive status and haven't seen player yet, declare the type(strategy) and it's pace
	 * @param x	coord of enemy spawn
	 * @param y	coord of enemy spawn
	 * @param speed of enemy
	 * @param strategy type of enemy to be spawned
	 */
	//assumption that when create object, this object is true hence is alive
	public Enemy(int x,int y, Strategy strategy) {
		super(x,y);
		this.setStrategy(strategy);
		this.setPlayerFound(false);
		this.setImage(strategy.getImage());
		this.prevCo = new Point(0,0);
	}
	
	public boolean isEnemy() {
		return true;
	}
	
	//getters and setters
	public boolean isPlayerFound() {
		return playerFound;
	}

	public void setPlayerFound(boolean playerFound) {
		this.playerFound = playerFound;
	}

	public Strategy getStrategy() {
		return strategy;
	}

	public void setStrategy(Strategy strategy) {
		this.strategy = strategy;
	}
	
	/**
	 * just need to update the backend where the monster is moving to, frontend/system handles everything else
	 * @param x monster x coordinates	
	 * @param y monster y coordinates
	 */
	public void move(Map map,int x,int y) {

		prevCo = new Point(this.getX(),this.getY());
        map.reloadItems();
		Item stuff = map.checkCoordinate(x, y);
		if(stuff != null) {
			if(!stuff.isEnemy()) {
				this.updatePosition(x, y);
			}
		}else {
			this.updatePosition(x, y);
		}
		this.combatCheck(map);
	}
	
	/**
	 * checks if enemy and player is same space
	 */
	public void combatCheck(Map map){
		if(map.getPlayer().getX()==this.getX() && map.getPlayer().getY()==this.getY()) {
    		if(map.getPlayer().swordAttack(this)) {
    			map.reloadItems();
    			map.removeItem(this);
    		}else {
    			map.getPlayer().setAlive(false);
    		}
    	}
	}
	
	/**
	 * test method to see if the walkable array is as expected outputs
	 */
	public void printWalkable() {
		for(Integer[] co:this.walkable) {
			System.out.println("{"+co[0]+","+co[1]+"}");
		}
	}
	public int walkableSpaceSize() {
		return this.walkable.size();
	}
	
	/**
	 * Run this after initialized a monster on map, this teaches the monster where he can walk and any coord not in list he shouldn't walk
	 * Uses the Map object, requests for the Map size then iterate over every grid of the map, checks the object on the map 
	 * if the object is of certain classes(boulder, pit, wall or closed doors) then it won't add the coordinates into the List. 
	 * @param grid Map Class
	 * @return arrayList of integer[]
	 */
	public void walkableSpace(Map grid) {
		ArrayList<Integer[]> walkableCo = new ArrayList<Integer[]>();
		for(int x = 0; x < grid.getMapSize();x++) {
			for(int y = 0; y < grid.getMapSize(); y++) {
				
				Item stuff2Check = grid.checkCoordinate(x, y);
				if(stuff2Check != null) {
					if(stuff2Check.isBoulder() || stuff2Check.isPit() || stuff2Check.isWall() 
							|| stuff2Check.isExit()||stuff2Check.isPit() ||stuff2Check.isEnemy()
							|| stuff2Check.isWall() ||stuff2Check.isExit()) {
						continue;
					}
					else if(stuff2Check.isDoor()) {
						Door checkDoor = (Door)stuff2Check;
						if(checkDoor.isBlocked()) {
							continue;
						}
					}
				}
				Integer[] coordinates= {x,y};
				walkableCo.add(coordinates);

			}
		}
		this.walkable = walkableCo;
	}
	
	/**assumption of that all the enemy has same scan area
	 * scan if the player is within the vincinity of the enemy(7blocks x and y area)
	 * so compare the player co ordinates with monster's coordinates if it's within 5 blocks
	 * The state of playerFound will be set true and the monster will chase according to the algorithm
	 * @return is void, as we need to change enemy only, public as system to scan for player 
	 */
	public void scan(Player player) {
		this.strategy.Scan(player, this);
	}
	
	/**
	 * searchPlayer is the wrapper method which returns a path for the enemy to find the player
	 * This method should always return an ArrayList of coords that reaches player unless the path is literally unreachable
	 * @param player passes the player coordinates to enemy's logic
	 * @return Route a state object which contains a path log which reaches the player 
	 */
	//search Greed best first algorithm, implement strategy for different enemies' behaviours
	//will implement heuristics to the algorithm to implement A* search for the player, the pattern exists but patterns are not marked i was informed 
	public Route searchPlayer(Map map) {
		// search path to player
		Route completeState = null;
		Route startState = new Route();
		completeState = this.strategy.searchPlayer(this,startState,completeState,map);
		return completeState;
		//move(x, y) implement search algorithm and then return the latest next ideal move to player then pass onto move
	}
	
	public void makeStayState(Route state) {
		state.setCurXY(new Point(getX(),getY()));
		ArrayList<Integer[]> stay = new ArrayList<Integer[]>();
		Integer[] num = {this.getX(),this.getY()};
		stay.add(num);
		stay.add(num);
		stay.add(num);
		state.setPathLog(stay);
	}
	
	/**
	 * This is the private method searches for the path from the current location to the player for the enemy
	 * this is only for searchPlayer to find the path, the method is here to be more clear and refractorable later on
	 * @param startRoute
	 * @param strategy type of enemy with their own search pattern
	 * @return returns an object with arraylist of coordinates to walk in
	 */
	protected Route search4Path(Route startRoute, Strategy strategy) {
		PriorityQueue<Route> toBeExpanded = new PriorityQueue<Route>();
		toBeExpanded.add(startRoute);
		ArrayList<Integer[]> visitedList = new ArrayList<Integer[]>();
		while(!toBeExpanded.isEmpty()) {
			Route visitSpace = toBeExpanded.poll();
			//visitSpace.printCurCo();
			if(visitSpace.isGoalState()) {
				visitSpace.getPathLog().add( visitSpace.getCurXY());
				visitSpace.getPathLog().add( visitSpace.getCurXY());
				return visitSpace;
			}
			ArrayList<Route> futureList = new ArrayList<Route>();
			

			this.expandRoute(futureList, visitSpace, strategy);
			
			for(Route childrenState : futureList) {
				boolean ifExist = false;
				Integer[] childCoord= childrenState.getCurXY();
				for(Integer[] visitedCoord : visitedList) {
					if(childCoord[0].equals(visitedCoord[0]) && childCoord[1].equals(visitedCoord[1])) {
						ifExist = true;
					}
				}
				if(ifExist == false) {
					toBeExpanded.add(childrenState);
					visitedList.add(childrenState.getCurXY());
				}
			}
		}
		return null;	//incase I don't have a goalState but reaches empty queue
	}
	
	/**
	 * state pattern expanding state, part of the Search for player algorithm, recurses until route's coord reaches player coord
	 * this won't go out of bound as the grid space always exist defined by walkable, when initialise walkable it bases on the map.size
	 * and this is a comparison, if if this exists in walkable.
	 * @param futureList	
	 * @param visitedSpace	the coord that is currently being expanded
	 * @param strategy		the behaviour of the search
	 */
	private void expandRoute(ArrayList<Route> futureList, Route visitSpace, Strategy strategy) {
		Integer[] currentCo = visitSpace.getCurXY();
		Integer[] endCo	= visitSpace.getGoalXY();
		//update current cost as new cost is need in next move
		Integer travelCost = visitSpace.getCurrentCost() + 1;
		Integer atravelCost = travelCost;
		//make new states and add current CO to new states
		ArrayList<Integer[]> curPath = visitSpace.getPathLog();
		curPath.add(currentCo);
		
		for(Integer[] space : this.walkable) {
			
			//north
			if(space[0].equals(currentCo[0])&&space[1].equals(currentCo[1]-1)) {
				if(prevCo.getX()==currentCo[0]&& prevCo.getY()==currentCo[1]-1){
					continue;
				}
				Integer[] northCo = {space[0],space[1]};
				atravelCost += strategy.pathCost(northCo, endCo);
				Route path1 = makeRoute(northCo, endCo, travelCost, atravelCost, curPath);
				futureList.add(path1);
			}
			//south
			if(space[0].equals(currentCo[0])&&space[1].equals(currentCo[1]+1)) {
				if(prevCo.getX()==currentCo[0]&& prevCo.getY()==currentCo[1]+1){
					continue;
				}
				Integer[] southCo = {space[0],space[1]};
				atravelCost += strategy.pathCost(southCo, endCo);
				Route path2 = makeRoute(southCo, endCo, travelCost, atravelCost, curPath);
				futureList.add(path2);
			}
			//east
			if(space[0].equals(currentCo[0]+1)&&space[1].equals(currentCo[1])) {
				if(prevCo.getX()==currentCo[0]+1&& prevCo.getY()==currentCo[1]){
					continue;
				}
				Integer[] eastCo = {space[0],space[1]};
				atravelCost += strategy.pathCost(eastCo, endCo);
				Route path3 = makeRoute(eastCo, endCo, travelCost, atravelCost, curPath);
				futureList.add(path3);
			}
			//west
			if(space[0].equals(currentCo[0]-1)&&space[1].equals(currentCo[1])) {
				if(prevCo.getX()==currentCo[0]-1&& prevCo.getY()==currentCo[1]){
					continue;
				}
				Integer[] westCo = {space[0],space[1]};
				atravelCost += strategy.pathCost(westCo, endCo);
				Route path4 = makeRoute(westCo, endCo, travelCost, atravelCost, curPath);
				futureList.add(path4);
			}
		}
	}
	/**
	 * makes a state object for the state pattern in enemy
	 * @param Start	start state
	 * @param End	end state
	 * @param curCost	cost of the state currently
	 * @param aCost	heuristic cost
	 * @param path	actual path of the state
	 * @return	returns a state that's completed the path
	 */
	private Route makeRoute(Integer[] Start, Integer[] End, Integer curCost, Integer aCost, ArrayList<Integer[]>path) {
		Route pathObj = new Route();
		pathObj.setCurXY(Start);
		pathObj.setGoalXY(End);
		pathObj.setCurrentCost(curCost);
		pathObj.setaSearchCost(aCost);
		pathObj.addPathLog(path);
		return pathObj;
	}
	
	/**
	 * gives the enemy a retreating behaviour
	 * @param player find the player 
	 * @param map	gets the map to know where everything is
	 * @return returns a state that has a point away from the player
	 */
	public Route RetreatMethod(Player player, Map map) {
		// search path to player
		Route startRoute = new Route();
		startRoute.setCurXY(new Point(getX(),getY()));
			// change this to a strategy based(class based goal coord) is possible then we need to change uml can do this sunday
		Integer RX = map.getMapSize()-player.getX()-1;
		Integer RY = map.getMapSize()-player.getX()-1;
		Integer[] RCo = {RX,RY};
		RCo = this.findWalkable(RCo, map.getMapSize()); 
		startRoute.setGoalXY(RCo);
			//inorder to make so i can take out search Algo method into it's own class in the future perhaps
		Route retreatState = this.search4Path(startRoute, this.getStrategy());
		if(retreatState == null) {
			retreatState = new Route();
			this.makeStayState(retreatState);
		}
		return retreatState;
		//move(x, y) implement search algorithm and then return the latest next ideal move to player then pass onto move
	}
	
	/**
	 * scans the entire map for coordinates that can the enemy object can walk on
	 * @param RCO	the coordinates which is required
	 * @param size map size to check the entire map 
	 * @return	returns the closest coordinates to the points inquired
	 */
	public Integer[] findWalkable(Integer[] RCO, int size) {
		Integer coDis = size;
		int X = 0;
		int Y = 0;
		//pythagoras's theorem
		for( Integer[] CO:this.walkable) {
			int a_2 = (RCO[0]-CO[0])*(RCO[0]-CO[0]);
			int b_2 = (RCO[1]-CO[1])*(RCO[1]-CO[1]);
			Double c = Math.sqrt(a_2+b_2);
			if(c < coDis) {
				X = CO[0];
				Y = CO[1];
				coDis = c.intValue();
			}
		}
		Integer[] walkableCO = {X,Y};
		return walkableCO;
	}
    @Override
    public String getSymbol(){
        return strategy.getSymbol();
    }
}
