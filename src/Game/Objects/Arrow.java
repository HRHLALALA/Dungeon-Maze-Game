package Game.Objects;

import Game.Enemy;
import Game.Item;
import Game.ItemMove;
import Game.Map;

/**
 *Can be collected by the player and at will either left, 
 *right, up or down. Enemies are destroyed if they are hit with an arrow.
 *Arrows are destroyed upon collision with anything.
 *There is no limit to how many arrows the player can carry.
 */
public class Arrow extends PickableItem {
	public Arrow(int x,int y) {
		super(x,y,PickableItemList.ARROW, Integer.MAX_VALUE);
		this.setImage("Resource/arrow.png");
	}

	@Override
	public boolean isArrow() {
		return true;
	}
	
	/**
	 * Shoot the enemy on the map
	 * @param itemMove the shoot direction
	 * @param map the map
	 */
	public void shoot(ItemMove itemMove, Map map) {
		while(this.getX() < map.getMapSize()-1 && this.getY() < map.getMapSize()-1) {
			itemMove.move(this);
			Item item = map.checkDynamicItems(this.getX(), this.getY());
			if(item==null) continue;
			if(item.isEnemy()) {
				Enemy e = (Enemy) item;
				map.removeItem(e);
				return;
			}
			if(item.isBoulder()) return;
			item = map.checkStaticItems(this.getX(), this.getY());
			if(item!=null && item.isWall() ) return;
		}

	}
    @Override
    public String getSymbol(){
        return "a";
    }
}
