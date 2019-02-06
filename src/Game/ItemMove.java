package Game;

import java.awt.Point;

public interface ItemMove {
	public Point predMove(Item item);
	public void move(Item item);
}
