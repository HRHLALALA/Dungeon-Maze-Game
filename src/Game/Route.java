package Game;

import java.awt.Point;
import java.util.ArrayList;

/**
 * this is the Route finding class implemented in order to find the optimal path towards the player 
 * @author Eddie
 *
 */
public class Route implements Comparable<Route>{
	private Integer[] curXY;
	private Integer[] goalXY;
	private int aSearchCost;
	private int currentCost;
	private ArrayList<Integer[]> pathLog;
	/**
	 * Constructor, initialises everything to be the base
	 */
	public Route() {
		this.curXY = null;
		this.goalXY = null;
		this.aSearchCost = 0;
		this.currentCost = 0;
		this.pathLog = new ArrayList<Integer[]>();
	}
	//GETTERS AND SETTERS
	public boolean isGoalState() {
		if(curXY[0].equals(goalXY[0]))
			if(curXY[1].equals(goalXY[1]))
				return true;
		return false;
	}
	
	public Integer[] getGoalXY() {
		return goalXY;
	}
	
	public void setGoalXY(Point point) {
		Integer[] num = {point.x,point.y};
		this.goalXY = num;
	}
	
	public void setGoalXY(Integer[] goalXY) {
		this.goalXY = goalXY;
	}
	
	public Integer[] getCurXY() {
		return curXY;
	}

	public void setCurXY(Point point) {
		Integer[] num = {point.x,point.y};
		this.curXY = num;
	}
	
	public void setCurXY(Integer[] curXY) {
		this.curXY = curXY;
	}

	public int getaSearchCost() {
		return aSearchCost;
	}

	public void setaSearchCost(int aSearchCost) {
		this.aSearchCost = aSearchCost;
	}

	public int getCurrentCost() {
		return currentCost;
	}

	public void setCurrentCost(int currentCost) {
		this.currentCost = currentCost;
	}

	/**
	 * gets the PathLog which is the entire path Route that the state traversed in
	 * @return ArrayList of int[]
	 */
	public ArrayList<Integer[]> getPathLog() {
		return pathLog;
	}

	public void setPathLog(ArrayList<Integer[]> pathLog) {
		this.pathLog = pathLog;
	}
	/**
	 * adds a deep copy of the list to the object instead of a shallow copy
	 * @param pathLog
	 */
	public void addPathLog(ArrayList<Integer[]> pathLog) {
		for(Integer[] CO:pathLog) {
			this.pathLog.add(CO);
		}
	}

	/**
	 * comparable class implementation, the compareTo method allows for a priority queue implementation of the state which looks for the route to reach the player
	 */
	@Override
	public int compareTo(Route arg0) {
		return this.aSearchCost - arg0.aSearchCost;
	}
	//current coordinate to string
	public void printCurCo() {
		System.out.println(this.curXY[0]+ "," +this.curXY[1]);
	}
	//current coordinate to string
	public void printGoalCo() {
		System.out.println(this.goalXY[0]+ "," +this.goalXY[1]);
	}

}
