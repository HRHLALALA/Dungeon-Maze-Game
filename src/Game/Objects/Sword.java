package Game.Objects;

import Game.Enemy;

/**
 * This can be picked up the player and used to kill enemies. 
 * Only one sword can be carried at once. 
 * Each sword is only capable of 5 hits and disappears after that. 
 * One hit of the sword is sufficient to destroy any enemy.
 * @author hrhlalala
 *
 */
public class Sword extends PickableItem{

	// how many times the sword can be used
	private int durability;
	
	public Sword(int x, int y) {
		// TODO Auto-generated constructor stub
		super(x, y, PickableItemList.SWORD, 1);
		this.durability = 5;
		this.setImage("Resource/Sword.png");

	}
	
	/**
	 * durability > 0
	 * @param player
	 * @return
	 */
	public boolean swing(Enemy target){
		if(target == null) return false;
		//target.setLifeState(false);
		return true;
	}

	/**
	 * 
	 * @return	how many times left can be used
	 */
	public int getDurability() {
		return durability;
	}

	/**
	 * 
	 * @param durability
	 */
	public void setDurability(int durability) {
		this.durability = durability;
	}

	@Override
	public boolean isSword() {
		return true;
	}
    @Override
    public String getSymbol(){
        return "l";
    }
}
