package Game.Objects;
import java.security.cert.Extension;

import Game.Player;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 *Gives the player the ability to
 *hover and fly over pits. 
 *This potion lasts until either 
 *the player is destroyed or the dungeon is complete.
 */
public class HoverPotion extends Potion {

	public HoverPotion(int x, int y) {
		super(x, y,PickableItemList.HOVERPOTION);
		this.setImage("Resource/hoverPotion.png");
	}

	@Override
	public boolean isHoverPotion() {
		return true;
	}
    @Override
    public String getSymbol(){
        return "2";
    }
}
