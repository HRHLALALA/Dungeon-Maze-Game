package Game;

import Game.Objects.*;
import winnningCondions.WinningCondition;
/**
 * This Class is the system of the game, where most of the gameplay happens by evoking on other object's methods
 * @author Eddie
 *
 */
public class GameSystem{

	private Map map;
    private Player player;
    
    public GameSystem() {
        this.map = null;
        this.player = null;
    }
    
    /**
     * start up and load the map from a file
     * @param file text file
     */
    public void initialiseMap(String file) {
        Map map = new Map();
        map.loadMap(file);
        this.map = map;
        this.player = this.map.getPlayer();
    }
    
    /**
     * restarts the Map to the initial status
     */
    
    public void restart() {
    	String location = this.map.getMapFileLocation();
    	this.initialiseMap(location);
    	
    }
    
/**
 * checks if the player reach safety or their goal
 * @return boolean true for achieved false for still going
 */
    public boolean checkWinning() {
    	if(this.map.checkItems(Exit.class)) {
    		Player player = map.getPlayer();
    		Item e = map.checkStaticItems(player.getX(), player.getY());
    		return (e!=null && e.isExit());
    	}
    	if(this.map.getWinningConditions().size()==0) return false;
    	for(WinningCondition c: this.map.getWinningConditions()) {
    		if(!c.checkWin(map)) return false;
    	}

    	return true;
    }
    
    /**
     * checks if the player died
     * @return boolean true if dead false if alive
     */
    public boolean checkLoseCondition() {
    	boolean lost=false;
    	if(!player.isAlive()) {
    		lost = true;
    	}
    	return lost;
    }
    
    /**
     * consumes an invincibilityPotion if it exists within the bag, allows the player to not die within a limited time
     */
    public void useInvinciblePotion() {
    	this.player.usePotion(PickableItemList.INVINCIBILITYPOITION);
    }
    
    /**
     * consumes a hoverPotion if it exists within the bag, allows the player to hover over pits
     */
    public void useHoverPotion() {
    	this.player.usePotion(PickableItemList.HOVERPOTION);
    }
    
    /**
     * this method uses an arrow from the player's bag if it exists
     */
    public void useArrow() {
    	this.player.arrowAttack(map);
    }
    
    /**
     * this method evokes the enemy to find a path towards the player according to their behaviour
     */
     public void moveEnemies() {
        for(Item t:this.map.getItems(Enemy.class)) {
            Enemy e = (Enemy)t;
        	e.scan(this.player);
        	e.walkableSpace(this.map);
        	Route r;
			if(this.player.hasBuff(PickableItemList.INVINCIBILITYPOITION))
        		r =e.RetreatMethod(player, map);
        	else
        		r = e.searchPlayer(this.map);
        	Integer[] i= r.getPathLog().get(1);
        	e.move(map,i[0], i[1]);
        	
        }
        map.reloadItems();
    }
     
	/**
	 * updates the player's movement on the map
	 * @param movement which way to move
	 */
    public void movePlayer(ItemMove movement){
        this.player.move(movement, map);
    }
    
    /**
     * changes the switch states for floorswitch objects
     */
    public void updateSwitchState() {
    	for(Item t:this.map.getItems(FloorSwitch.class)) {
    	    FloorSwitch s = (FloorSwitch)t;
    	    int x = s.getX();
    	    int y = s.getY();
    		if(map.checkDynamicItems(x, y)!=null&& map.checkDynamicItems(x, y).isBoulder()) s.setActivated(true);
    	}
    }
    
	/**
	 * evoke to allow the player to pick up items
	 */
    public void pickItem() {
		Item item = this.map.checkStaticItems(player.getX(), player.getY());
		if(item == null) return;
		if(item.isPickable()) {
			Item i = player.pickItem((PickableItem)item);
			if(i!=null)i.updatePosition(player.getX(), player.getY());
			map.removeItem(item);
			map.addItem(i);
		}
    }
    
    /**
     * refreshes the information on the bomb (timer)
     */
    public void bombUpdate(){
    	this.map.updateBoombs();
    }
    
    /**
     * if player has bomb in inventory then uses and activates the bomb
     */
    public void useBomb() {
    	if(!this.player.dropBomb()) return;
    	int x = player.getX();
    	int y = player.getY();
    	Bomb b = new Bomb(x,y);
    	b.setLight(true);
    	map.addItem(b);
    	map.reloadItems();
    }
    
    /**
     * getters for variables player
     * @return
     */
    public Player getPlayer() {
        return this.player;
    }
    
    /**
     * getter for maps
     * @return
     */
    public Map getMap() {
        return this.map;
    }
    
    /**
     * loads custom map
     * @param map
     */
    public void diyMapLoad(Map map) {
    	this.map = map;
    	this.player = this.map.getPlayer();
    }
    
    
}
