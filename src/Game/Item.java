/**
 * Common class for all objects
 */
package Game;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Item {
	/**
	 * x and y coordinates
	 */
	private int x;
	private int y;
	private ImageView image;
	/**
	 * Constructs an initial abstract class
	 * 
	 * @param x The initial x coordinate
	 * @param y The initial y coordinate
	 * 
	 * 
	 * need to add !!!
	 */
	public Item(int x, int y){
		this.x = x;
		this.y = y;
	}
	/**
	 * update the position of the object
	 * @param x The new x coordinate
	 * @param y The new y coordinate
	 * @return True if both of coordinates are successfully updated and false otherwise
	 */
	public void updatePosition(int x,int y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * The x coordinate of the item
	 * @return x coordinate
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * The y coordinate of the item
	 * @return y coordinate
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * Check if the item is a player
	 * @return true if yes
	 */
	
	public boolean isPlayer() {
		return false;
	}
	/**
	 * Check if the item is enemy
	 * @return true if yes
	 */
	public boolean isEnemy() {
		return false;
	}
	/**
	 * Check if the item is arrow
	 * @return true if yes
	 */
	public boolean isArrow() {
		return false;
	}
	/**
	 * Check if the item is bomb
	 * @return true if yes
	 */
	public boolean isBomb() {
		return false;
	}
	/**
	 * Check if the item is boulder
	 * @return true if yes
	 */
	public boolean isBoulder() {
		return false;
	}
	/**
	 * Check if the item is door
	 * @return true if yes
	 */
	public boolean isDoor() {
		return false;
	}
	/**
	 * Check if the item is exit
	 * @return true if yes
	 */
	public boolean isExit() {
		return false;
	}
	/**
	 * Check if the item is floor switch
	 * @return true if yes
	 */
	public boolean isFloorSwitch() {
		return false;
	}
	/**
	 * Check if the item is invincibility potion
	 * @return true if yes
	 */
	public boolean isInvincibilityPotion() {
		return false;
	}
	/**
	 * Check if the item is hover potion
	 * @return true if yes
	 */
	public boolean isHoverPotion() {
		return false;
	}
	/**
	 * Check if the item is key
	 * @return true if yes
	 */
	public boolean isKey() {
		return false;
	}
	/**
	 * Check if the item is pit
	 * @return true if yes
	 */
	public boolean isPit() {
		return false;
	}
	/**
	 * Check if the item is sword
	 * @return true if yes
	 */
	public boolean isSword() {
		return false;
	}
	/**
	 * Check if the item is treasure
	 * @return true if yes
	 */
	public boolean isTreasure() {
		return false;
	}
	/**
	 * Check if the item is wall
	 * @return true if yes
	 */
	public boolean isWall() {
		return false;
	}
	
	/**
	 * Get the image of the item
	 * @return ImageItem
	 */
    public ImageView getImage(){
        return image;
    }
    
    /**
     * Check if the item is pickable
     * @return true if yes
     */
    public boolean isPickable() {
    	return false;
    }
    /**
     * Set the ImageView
     * @param path the path of the image
     */
    public void setImage(String path){
    	try {
	    	if(this.image == null) {
		    	ImageView imageView = new ImageView();
		        imageView.setImage(new Image(path));
		        imageView.setFitHeight(30);
		        imageView.setFitWidth(33);
		        this.image = imageView;
	    	}
	    	else {
	    		this.image.setImage(new Image(path));
	    	}
    	}
    	catch(Exception e) {
    	}
    }
    /**
     * Replace the image with a new image
     * @param i new ImageView
     */
    public void setExtraImage(ImageView i) {
    	this.image = i;
    }
    
    public String getSymbol(){
        return "";
    }
}
