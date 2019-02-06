package Game;

import java.awt.Point;

public class MoveLeft implements ItemMove {
	@Override
	public void move(Item item) {
		item.updatePosition(item.getX()-1, item.getY());
	}

	@Override
	public Point predMove(Item item) {
		int x = item.getX();
		int y = item.getY();
		Point p = new Point(x-1,y);
		return p;
	}
}
