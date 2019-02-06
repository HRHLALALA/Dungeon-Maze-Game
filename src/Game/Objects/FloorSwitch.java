package Game.Objects;

import Game.Item;
import Game.Map;

/**
 * Switches behave like empty squares so other entities 
 * can appear on top of them. 
 * When a boulder is pushed onto a floor switch, it is 
 * triggered. 
 * Pushing a boulder off the floor switch untriggers it.
 * @author hrhlalala
 *
 */
public class FloorSwitch extends Item{
	
	// switch state
	private boolean isActivated;
	
	public FloorSwitch(int x, int y) {
		super(x, y);
		this.isActivated = false;
		this.setImage("Resource/floorswitch.png");
	}

	/*
	 * test switch state
	 */
	public boolean isActivated() {
		return isActivated;
	}

	/*
	 * flip switch state 
	 */
	public void setActivated(Boolean a) {
		this.isActivated = a;
	}
	
	public void updateState(Map map) {
		int x = this.getX();
		int y = this.getY();
		if(map.checkDynamicItems(x, y).isBoulder()) this.setActivated(true);;
		this.setActivated(false);
	}

	@Override
	public boolean isFloorSwitch() {
		return true;
	}
    @Override
    public String getSymbol(){
        return "s";
    }
}
