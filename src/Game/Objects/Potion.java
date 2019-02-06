package Game.Objects;

import Game.Player;
/**
 * All kinds of potion
 * @author hrhlalala
 *
 */
public abstract class Potion extends PickableItem{
	public Potion(int x,int y, PickableItemList type) {
		super(x,y,type,1);
	}
	/**
	 * player used this potion as a buff where it is stored in the player object
	 * @param p	player
	 */
	public void usePotion(Player p) {
		p.addBuff(this);
	}
}
