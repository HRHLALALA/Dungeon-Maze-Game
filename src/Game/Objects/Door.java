package Game.Objects;


import java.awt.Color;
import Game.Item;

/**
 * Exists in conjunction with a single key that can open it.
 * If the player holds the key, they can open the door by moving through it. 
 * After opening it remains open. 
 */
public class Door extends Item{
	// door state
	private boolean isBlocked;
	// door color
	private Color color;
	
	public Door(int x, int y,Color color) {
		super(x, y);
		this.isBlocked = true;
		this.color = color;
		if(this.color == Color.RED) this.setImage("Resource/red_door.png");
		else if(this.color == Color.YELLOW) YELLOW:this.setImage("Resource/yellow_door.png");
		else if(this.color == Color.BLUE) BLUE:this.setImage("Resource/blue_door.png");
	}

	/**
	 * 
	 * @return	test the state of the door
	 */
	public boolean isBlocked() {
		return isBlocked;
	}

	/**
	 * 
	 * @return	the color of the door
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * open door if door is blocked
	 */
	public void openDoor() {
		if(!isBlocked) return;
		else this.isBlocked = !this.isBlocked;
		this.setImage("Resource/open_door.png");
	}
	/**
	 * Check if the key is fitted in this door
	 * @return true if fits
	 */
	public boolean keyFitness(Key k) {
		return (k.getColor() == this.getColor());
	}

	@Override
	public boolean isDoor() {
		return true;
	}
    @Override
    public String getSymbol(){
        String ret = "d";
        if(this.color == Color.RED){
            ret = "d";
        }else if(this.color == Color.BLUE){
            ret = "9";
        }else if(this.color == Color.YELLOW){
            ret = "D";
        }
        return ret;
    }
}
