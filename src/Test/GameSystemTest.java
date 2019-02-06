package Test;

import org.junit.jupiter.api.Test;
import java.awt.Color;
import Game.GameSystem;
import Game.MoveDown;
import Game.MoveRight;
import Game.MoveUp;
import Game.Objects.HoverPotion;
import Game.Objects.InvincibilityPotion;
import Game.Objects.Key;

class GameSystemTest {

    @Test
    void initialiseMapTest() {
        GameSystem sys = new GameSystem();
        sys.initialiseMap("largeMap.txt");
        assert(sys.getPlayer() != null);
        assert(sys.getMap() != null);
    }
    
    @Test
    void checkWinningTest() {
        GameSystem sys = new GameSystem();
        sys.initialiseMap("largeMap.txt");
        assert(!sys.checkWinning());
    }
    
    @Test
    void checkLoseConditionTest() {
        GameSystem sys = new GameSystem();
        sys.initialiseMap("largeMap.txt");
        assert(!sys.checkLoseCondition());
    }
    
    @Test
    void useInvinciblePotionTest() {
        GameSystem sys = new GameSystem();
        sys.initialiseMap("largeMap.txt");
        InvincibilityPotion p = new InvincibilityPotion(1,1);
        sys.getPlayer().pickItem(p);
        sys.useInvinciblePotion();
        assert(!sys.getPlayer().getBuff().isEmpty());
    }
    
    @Test
    void useHoverPotionTest() {
        GameSystem sys = new GameSystem();
        sys.initialiseMap("largeMap.txt");
        HoverPotion p = new HoverPotion(1,1);
        sys.getPlayer().pickItem(p);
        sys.useHoverPotion();
        assert(!sys.getPlayer().getBuff().isEmpty());
    }
    
    @Test
    void moveEnemiesTest() {
        GameSystem sys = new GameSystem();
        sys.initialiseMap("largeMap.txt");
        sys.getPlayer().updatePosition(5, 15);
        assert(!sys.checkLoseCondition());
        sys.moveEnemies();
        assert(sys.checkLoseCondition());
        
    }
    
    @Test
    void movePlayerTest() {
        GameSystem sys = new GameSystem();
        sys.initialiseMap("largeMap.txt");
        int oldX = sys.getPlayer().getX();
        int oldY = sys.getPlayer().getY();
        sys.movePlayer(new MoveDown());
        int newX = sys.getPlayer().getX();
        int newY = sys.getPlayer().getY();
        assert(oldX != newX || oldY != newY);
    }
    
    @Test
    void movePlayer_Wall() {
        GameSystem sys = new GameSystem();
        sys.initialiseMap("largeMap.txt");
        int oldX = sys.getPlayer().getX();
        int oldY = sys.getPlayer().getY();
        sys.movePlayer(new MoveUp());
        int newX = sys.getPlayer().getX();
        int newY = sys.getPlayer().getY();
        assert(oldX == newX || oldY == newY);
    }
    
    @Test
    void movePlayer_Boulder() {
        GameSystem sys = new GameSystem();
        sys.initialiseMap("largeMap.txt");
        sys.movePlayer(new MoveDown());
        int oldX = sys.getPlayer().getX();
        int oldY = sys.getPlayer().getY();
        sys.movePlayer(new MoveDown());
        int newX = sys.getPlayer().getX();
        int newY = sys.getPlayer().getY();
        assert(oldX != newX || oldY != newY);
    }
    
    @Test
    //Boulder next to the wall
    void movePlayer_Boulder_con() {
        GameSystem sys = new GameSystem();
        sys.initialiseMap("largeMap.txt");
        int oldX = sys.getPlayer().getX();
        int oldY = sys.getPlayer().getY();
        sys.movePlayer(new MoveRight());
        int newX = sys.getPlayer().getX();
        int newY = sys.getPlayer().getY();
        assert(oldX == newX || oldY == newY);
    }
    
    @Test
    // double Boulder
    void movePlayer_Boulder_con2() {
        GameSystem sys = new GameSystem();
        sys.initialiseMap("largeMap.txt");
        sys.getPlayer().updatePosition(1, 8);
        int oldX = sys.getPlayer().getX();
        int oldY = sys.getPlayer().getY();
        sys.movePlayer(new MoveDown());
        int newX = sys.getPlayer().getX();
        int newY = sys.getPlayer().getY();
        assert(oldX == newX || oldY == newY);
    }

    @Test
    //cannot open
    void movePlayer_Door() {
        GameSystem sys = new GameSystem();
        sys.initialiseMap("largeMap.txt");
        sys.getPlayer().updatePosition(17, 4);
        int oldX = sys.getPlayer().getX();
        int oldY = sys.getPlayer().getY();
        sys.movePlayer(new MoveUp());
        int newX = sys.getPlayer().getX();
        int newY = sys.getPlayer().getY();
        assert(oldX == newX || oldY == newY);
    }
    
    @Test
    // can open
    void movePlayer_Door_con() {
        GameSystem sys = new GameSystem();
        sys.initialiseMap("largeMap.txt");
        sys.getPlayer().updatePosition(17, 4);
        int oldX = sys.getPlayer().getX();
        int oldY = sys.getPlayer().getY();
        Key k = new Key(1,1,Color.RED);
        sys.getPlayer().pickItem(k);
        sys.movePlayer(new MoveUp());
        int newX = sys.getPlayer().getX();
        int newY = sys.getPlayer().getY();
        assert(oldX != newX || oldY != newY);
    }
}
