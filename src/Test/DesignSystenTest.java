package Test;


import org.junit.jupiter.api.Test;

import Game.DesignSystem;
import Game.Objects.Arrow;


class DesignSystenTest {

	@Test
	void initialGameMapTest() {
		DesignSystem sys = new DesignSystem();
		assert(!sys.newMap(0));
	}
	@Test
	void addItemTest_single() {
		DesignSystem sys = new DesignSystem();
		sys.newMap(10);
		Arrow a = new Arrow(1,1);
		sys.addItem(a);
		assert(sys.getMap().checkCoordinate(1, 1).equals(a));
	}
	
	@Test
	void addItemTest_mul() {
		DesignSystem sys = new DesignSystem();
		sys.newMap(10);
		Arrow a1 = new Arrow(1,1);
		Arrow a2 = new Arrow(1,1);
		sys.addItem(a1);
		assert(!sys.addItem(a2));
		assert(sys.getMap().checkCoordinate(1, 1).equals(a1));
	}
	
	@Test
	void undoTest() {
		DesignSystem sys = new DesignSystem();
		sys.newMap(10);
		Arrow a1 = new Arrow(1,1);
		Arrow a2 = new Arrow(1,2);
		sys.addItem(a1);
		sys.addItem(a2);
		sys.undo();
		assert(sys.getMap().checkCoordinate(1, 2)==null);
	}


	
	


}
