package Test;

import org.junit.Test;
import Game.GameSystem;
import Game.MoveDown;
import Game.MoveLeft;
import Game.MoveRight;
import Game.MoveUp;
import Game.Player;
import Game.Objects.Bomb;
import Game.Objects.HoverPotion;
import Game.Objects.InvincibilityPotion;
import Game.Objects.PickableItemList;
import Game.Objects.Sword;

public class TestPlayer {

	@Test
	public void testPickItem() {
	    GameSystem sys = new GameSystem();
        sys.initialiseMap("largeMap.txt");
        //Map map = sys.getMap();
		Player p = new Player(1 ,1);
		Sword s1 = new Sword(2, 2);
		p.pickItem(s1);
		assert(p.checkBagItemQuantity("SWORD") == 1);
	}
	@Test
	public void testMoveUp() {
	    GameSystem sys = new GameSystem();
        sys.initialiseMap("largeMap.txt");
        sys.getPlayer().updatePosition(2, 18);
		int oldX = sys.getPlayer().getX();
        int oldY = sys.getPlayer().getY();
        sys.movePlayer(new MoveUp());
        int newX = sys.getPlayer().getX();
        int newY = sys.getPlayer().getY();
        assert(oldX != newX || oldY != newY);
		
	}
	@Test
	public void testMoveDown() {
	    GameSystem sys = new GameSystem();
        sys.initialiseMap("largeMap.txt");
        sys.getPlayer().updatePosition(11, 7);
        int oldX = sys.getPlayer().getX();
        int oldY = sys.getPlayer().getY();
        sys.movePlayer(new MoveDown());
        int newX = sys.getPlayer().getX();
        int newY = sys.getPlayer().getY();
        assert(oldX != newX || oldY != newY);
	}
	@Test
	public void testMoveLeft() {
	    GameSystem sys = new GameSystem();
        sys.initialiseMap("largeMap.txt");
        sys.getPlayer().updatePosition(2, 18);
        int oldX = sys.getPlayer().getX();
        int oldY = sys.getPlayer().getY();
        sys.movePlayer(new MoveLeft());
        int newX = sys.getPlayer().getX();
        int newY = sys.getPlayer().getY();
        assert(oldX != newX || oldY != newY);
	}
	@Test
	public void testMoveRight() {
	    GameSystem sys = new GameSystem();
        sys.initialiseMap("largeMap.txt");
        sys.getPlayer().updatePosition(2, 18);
        int oldX = sys.getPlayer().getX();
        int oldY = sys.getPlayer().getY();
        sys.movePlayer(new MoveRight());
        int newX = sys.getPlayer().getX();
        int newY = sys.getPlayer().getY();
        assert(oldX != newX || oldY != newY);
	}
	
	@Test
	public void testInvincibilityPotion(){
	    GameSystem sys = new GameSystem();
        sys.initialiseMap("largeMap.txt");
		InvincibilityPotion invinP = new InvincibilityPotion(4, 5);
		Player p = sys.getPlayer();
		p.pickItem(invinP);
		p.usePotion(PickableItemList.INVINCIBILITYPOITION);
		assert(p.getBuff().contains(invinP));
	}
	
	@Test
	public void testInvincibilityPotion_Time(){
	    GameSystem sys = new GameSystem();
        sys.initialiseMap("largeMap.txt");
        HoverPotion hoverP = new HoverPotion(4, 5);
        Player p = sys.getPlayer();
        p.pickItem(hoverP);
        p.usePotion(PickableItemList.HOVERPOTION);
        assert(p.getBuff().contains(hoverP));
	}
	
	@Test
	public void testDropBomb() {
	    GameSystem sys = new GameSystem();
        sys.initialiseMap("largeMap.txt");
        Bomb bomb = new Bomb(4, 5);
        Player p = sys.getPlayer();
        p.pickItem(bomb);
        assert(p.checkBagItemQuantity("BOOM") == 1);
        assert(p.dropBomb());
        assert(p.checkBagItemQuantity("BOOM") == 0);
	}
}
