package winnningCondions;

import Game.Enemy;
import Game.Map;

public class EnemyCondition implements WinningCondition {
	/**
	 * Check the enemy winning condition
	 */
	@Override
	public boolean checkWin(Map map) {
		return !map.checkItems(Enemy.class);
	}

	@Override
	public int getWinningCode() {
		return 3;
	}


}
