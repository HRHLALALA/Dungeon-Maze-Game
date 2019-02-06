package Game;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import Game.Objects.*;
import winnningCondions.*;

public class Map implements Cloneable {
    
	private Player player;
	private String mapFileLocation;
	private int mapSize = 20;
	ArrayList<WinningCondition> winningConditions = new ArrayList<WinningCondition>();
	private Item[][] dynamicItems;
	private Item[][] staticItems;

	/**
	 * The size of the map
	 * @return The size of the map
	 */
	public int getMapSize() {
	    return this.mapSize;
	}

	/**
	 * Get the dynamic item matrix map
	 * @return the matrix of the dynamic items
	 */
	public Item[][] getDynamicItems(){
	    return this.dynamicItems;
	}
	/**
	 * Get the dynamic item matrix map
	 * @return the matrix of the dynamic items
	 */
	public Item[][] getStaticItems(){
        return this.staticItems;
    }
	/**
	 * Get all of the winning conditions
	 * @return  the list of the winning conditions
	 */
	public ArrayList<WinningCondition> getWinningConditions() {
	    return this.winningConditions;
	}
	/**
	 * Get the item from the static item matrix map with corresponding position
	 * @param x
	 * @param y
	 * @return the item and null if none
	 */
	public Item checkDynamicItems(int x, int y) {
	    return this.dynamicItems[x][y];
	}
	/**
	 * Get the item from the dynamic item matrix map with corresponding position
	 * @param x
	 * @param y
	 * @return the item and null if none
	 */
	public Item checkStaticItems(int x, int y) {
        return this.staticItems[x][y];
    }
	/**
	 * Get an item on the map with corresponding position
	 * @param x
	 * @param y
	 * @return the item and null if none
	 */
	public Item checkCoordinate(int x, int y) {
	    if (this.dynamicItems[x][y] != null) {
            return this.dynamicItems[x][y];
        }
	    if (this.staticItems[x][y] != null) {
            return this.staticItems[x][y];
        }
	    if(player==null) return null;
	    int posX = player.getX();
		int posY = player.getY();
		if(posX == x && posY == y) return this.player;
        return null;
	}
	
	/**
	 * Add an item on the map and 
	 * into the list of both dynamic and static item lists
	 * @param item
	 */
	public void addItem(Item item) {
		if(item==null) return;
		if(this.outsideRange(item)) return;
		int x = item.getX();
		int y = item.getY();
		if(item.isPlayer()) this.player = (Player)item;
		else if(item.isBoulder() || item.isEnemy()) {
		    this.dynamicItems[x][y] = item;
		}
		else {
		    this.staticItems[x][y] = item;
		}
	}
	/**
	 * To check if the item is outside boundary.
	 * @param item the item
	 * @return true if outside
	 */
	public boolean outsideRange(Item item) {
		int x = item.getX();
		int y = item.getY();
		if(x>this.mapSize || y>this.mapSize) return true;
		else return false;
	}
	/**
	 * Remove an items on two matrix maps and a map pane
	 * @param item
	 */
	public void removeItem(Item item) {
	    if(item==null) return;
        if(this.outsideRange(item)) return;
        int x = item.getX();
        int y = item.getY();
        if(item.isBoulder() || item.isEnemy()) {
            this.dynamicItems[x][y] = null;
        }
        else {
            this.staticItems[x][y] = null;
        }
        if(item.isPlayer()) {
        	this.player = null;
        }
	}
	
	/**
	 * Load the winning conditions from the file
	 * 2 - check if all switches are activated
	 * 3 - check if all enemy are killed
	 * 4 - check if all treasure are picked
	 * @param i the winning condition code
	 */
	public void loadWinningConndition(int i) {
		switch(i) {
		//case 1: this.winningConditions.add(new ExitCondition());break;
		case 2: this.winningConditions.add(new SwitchCondition());break;
		case 3: this.winningConditions.add(new EnemyCondition());break;
		case 4: this.winningConditions.add(new TreasureCondition());break;
		}
	}
	/**
	 * load a map from the file
	 * @param mapFile the path of the file
	 */
	public void loadMap(String mapFile) {
		this.mapFileLocation = mapFile;
	    Scanner sc = null;
	    ItemFactory itemFactory = new ItemFactory();
        try {
            sc = new Scanner(new File(mapFile));
        }
        catch (FileNotFoundException e) {
        	try {
        		 sc = new Scanner(new File("src/"+ mapFile));
        	}
        	catch (FileNotFoundException r) {
        		System.out.println(e.getMessage());
        		//sc.close();
        		return;
        	}
        }
            // Read input from the scanner here
        int line = 0;
        while (sc.hasNextLine()) {
	        // Read input from the scanner here
	        String input = sc.nextLine();
	        String[] splitstr = input.split("");
	        if (line == 0) {
	        	for(String i: splitstr) this.loadWinningConndition(Integer.parseInt(i));
	        }
	        else if (line == 1) {
                    this.mapSize = Integer.valueOf(input);
                    this.dynamicItems = new Item[this.mapSize][this.mapSize];
                    this.staticItems = new Item[this.mapSize][this.mapSize];
	        }
	        else {
	        	int n = 0;
	        	while (n < splitstr.length) {
	        		Item item= itemFactory.getItem(splitstr[n], n, line - 2);
	        		addItem(item);
	        		n++;
	        	}
	        }
	        line++;
        }    
        sc.close();
	}
	
	/**
	 * @return the mapFileLocation
	 */
	public String getMapFileLocation() {
		return mapFileLocation;
	}

	public ArrayList<Item> getItems(Class<?> a){
	    ArrayList<Item> items = new ArrayList<Item>();
	    Item item;
	    if(a == Player.class) {
	    	items.add(player);
	    	return items;
	    }
	    for(int x = 0; x < this.mapSize; x++) {
            for(int y = 0; y < this.mapSize; y++) {
                item = this.staticItems[x][y];
                if(a.isInstance(item)) {
                    items.add(item);
                }
                item = this.dynamicItems[x][y];
                if(a.isInstance(item)) {
                    items.add(item);
                }
            }
        }
	    return items;
	}
	
	public Player getPlayer() {
	    return this.player;
	}
	/**
	 * load a new empty map
	 * @param size
	 */
	public void newBlankMap(int size) {
	    this.staticItems = new Item[size][size];
		this.dynamicItems = new Item[size][size];
		this.mapSize=size;
		this.player = null;
	}
	/**
	 * CHeck if an kind of item on the map
	 * @param a the class of the item
	 * @return true if found
	 */
	public boolean checkItems(Class<?> a) {
	    Item item = null;
	    for(int x = 0; x < this.mapSize; x++) {
	        for(int y = 0; y < this.mapSize; y++) {
	            item = this.staticItems[x][y];
	            if(a.isInstance(item)) return true;
	            item = this.dynamicItems[x][y];
	            if(a.isInstance(item)) return true;
	        }
	    }
	    return false;
	}
	/**
	 * update the map and reload items
	 */
	public void reloadItems() {
	    Item item;
	    int itemX;
	    int itemY;
        for(int x = 0; x < this.mapSize; x++) {
            for(int y = 0; y < this.mapSize; y++) {
                item = this.staticItems[x][y];
                if (item != null) {
                	this.staticItems[x][y] = null;
                    itemX = item.getX();
                    itemY = item.getY();
                    this.staticItems[itemX][itemY] = item;
                    
                }
                item = this.dynamicItems[x][y];
                if (item != null) {
                	this.dynamicItems[x][y] = null;
                    itemX = item.getX();
                    itemY = item.getY();
                    this.dynamicItems[itemX][itemY] = item;
                }
            }
        }
        if(this.player==null) return;

    }
	/**
	 * Update the item position on the map
	 * @param x
	 * @param y
	 */
	public void updateItem(int x, int y) {
	    Item item;
	    int itemX;
        int itemY;
	    item = this.staticItems[x][y];
        if (item != null) {
            this.removeItem(item);
            itemX = item.getX();
            itemY = item.getY();
            this.staticItems[itemX][itemY] = item;
        }
        
        item = this.dynamicItems[x][y];
        if (item != null) {
            this.removeItem(item);
            itemX = item.getX();
            itemY = item.getY();
            this.dynamicItems[itemX][itemY] = item;
        }
	}
	/**
	 * Update all the status of the bombs on the map
	 */
	public void updateBoombs() {
		for(int x = 0; x < this.mapSize; x++) {
	            for(int y = 0; y < this.mapSize; y++) {
	                Item item = this.staticItems[x][y];
	                if (item != null && item.isBomb()) {
	                	Bomb b = (Bomb)item;
	                	if(b.isLight()) {
	                		if(b.getSecond()==0) {
	                			this.bombExplode(b);
	                			this.removeItem(b);
	                		}
	                		else b.countDown();
	                	}
	                }
	            }
		 }
	}
	
	/**
	 * Explode the bomb
	 * All enemies and players has the distance
	 * of 3 will disappear.
	 * @param bomb the bomb
	 */
	private void bombExplode(Bomb bomb){
		int x = bomb.getX();
		int y = bomb.getY();
		for(int i = (x-1>=0?x-1:0);i<(x+2<20?x+2:20);i++) {
			for(int j =(y-1>=0?y-1:0);j<(y+2<20?y+2:20);j++) {
				Item item = this.dynamicItems[i][j];
				if(item!=null && (item.isEnemy() || item.isBoulder())) {
					this.removeItem(item);
				}
				item = this.getPlayer();
				if(item!=null && item.getX()==i && item.getY()==j) {
					Player hero = (Player) item;
					hero.setAlive(false);
				}
			}
		}
	}
    public void printMap(String mapName){
    	PrintWriter writer = null;
    	File file = new File("src/DIYmaps/"+ mapName+".txt");
    	try {
    		file.delete();
    		file.createNewFile();
		} catch (IOException e1) {
			try {
				file = new File("DIYmaps/"+mapName+".txt");
				file.delete();
	    		file.createNewFile();
			}
			catch(IOException e2) {
				System.out.println(e2.getMessage());
			}
		}
    	file.setWritable(true);
    	try {
			writer = new PrintWriter("DIYmaps/"+mapName+".txt");
    	}
		catch (FileNotFoundException e) {
            try {
                writer = new PrintWriter("src/DIYmaps/"+mapName+".txt");
            }
            catch (FileNotFoundException ee) {
                System.out.println(ee.getMessage());
                return;
            }
        }
			if(this.winningConditions.size()==0) writer.print("0");
			for(WinningCondition w: this.winningConditions) {
				writer.print(w.getWinningCode());
			}
			writer.println("\n"+this.mapSize);
			this.reloadItems();
	        int mapSize = this.getMapSize();
	        for(int y = 0; y < mapSize; y++) {
	            for(int x = 0; x < mapSize; x++) {
	                Item item = this.checkCoordinate(x, y);
	                if (this.player!=null && x == this.player.getX()&& y ==this.player.getY()) {
	                    if (this.checkStaticItems(x, y) != null) {
	                    	writer.print("@");
	                    }
	                    else {
	                    	writer.print("r");
	                    }
	                }
	                else if (item != null) {
                        writer.print(item.getSymbol());
	                }
	                else {
	                	writer.print(" ");
	                }
	            }
	            writer.print("\n");
	        }
            writer.close();
		
    }
	public Map clone() throws CloneNotSupportedException {
		Map newMap = (Map)super.clone();
		newMap.dynamicItems = this.dynamicItems.clone();
		newMap.staticItems = this.staticItems.clone();
		return newMap;
	}
}

