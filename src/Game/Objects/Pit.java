package Game.Objects;

import Game.Item;

/**
 * If the player falls into a pit they die. 
 * Boulders pushed into a pit disappear.
 */
public class Pit extends Item {
	
	private boolean isDroppable;
	
	public Pit(int x, int y) {
		super(x, y);
		this.isDroppable = true;
		this.setImage("Resource/Pit.png");
	}

	/**
	 * 
	 * @return	true if no boulder dropped to this pit
	 */
	public boolean isDroppable() {
		return isDroppable;
	}
	
	/**
	 * set the state of this pit
	 * @param state
	 */
	public void setIsDroppable(boolean state) {
		this.isDroppable = state;
	}
	
	@Override
	public boolean isPit() {
		return true;
	}
	    @Override
    public String getSymbol(){
        return "p";
    }
}
