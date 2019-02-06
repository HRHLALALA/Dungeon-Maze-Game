package Game.Objects;

import Game.Item;
/**
 * 
 * All pickable items
 *
 */
public abstract class PickableItem extends Item {

	// enumeration of pickable item
	private PickableItemList type;
	private int capacity;
	
	public PickableItem(int x, int y, PickableItemList type, int capacity) {
		super(x, y);
		this.type = type;
		this.capacity = capacity;
	}

	/**
	 * @return	type of the pickable item
	 */
	public PickableItemList getType() {
		return type;
	}

	/**
	 * set enumeration type
	 * @param type
	 */
	public void setType(PickableItemList type) {
		this.type = type;
	}
	
	/**
	 * 
	 * @return the maximum quantity of this item
	 */
	public int getCapacity() {
		return this.capacity;
	}
	
	/**
	 *	@return if the item is pickable
	 */
	@Override
	public boolean isPickable() {
		return true;
	}
}
