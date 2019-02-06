package Game.Objects;

import Game.Item;

/**
 * If the player goes through an exit the puzzle is complete.
 */
public class Exit extends Item{

	public Exit(int x, int y) {
		super(x, y);
		this.setImage("Resource/exit.png");
	}

	@Override
	public boolean isExit() {
		return true;
	}
    @Override
    public String getSymbol(){
        return "e";
    }
}
