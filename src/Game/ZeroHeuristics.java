package Game;

import java.awt.Point;

public class ZeroHeuristics implements Strategy {
	/**
	 * this is the method to get the Path cost estimation which will contribute to the search algorithm
	 * this is a Zero Heuristics so returns 0, turning into a Greed Best first search algorithm
	 * @param route
	 * @return
	 */
	@Override
	public int pathCost(Integer[] Start, Integer[] End) {
		return 0;
	}
	/**
	 * Zero Heuristic to use as a test, and this scan equates that enemy always finds the player form the beginning
	 * @param player
	 * @param enemy
	 */
	@Override
	public void Scan(Player player, Enemy enemy) {
		enemy.setPlayerFound(true);
	}
	@Override
	public Route searchPlayer(Enemy enemy, Route startState, Route completeState, Map map) {
		if(enemy.isPlayerFound()) {
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
	@Override
	public int pathCost(Enemy enemy, Player player) {
		return 0;
	}
	@Override
	public String getImage() {
		return null;
	}
	@Override
	public String getSymbol() {
		return null;
	}

}
