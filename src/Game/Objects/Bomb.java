package Game.Objects;

/**
 *The bomb is picked up by the player when they move into the square containing it.
 *The player can light the bombs they have collected and drop them. 
 *The fuse burns down for a short fixed period of time before the bomb explodes. 
 *Upon explosion, any boulders or enemies in the squares 
 *immediately to the left, right, above or below are destroyed. 
 *If the player is in one of these squares they die.
 */
public class Bomb extends PickableItem{

	private boolean isLight;
	private int countSecond;
	public Bomb(int x,int y) {
		super(x,y,PickableItemList.BOOM,1);
		this.countSecond = 5;
		this.setImage("Resource/bomb_unlit.png");
	}
	/**
	 * Check if the bomb is lighted
	 * @return
	 */
	public boolean isLight() {
		return isLight;
	}
	/**
	 * Change the light state of the bomb
	 * @param isLight
	 */
	public void setLight(boolean isLight) {
		this.isLight = isLight;
	}
	/**
	 * The remaining second of the bomb
	 * @return
	 */
	public int getSecond() {
		return this.countSecond;
	}
	/**
	 * Count down update the image
	 */
	public void countDown() {
		if(this.isLight)
			this.countSecond --;
		switch(this.countSecond) {
		case 1:this.setImage("Resource/bomb_lit_4.png");break;
		case 2: this.setImage("Resource/bomb_lit_3.png");break;
		case 3: this.setImage("Resource/bomb_lit_2.png");break;
		case 4: this.setImage("Resource/bomb_lit_1.png");break;
		}
	}
	
	@Override
	public boolean isBomb() {
		return true;
	}
	@Override
    public String getSymbol(){
        return "b";
    }
}
