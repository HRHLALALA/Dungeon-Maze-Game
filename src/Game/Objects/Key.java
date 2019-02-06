package Game.Objects;


import java.awt.Color;

/**
 * Can be picked up by the player when they move into the containing square. 
 * The player can carry only one key at a time, and only one door has a lock that fits the key. 
 * The key disappears once it is used to open its corresponding door.
 */
public class Key extends PickableItem {
	private Color color;
	public Key(int x, int y,Color c) {
		super(x,y,PickableItemList.KEY,1);
		this.color = c;
		if(this.color == Color.RED) this.setImage("Resource/red_key.png");
		else if(this.color == Color.YELLOW) YELLOW:this.setImage("Resource/yellow_key.png");
		else if(this.color == Color.BLUE) BLUE:this.setImage("Resource/blue_key.png");
	}
	/**
	 * 
	 * @return	the color of this key
	 */
	public Color getColor() {
		return this.color;
	}
	
	/**
	 * 
	 * @param door
	 * @return	true if the color of this key is the same as the color of the door to open
	 */
	public boolean useKey(Door door){
		if (door.getColor() != this.color) return false;
		door.openDoor();
		return true;
	}
	@Override
	public boolean isKey() {
		return true;
	}
    @Override
    public String getSymbol(){
        String ret = "k";
        if(this.color == Color.RED){
            ret = "k";
        }else if(this.color == Color.BLUE){
            ret = "8";
        }else if(this.color == Color.YELLOW){
            ret = "K";
        }
        return ret;
    }

}
