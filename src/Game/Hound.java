package Game;

import java.awt.Point;

public class Hound implements Strategy{
	
	
	/**
	 * this is the method to get the Path cost estimation which will contribute to the search algorithm
	 * @param route
	 * @return
	 */
	@Override
	public int pathCost(Integer[] Start, Integer[] End) {
		int a_2 = Math.abs(End[0]-Start[0]);
		int b_2 = Math.abs(End[1]-Start[1]);
		Double c_2 = Math.sqrt(a_2*a_2+b_2*b_2);
		return c_2.intValue();
	}
	@Override
	public int pathCost(Enemy enemy, Player player) {
		int a_2 = Math.abs(player.getX()-enemy.getX());
		int b_2 = Math.abs(player.getY()-enemy.getY());
		Double c_2 = Math.sqrt(a_2*a_2+b_2*b_2);
		return c_2.intValue();
	}
	
	
	/**
	 * Every Scan strategy is similar(if not just same) to each other except for Coward which has a bigger range to check for player
	 * @param player get player object for location
	 * @param enemy get the enemy object's location
	 */
	@Override
	public void Scan(Player player, Enemy enemy) {
		for(int i = 0; i < 5; i++) {
			if(player.getX() == enemy.getX()+i) {
				enemy.setPlayerFound(true);
			}
			if(player.getY() == enemy.getY()+i) {
				enemy.setPlayerFound(true);
			}
			if(player.getX() == enemy.getX()-i) {
				enemy.setPlayerFound(true);
			}
			if(player.getY() == enemy.getY()-i) {
				enemy.setPlayerFound(true);
			}
		}
	}
	
	
	@Override
	public Route searchPlayer(Enemy enemy, Route startState, Route completeState, Map map) {
		if(enemy.isPlayerFound()) {
			startState.setCurXY(new Point(enemy.getX(),enemy.getY()));
			//change this into find the closest hunter to the player
			
			startState.setGoalXY(this.findHunter(enemy, map));
			
			//in order to make so i can take out search Algo method into it's own class in the future perhaps
			completeState = enemy.search4Path(startState, enemy.getStrategy());
			if(completeState == null) {
				completeState = new Route();
				enemy.makeStayState(completeState);
			}
		}else {
			//when player is not found then wander around 2-3 space
			completeState = new Route();
			enemy.makeStayState(completeState);
		}
		return completeState;
	}
	
	//to find the position to the player and closest hunter, to close him off, use find walkable in opposite of the hunter with player as midpoint 
	/*basically use similar to pythagoras, find the distance between the hunter and player then use that to add onto the player's coordinates
	 * it should return an inverse position which i can then evoke findWalkable() to find a opposite spacing, and the closer the hunter gets
	 * the closer the hound should get to from the other side thus making a pseudo trapping method*/
	private Point findHunter(Enemy thisHound, Map map) {
		//find the closest hunter to the player
		Point sandwichCO = null;
		Enemy helpTarget = null;
		double closestDist = map.getMapSize();
		
		for(int x = 0; x<map.getMapSize();x++) {
			for(int y = 0; y < map.getMapSize(); y++) {
				Item findHunters = map.checkCoordinate(x, y);
				if(findHunters == null) {
					continue;
				}
				if(findHunters.isEnemy()) {
					Enemy enemyObj = (Enemy) findHunters;
					if(enemyObj.getStrategy() instanceof Hunter) {
						//check distance to player
						double distance = enemyObj.getStrategy().pathCost(enemyObj, map.getPlayer());
						if(distance < closestDist) {
							closestDist = distance;
							helpTarget = enemyObj;		//if the enemy is closer to current obj then go help that hunter
						}
					}
				}
				
			}
		}
		//helpTarget located then now find the mirror position of hunter to the player to sandwich the player
		if(helpTarget != null) {
			int DistanceX = helpTarget.getX() - map.getPlayer().getX();
			int DistanceY = helpTarget.getY() - map.getPlayer().getY();
			int helpCoX = map.getPlayer().getX() - DistanceX;
			int helpCoY = map.getPlayer().getY() - DistanceY;
			Integer[] oppositeCO = {helpCoX,helpCoY};

			Integer []oppositeCO1 = thisHound.findWalkable(oppositeCO, map.getMapSize());
			sandwichCO = new Point(oppositeCO1[0],oppositeCO1[1]);
		}
		
		if(sandwichCO == null || helpTarget == null) {
			//this will just make the hound stay at same place as there is no hunter to help, a hound would not know what to do
			sandwichCO = new Point(thisHound.getX(),thisHound.getY());
		}
		return sandwichCO;
	}
	@Override
	public String getImage() {
		return "Resource/hound.png";
	}
	@Override
    public String getSymbol(){
        return "h";
    }
}
