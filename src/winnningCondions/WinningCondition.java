package winnningCondions;

import Game.Map;

public interface WinningCondition {
	public boolean checkWin(Map map);
	public int getWinningCode();
}
