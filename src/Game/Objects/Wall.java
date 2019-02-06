package Game.Objects;

import Game.Item;
/**
 * Blocks the movement of the player, enemies, boulders and arrows.
 */

public class Wall extends Item{

	public Wall(int x, int y) {
		super(x, y);
		this.setImage("Resource/Wall.png");
	}

	@Override
	public boolean isWall() {
		return true;
	}
    @Override
    public String getSymbol(){
        return "#";
    }
}
