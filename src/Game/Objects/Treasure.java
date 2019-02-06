package Game.Objects;

/**
 * Can be collected by the player.
 */
public class Treasure extends PickableItem{

	public Treasure(int x, int y) {
		super(x, y,PickableItemList.TREASURE,Integer.MAX_VALUE);
		this.setImage("Resource/Treasure.png");
	}

	@Override
	public boolean isTreasure() {
		return true;
	}
    @Override
    public String getSymbol(){
        return "t";
    }
}
