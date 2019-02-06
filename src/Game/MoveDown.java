package Game;

import java.awt.Point;

public class MoveDown implements ItemMove {
	@Override
	public void move(Item item) {
		item.updatePosition(item.getX(), item.getY()+1);
	}

	@Override
	public Point predMove(Item item) {
		int x = item.getX();
		int y = item.getY();
		Point p = new Point(x,y+1);
		return p;
	}
}
