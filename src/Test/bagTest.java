package Test;

import java.awt.Color;
import org.junit.jupiter.api.Test;
import Game.Bag;
import Game.Objects.Arrow;
import Game.Objects.HoverPotion;
import Game.Objects.Key;
import Game.Objects.PickableItemList;
import Game.Objects.Sword;

class bagTest {	
	/**
	 * Check if can successfully add an item 
	 * and the num of item
	 */
	@Test
	void addItemTest_numof() {
		Bag bag = new Bag();
		Key key = new Key(1,2,Color.red);
		bag.addItem(key);
		assert(bag.numOfItem()==1);
		
	}
	/**
	 * Check if the item can be deleted
	 */
	@Test
	void deleteItemTest_del() {
		Bag bag = new Bag();
		Key key1 = new Key(1,2,Color.red);
		bag.addItem(key1);
		bag.deleteItem(key1);
		int i = bag.numOfItem();
		assert(i==0);
	}
	
	/**
	 * Check if first item can be replaced
	 */
	@Test
	void replaceItemTest_rep() {
		Bag bag = new Bag();
		Key key1 = new Key(1,2,Color.red);
		Key key2 = new Key(1,2,Color.green);
		Key key3 = new Key(1,2,Color.blue);
		bag.addItem(key1);
		bag.addItem(key2);
		bag.replaceFirst(key3);
		assert(bag.containItem(key3) && !bag.containItem(key1));

	}
	/**
	 * Check if containskey works
	 */
	@Test
	void containKeyTest_con() {
		Bag bag = new Bag();
		Key key1 = new Key(1,2,Color.red);
		bag.addItem(key1);
		assert(bag.containKey(PickableItemList.KEY));
	}
	/**
	 * addItem method null test
	 */
	@Test
	void addItemTest_null() {
		Bag bag = new Bag();
		assert(!bag.addItem(null));
	}
	/**
	 * check if new bag will be size of 0 and after add 1 item will be size of 1
	 */
	@Test
	void numOfItemTest() {
		Bag bag = new Bag();
		assert(bag.numOfItem()==0);
		Key key1 = new Key(1,2,Color.red);
		bag.addItem(key1);
		assert(bag.numOfItem()==1);
		
	}
	/**
	 * Check if the addItem method can add different type of items
	 */
	@Test 
	void addItem_differentType(){
		Bag bag = new Bag();
		Key key = new Key(1,2,Color.red);
		HoverPotion p = new HoverPotion(1,2);
		bag.addItem(p);
		bag.addItem(key);
		assert(bag.numOfItem()==2 && bag.containKey(key.getType())&& bag.containKey(p.getType()));	
	}
	/**
	 * Test if multiple items with same class can be added
	 */
	@Test
	void addItemTest_Mul_Key() {
		Bag bag = new Bag();
		Key key1 = new Key(1,2,Color.red);
		Key key2 = new Key(1,2,Color.green);
		Key key3 = new Key(1,2,Color.blue);
		bag.addItem(key1);
		bag.addItem(key2);
		bag.addItem(key3);
		assert(bag.getItemList(PickableItemList.KEY).size()==1 && bag.containItem(key1));
	}
	@Test
	void addItemTest_Mul_Arrow() {
		Bag bag = new Bag();
		Arrow a1 = new Arrow(1,2);
		Arrow a2 = new Arrow(2,3);
		Arrow a3 = new Arrow(2,3);
		bag.addItem(a1);
		bag.addItem(a2);
		bag.addItem(a3);
		
		
	}
	
	@Test
	void spaceOverFlow() {
		Bag bag = new Bag();
		Sword s1 = new Sword(2, 2);
		Sword s2 = new Sword(3, 3);
		Key redK = new Key(1, 5, Color.red);
		bag.addItem(s1);
		bag.addItem(s2);
		bag.addItem(redK);
		assert(bag.spaceOverflow(redK));

	}

}
