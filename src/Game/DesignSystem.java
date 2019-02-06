package Game;

import java.util.Stack;

public class DesignSystem {
	private Map map;
	private Player player;
	private Stack<Item> record;
	
	public DesignSystem() {
		this.map = new Map();
		this.player = null;
		this.record = new Stack<Item>();
	}
	
	public Map getMap() {
		return map;
	}
	
	/**
	 * load a new map
	 * @param size The size of map. Cannot be zero
	 * @return true if successfully load
	 */
	public boolean newMap(int size) {
		if(size==0) return false;
		this.map.newBlankMap(size);
		return true;
	}
	
	/**
	 * Add an item into the map and record
	 * @param item
	 * @return
	 */
	public boolean addItem(Item item) {
		if(map.checkCoordinate(item.getX(),item.getY())!=null) return false;
		this.map.addItem(item);
		this.record.push(item);
		return true;
	}
	
	/**
	 * undo an operation.
	 */
	public Item undo() {
		if(this.record.size()==0) return null;
		Item item = this.record.pop();
		map.removeItem(item);
		return item;	
	}

	/**
	 * run the map
	 */
	public GameSystem runMap() {
		GameSystem g = new GameSystem();
		g.diyMapLoad(map);
		return g;
	}

}
