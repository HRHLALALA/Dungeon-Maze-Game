package Game.Objects;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * If the player picks this up they become invincible to all bombs and enemies. 
 * Colliding with an enemy should result in their immediate destruction. 
 * Because of this, all enemies will run away from the player when they are invincible. 
 */
public class InvincibilityPotion extends Potion{
	// timer
	private int second;
	
	public InvincibilityPotion(int x, int y) {
		super(x, y,PickableItemList.INVINCIBILITYPOITION);
		this.second = 20;
		this.setImage("Resource/invinciblePotion.png");
	}

	/**
	 * 
	 * @return	how many seconds left
	 */
	public int getSecond() {
		return this.second;
	}
	
	/**
	 * 
	 * @return	false if buffer time out
	 */
	public boolean reduceTime() {
		if(this.second == 0) return false;
		this.second --;
		return true;
	}

	@Override
	public boolean isInvincibilityPotion() {
		return true;
	}
    @Override
    public String getSymbol(){
        return "1";
    }
}
