package winnningCondions;


import java.util.ArrayList;

import Game.Item;
import Game.Map;
import Game.Objects.FloorSwitch;

public class SwitchCondition implements WinningCondition {

	@Override
	public boolean checkWin(Map map) {
		ArrayList<Item> s = map.getItems(FloorSwitch.class);
		if(s.isEmpty()) return false;
		for(Item swi :s) {
			if(!((FloorSwitch)swi).isActivated()) {
				return false;
			}
		}
		return true;
		
	}

	@Override
	public int getWinningCode() {
		return 2;
	}

}
