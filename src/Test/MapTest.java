package Test;

import org.junit.jupiter.api.Test;

import Game.GameSystem;
import Game.Map;
import Game.Player;
import Game.Objects.Exit;
import Game.Objects.Pit;
import winnningCondions.SwitchCondition;

class MapTest {

	@Test
    void mapSizeTest() {
        GameSystem sys = new GameSystem();
        sys.initialiseMap("largeMap.txt");
        Map map = sys.getMap();
        assert(map.getMapSize()==20);
        
    }
	
	@Test
    void dynamicItemTest() {
        GameSystem sys = new GameSystem();
        sys.initialiseMap("largeMap.txt");
        Map map = sys.getMap();
        int counter = 0;
        for (int x = 0; x < map.getMapSize(); x++) {
            for (int y = 0; y < map.getMapSize(); y++) {
                if (map.checkDynamicItems(x, y) != null) counter++;
            }
        }
        assert(counter==6); 
    }
	
	@Test
    void stactItemTest() {
        GameSystem sys = new GameSystem();
        sys.initialiseMap("largeMap.txt");
        Map map = sys.getMap();
        int counter = 0;
        for (int x = 0; x < map.getMapSize(); x++) {
            for (int y = 0; y < map.getMapSize(); y++) {
                if (map.checkStaticItems(x, y) != null) counter++;
            }
        }
        assert(counter==120); 
    }
	
	@Test
    void winningConditionTest() {
        GameSystem sys = new GameSystem();
        sys.initialiseMap("largeMap.txt");
        Map map = sys.getMap();
        assert(map.getWinningConditions().get(0) instanceof SwitchCondition); 
    }
	
	@Test
	void selfMadeMapTest_size() {
		Map map = new Map();
		map.newBlankMap(10);
		assert(map.getMapSize()==10);
	}

	@Test
	void selfMadeMapTest_player() {
		Map map = new Map();
		map.newBlankMap(10);
		Player p = new Player(1,1);
		map.addItem(p);
		assert(map.getPlayer().equals(p));
	}
	
	@Test
	void selfMadeMapTest_pit() {
		Map map = new Map();
		map.newBlankMap(10);
		Pit a = new Pit(1,2);
		map.addItem(a);
		assert(map.checkItems(a.getClass()));
	}
	
	@Test
	void selfMadeMapTest_exit() {
		Map map = new Map();
		map.newBlankMap(10);
		Exit e = new Exit(9,9);
		map.addItem(e);
		assert(map.checkItems(e.getClass()));
	}
	
	@Test
	void checkItemTest() {
		Map map = new Map();
		map.newBlankMap(10);
		Exit e = new Exit(9,9);
		map.addItem(e);
		assert(map.checkItems(Exit.class));
	}
	
	@Test
	void reloadMapTest() {
		Map map = new Map();
		map.newBlankMap(10);
		Pit a = new Pit(1,2);
		Exit e = new Exit(9,9);
		map.addItem(a);
		map.addItem(e);
		map.reloadItems();
		assert(map.checkCoordinate(1, 2).equals(a));
		assert(map.checkCoordinate(9, 9).equals(e));
	}
}
