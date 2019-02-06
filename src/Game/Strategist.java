package Game;

import java.awt.Point;

public class Strategist implements Strategy{
	/**
	 * this is the method to get the Path cost estimation which will contribute to the search algorithm
	 * @param route
	 * @return
	 */
	@Override
	public int pathCost(Integer[] Start, Integer[] End) {
		//Strategist gets a different goal coordinates to other class to get to the predicted coordinates
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
		for(int i = 0; i < 10; i++) {
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
			startState.setGoalXY(this.closestPrediction(enemy, map));
			
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
	
	private Point closestPrediction(Enemy thisEnemy, Map map){
		Point closestItem = new Point(thisEnemy.getX(),thisEnemy.getY());
		int closestD = map.getMapSize();
		for( int x=0 ; x < map.getMapSize() ; x++ ){
			for( int y=0 ; y < map.getMapSize() ; y++ ){
				Item stuff2Check = map.checkCoordinate(x, y);
				if(stuff2Check != null){
					if(!stuff2Check.isWall()&&!stuff2Check.isPlayer()&&!stuff2Check.isEnemy()){
						Integer[] itemCO = {stuff2Check.getX(),stuff2Check.getY()};
						Integer[] playerCO = {map.getPlayer().getX(),map.getPlayer().getY()};
						Integer[] thisCO = {thisEnemy.getX(),thisEnemy.getY()};
						int distance = this.pathCost(itemCO, playerCO)+this.pathCost(thisCO, playerCO);
						if(distance < closestD){
							closestItem = new Point(itemCO[0],itemCO[1]);
							closestD = distance;
						}
						//System.out.println(closestItem.getX()+"'-----------------------"+closestItem.getY());
					}
				}
			}
		}
		return closestItem;
	}
	@Override
	public String getImage() {
		return "Resource/Strategist.png";
	}
    @Override
    public String getSymbol(){
        return "?";
    }
}
