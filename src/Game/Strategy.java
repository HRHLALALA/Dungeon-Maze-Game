package Game;

public interface Strategy {
	
	//heuristics estimation of arrival
	/**
	 * this is the method to get the Path cost estimation which will contribute to the search algorithm
	 * @param route
	 * @return
	 */
	public int pathCost(Integer[] Start, Integer[] End);
	public int pathCost(Enemy enemy, Player player);
	/**
	 * Scans for the player if it is within the vincinity
	 * @param player get player object for location
	 * @param enemy get the enemy object's location
	 */
	public void Scan(Player player, Enemy enemy);
	
	public Route searchPlayer(Enemy enemy, Route startState, Route completeState, Map map);
	public String getImage();
    public String getSymbol();
}
