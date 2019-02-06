package winnningCondions;

import Game.Map;
import Game.Objects.Treasure;

public class TreasureCondition implements WinningCondition {
	/**
	 * Check the treasure winning condition
	 */
	@Override
	public boolean checkWin(Map map) {
		return !map.checkItems(Treasure.class);
	}

	@Override
	public int getWinningCode() {
		return 4;
	}

}
