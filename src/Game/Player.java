package Game;

import java.awt.Point;
import java.util.ArrayList;

import Game.Objects.Arrow;
import Game.Objects.Boulder;
import Game.Objects.Door;
import Game.Objects.InvincibilityPotion;
import Game.Objects.Key;
import Game.Objects.PickableItem;
import Game.Objects.PickableItemList;
import Game.Objects.Pit;
import Game.Objects.Potion;
import Game.Objects.Sword;

public class Player extends Item {
	/**
	 * Bag used to store the picked items
	 * Buff contains potions
	 * Direction indicates the direction that player faces.
	 */
	private Bag bag;
	private ArrayList<Potion> buff;
	private ItemMove preMovement;
	private boolean Alive;
	/**
	 * Constructs initial state of the player
	 * with initially facing downwards
	 * @param x
	 * @param y
	 */
	public Player(int x, int y) {
		super(x, y);
		this.bag = new Bag();
		this.buff = new ArrayList<Potion>();
		this.preMovement = new MoveDown();
		this.setImage("Resource/Player.png");
		this.Alive = true;
		
	}
	/**
	 * @return the buff
	 */
	public ArrayList<Potion> getBuff() {
		return buff;
	}
	/**
	 * @param buff the buff to set
	 */
	public void addBuff(Potion p) {
		this.buff.add(p);
	}
	/**
	 * @return the faceDirection
	 */
	public ItemMove getPreMovement() {
		return this.preMovement;
	}
	/**
	 * @return the bag
	 */
	public Bag getBag() {
		return bag;
	}
	/**
	 * Move the player 1 step with direction if can move
	 * @param move direction The direction that player to move
	 * @param item the item at the next position
	 */
	
	public void move(ItemMove move,Map map) {
		Point p = move.predMove(this);
		if(p.x<0 || p.x>=20||p.y<0||p.y>=20) return;
		Item item = map.checkStaticItems(p.x, p.y);
		if(item!=null) {
			if((item.isWall())) return;
			if(item.isDoor()) {
				if(((Door)item).isBlocked() && !openDoor((Door)item)) return;
			}
		}
		if(item!=null&&item.isEnemy()){
			Enemy e = (Enemy) item;
			e.combatCheck(map);
		}
		item = map.checkDynamicItems(p.x, p.y);
		if(item!=null){
			if(item.isBoulder()) {
				if(!((Boulder)item).move(move, map)) {
					return;
				}
			}
		}
		map.reloadItems();
		move.move(this);
		this.preMovement = move;
		Item p1 = map.checkCoordinate(this.getX(), this.getY());
		if(p1.isPit()) {
			Pit p2 = (Pit)p1;
	    	if(this.hasBuff(PickableItemList.HOVERPOTION)) this.Alive = true;
	    	else if(this.getX()==p1.getX() && this.getY()==p1.getY()&& p2.isDroppable()) this.Alive = false;
		}
	}	
	/**
	 * Pick the item
	 * @return false if unsuccessfully pick
	 */
	public Item pickItem(PickableItem item) {
		if(item==null) return null;
		else if(bag.spaceOverflow(item))return bag.replaceFirst(item);
		else {
			bag.addItem(item);
			return null;
		}
	}
	/**
	 * Attack the enemy with arrow
	 * @return true if successfully use the arrow
	 * @throws TypeErrorException 
	 */
	public void arrowAttack(Map map){
		ArrayList<PickableItem> arrows = this.bag.getItemList(PickableItemList.ARROW);
		if(arrows == null) return;
		PickableItem item = arrows.get(0);
		if(item == null) return;
		if(!(item.isArrow())) return;
		this.bag.deleteItem(item);
		item.updatePosition(this.getX(), this.getY());
		((Arrow) item).shoot(this.preMovement, map);
	}
	
	/**
	 * Attack the enemy with sword
	 * @return true if successfully use the sword 
	 * @throws TypeErrorException 
	 */
	public boolean swordAttack(Enemy target) {
		if(!this.bag.containKey(PickableItemList.SWORD)) return false;
		PickableItem item = this.bag.getItemList(PickableItemList.SWORD).get(0);
		if(item==null) return false;
		if(!(item.isSword())) return false;
		Sword sword =  (Sword)item;
		sword.swing(target);
		sword.setDurability(sword.getDurability()-1);
		if(sword.getDurability()==0) bag.deleteItem(sword);
		return true;
	}
	/**
	 * Drop the bomb at the position the player stands
	 * @return true if successfully use the bomb
	 * @throws TypeErrorException 
	 */
	public boolean dropBomb(){
		if(!this.bag.containKey(PickableItemList.BOOM)) return false;
		ArrayList<PickableItem> items = this.bag.getItemList(PickableItemList.BOOM);
		if(items.size()==0) return false;
		PickableItem item = items.get(0);
		if(!(item.isBomb())) return false;
		return this.bag.deleteItem(item);
	}
	
	/**
	 * Open the door with key
	 * @return true if successfully use the key to open the door
	 * @throws TypeErrorException 
	 */
	public boolean openDoor(Door door) {
		if(door==null) return false;
		if(!this.bag.containKey(PickableItemList.KEY)) return false;
		ArrayList<PickableItem> keys = this.bag.getItemList(PickableItemList.KEY);
		for(PickableItem k: keys) {
			if(!(k.isKey()))return false;
			Key key = (Key)k;
			if (door.keyFitness(key)){
				if(key.useKey(door)) {
					bag.deleteItem(k);
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Use the  potion
	 * @return true if potion successfully be used
	 */
	public boolean usePotion(PickableItemList potionType) {
		if(!bag.containKey(potionType)) return false;
		ArrayList<PickableItem> potions = this.bag.getItemList(potionType);
		if(potions == null) return false;
		if(potions.isEmpty()) return false;
		Potion p = (Potion)potions.get(0);
		bag.deleteItem(p);
		p.usePotion(this);
		return true;
	}

	/**
	 * Remain the potion being activated
	 * @return successfully updated
	 */
	public boolean stayPotion() {
		if(this.buff == null )return false;
		for(Potion p:this.buff) {
			if(p.getType() == PickableItemList.INVINCIBILITYPOITION) {
				InvincibilityPotion potion = (InvincibilityPotion) p;
				potion.reduceTime();
				if(potion.getSecond()==0) {
					this.buff.remove(p);
					break;
				}
			}
		}
		return true;
	}
	/**
	 * Check if has a certain buff(potion)
	 * @param P the potion type
	 * @return true if has such potion
	 */
	public boolean hasBuff(PickableItemList P) {
		for(Potion p: this.buff) {
			if(p.getType()==P) return true;
		}
		return false;
	}
	/**
	 * checks if the player character is alive still
	 */
	public boolean isAlive() {
		return this.Alive;
	}
	/**
	 * sets player's alive status
	 */
	public void setAlive(boolean status) {
		this.Alive = status;
	}
	@Override
	public boolean isPlayer() {
		return true;
	}
	public int checkBagItemQuantity(String name) {
	    return this.bag.itemQuantity(name);
	}
	
}
