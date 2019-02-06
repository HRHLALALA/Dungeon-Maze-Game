package Game;

import java.awt.Point;

/**
 * @Brief This is the Behavior class for the coward, a type of enemy that goes close to player but when close runs away
 * @author Eddie
 *
 */
public class Coward implements Strategy{
	
	private boolean Cowardice = false;
	
	public boolean isCowardice() {
		return Cowardice;
	}
	
	public void setCowardice(boolean cowardice) {
		Cowardice = cowardice;
	}
	
	@Override
	public int pathCost(Enemy enemy, Player player) {
		int a_2 = Math.abs(player.getX()-enemy.getX());
		int b_2 = Math.abs(player.getY()-enemy.getY());
		Double c_2 = Math.sqrt(a_2*a_2+b_2*b_2);
		return c_2.intValue();
	}
	
	/**
	 * this is the method to get the Path cost estimation which will contribute to the search algorithm
	 * @param route
	 * @return
	 */
	@Override
	public int pathCost(Integer[] Start, Integer[] End) {
		/**
		 * Basic heuristic, Hunter/Coward tries to get to the Player in the most straight forward behaviour
		 * checking for the way to get the closest
		 *	System should check if this value is greater than a value as this measure how close coward is from player
		 *	evoke retreat method when at certain values 
		 */
		int a_2 = Math.abs(End[0]-Start[0]);
		int b_2 = Math.abs(End[1]-Start[1]);
		Double c_2 = Math.sqrt(a_2*a_2+b_2*b_2);
		return c_2.intValue();
	}
	
	/**
	 * this is the enemy's detection method, a more unique application
	 * for the coward, they should have a bigger scan area, as they run towards player but runs away when close
	 * @param player get player object for location
	 * @param enemy get the enemy object's location
	 */
	@Override
	public void Scan(Player player, Enemy enemy) {
		for(int i = 0; i < 8; i++) {
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
	public Route searchPlayer(Enemy enemy,Route startState, Route completeState, Map map) {
		if(enemy.isPlayerFound()) {
			startState.setCurXY(new Point(enemy.getX(),enemy.getY()));
			startState.setGoalXY(new Point(map.getPlayer().getX(),map.getPlayer().getY()));
			int distance = this.pathCost( enemy, map.getPlayer() );
			if( distance < 5) {
				this.setCowardice(true);
			}else if( distance > 10) {
				this.setCowardice(false);
			}
			if(this.isCowardice()) {
				completeState = enemy.RetreatMethod(map.getPlayer(), map);
			}else {
				completeState = enemy.search4Path(startState, enemy.getStrategy());
			}
			
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
	
	@Override
	public String getImage() {
		return "Resource/Coward.png";
	}
	
    @Override
    public String getSymbol(){
        return "C";
    }
}
