package Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import Game.Objects.PickableItem;
import Game.Objects.Key;
import java.awt.Color;
import Game.Objects.PickableItemList;


public class Bag {
    /**
     * A hashmap bag has keys with String type and 
     * elements with Item type.
     * All kinds of key should be exist once
     * One key to one item
     */
    private HashMap<PickableItemList, ArrayList<PickableItem>> bagList;
    
    /**
     * Initialize a new empty bag
     */
    public Bag(){
        this.bagList = new HashMap<PickableItemList,ArrayList<PickableItem>>();
    }
    
    /**
     * add an item into the bag list
     * if the list does not contain the key, create a new key
     * @param o the pickable item instance
     * @return true if successfully added
     */
    public boolean addItem(PickableItem o) {
        if(o==null) return false;
        if(!this.bagList.containsKey(o.getType())) this.addKey(o.getType());
        ArrayList<PickableItem> list = this.getItemList(o.getType());
        if(!this.spaceOverflow(o))
            list.add(o);
        else return false;
        this.bagList.replace(o.getType(),list);
        return true;
    }
    
    /**
     * check the item quantity in the bag
     * @param name The name of the item want to check
     * @return the value of the item in the bag
     */
    public int itemQuantity(String name) {
        ArrayList<PickableItem> keyList = this.getItemList(PickableItemList.KEY);
        if (keyList != null) {
        int counter = 0;
        if (name.equals("red")) {
            for (PickableItem i : keyList) {
                if (((Key) i).getColor() == Color.RED) {
                    counter++;
                }
            }
            return counter;
        }
        if (name.equals("yellow")) {
            for (PickableItem i : keyList) {
                if (((Key) i).getColor() == Color.YELLOW) {
                    counter++;
                }
            }
            return counter;
        }
        if (name.equals("blue")) {
            for (PickableItem i : keyList) {
                if (((Key) i).getColor() == Color.BLUE) {
                    counter++;
                }
            }
            return counter;
        }
        }
        Set<PickableItemList> keyset = this.bagList.keySet();
        for(PickableItemList i:keyset) {
            if (name.equals(i.name())) {
                return this.getItemList(i).size();
            }  
        }
        
        return 0;
    }

    /**
     * Remove an item from the bag
     * @param the targeted item
     * @return ture if successfully deleted
     */
    public boolean deleteItem(PickableItem o) {
        if(o==null) return false;
        if(!this.containItem(o)) return false;
        ArrayList<PickableItem> list = this.getItemList(o.getType());
        list.remove(o);
        if(list.size()==0) this.bagList.remove(o.getType());
        else this.bagList.replace(o.getType(),list);
        return true;
    }
    
    /**
     * Check if an item is contained inside the bag
     * @param o the targeted item
     * @return true if contains
     */
    public boolean containItem(PickableItem o) {
        if(o == null) return false;
        ArrayList<PickableItem> a =this.getItemList(o.getType());
        return a.contains(o);
    }
    
    /**
     * check if a key exists inside the bag
     * @param key the name of the key
     * @return true if contains
     */
    public boolean containKey(PickableItemList a) {
        return this.bagList.containsKey(a);
    }
    
    /**
     * Reset the bag
     * Delete all keys and items
     */
    public void reset() {
        this.bagList.clear();
    }
    
    /**
     * Check the number of the item with the key
     * @return the number of the Item
     */
    public int numOfItem() {
        Set<PickableItemList> keys = this.bagList.keySet();
        int sum = 0;
        for(PickableItemList s:keys) {
            sum += this.getItemList(s).size();
        }
        return this.bagList.size();
    }
    
    /**
     * Add a key to the bag
     * @param o the key
     * @return if add successfully
     */
    public boolean addKey(PickableItemList o) {
        if(o==null) return false;
        if(this.bagList.containsKey(o)) return false;
        this.bagList.put(o, new ArrayList<PickableItem>());
        return true;
    }
    
    /**
     * Get the list of items with a key.
     * @param o The key
     * @return The list
     */
    public ArrayList<PickableItem> getItemList(PickableItemList o){
        ArrayList<PickableItem> list = this.bagList.get(o);
        return list;
    }
    
    /**
     * To check if the bag is overflowed with a kind of ite m
     * @param o An item
     * @return true if no extra space for the item
     */
    public boolean spaceOverflow(PickableItem o) {
        if(o==null) return false;
        PickableItemList key = o.getType();
        ArrayList<PickableItem> items = getItemList(key);
        return items !=null && o.getCapacity() <= getItemList(key).size();
    }
    
    /**
     * To replace the first item of the item list.
     * @param o The item
     * @return true if replace successfully
     */
    public PickableItem replaceFirst(PickableItem o) {
        if (o==null) return null;
        ArrayList<PickableItem> list = this.getItemList(o.getType());
        if(list.size()==0) return null;
        PickableItem i = list.remove(0);
        list.add(o);
        return i;
    }
}