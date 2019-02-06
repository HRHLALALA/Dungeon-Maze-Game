package Game.Objects;


import java.awt.Point;

import Game.Item;
import Game.ItemMove;
import Game.Map;

/**
 * Acts like a wall in most cases.
 * The only differences are that it can be pushed by the player into adjacent squares 
 * and can be destroyed by a bomb. 
 * The player is only strong enough to push one boulder at a time.
 * @author hrhlalala
 *
 */
public class Boulder extends Item{
	public Boulder(int x, int y){
		super(x, y);
		this.setImage("Resource/boulder.png");
	}
	
	public boolean move(ItemMove move, Map map) {
		Point p = move.predMove(this);
		Item item = map.checkStaticItems(p.x, p.y);
		if(item !=null) {
			if(item.isWall()) return false;
			if(item.isDoor()) return false;
			if(item.isPit()) map.removeItem(this);
		}
		item = map.checkDynamicItems(p.x, p.y);
		if(item!=null && item.isBoulder()) return false;
		move.move(this);
		return true;
	}

	@Override
	public boolean isBoulder() {
		return true;
	}
    @Override
    public String getSymbol(){
        return "o";
    }
}
