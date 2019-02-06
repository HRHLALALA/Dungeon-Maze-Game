package Game;

import java.awt.Point;

public class Hunter implements Strategy{
    
	/**
	 * this is the method to get the Path cost estimation which will contribute to the search algorithm
	 * @param route
	 * @return
	 */
	@Override
	public int pathCost(Integer[] Start, Integer[] End) {
		/**
		 * Basic heuristic, Hunter tries to get to the Player in the most straight forward behaviour
		 * checking for the way to get the closest
		 */
		int a_2 = Math.abs(End[0]-Start[0]);
		int b_2 = Math.abs(End[1]-Start[1]);
		Double c_2 = Math.sqrt(a_2*a_2+b_2*b_2);
		return c_2.intValue();
	}
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
		for(int i = 0; i < 10; i++) {
			if(player.getX() == enemy.getY()+i) {
				enemy.setPlayerFound(true);
			}
			if(player.getY() == enemy.getY()+i) {
				enemy.setPlayerFound(true);
			}
			if(player.getX() == enemy.getY()-i) {
				enemy.setPlayerFound(true);
			}
			if(player.getY() == enemy.getY()-i) {
				enemy.setPlayerFound(true);
			}
		}
	}
	
	@Override
	public Route searchPlayer(Enemy enemy,Route startState, Route completeState, Map map) {
		if(enemy.isPlayerFound()) {
			assert(map.getPlayer()!=null);
			startState.setCurXY(new Point(enemy.getX(),enemy.getY()));
			startState.setGoalXY(new Point(map.getPlayer().getX(),map.getPlayer().getY()));
			
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
	
	public String getImage(){
        return "Resource/Hunter.png";
    }
    @Override
    public String getSymbol(){
        return "H";
    }

}
