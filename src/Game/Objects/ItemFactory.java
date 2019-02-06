package Game.Objects;

import java.awt.Color;

import Game.Coward;
import Game.Enemy;
import Game.Hound;
import Game.Hunter;
import Game.Item;
import Game.Player;
import Game.Strategist;

public class ItemFactory {
    public Item getItem(String itemType, int x, int y){
        if(itemType == null){
            return null;
        }        
        if(itemType.equals(" ")) {
            return null;
        }
        else if(itemType.equals("r")) {
            return new Player(x, y);
        }
        else if(itemType.equals("e")) {
            return new Exit(x, y);
        }
        else if(itemType.equals("#")) {
            return new Wall(x, y);
        }
        else if(itemType.equals("o")) {
            return new Boulder(x, y);
        }
        else if(itemType.equals("p")) {
            return new Pit(x, y);
        }
        else if(itemType.equals("H")) {
        	return new Enemy(x,y,new Hunter());
        }
        else if(itemType.equals("h")) {
        	return new Enemy(x,y,new Hound());
        }
        else if(itemType.equals("?")) {
        	return new Enemy(x,y,new Strategist());
        }
        else if(itemType.equals("C")) {
        	return new Enemy(x,y,new Coward());
        }
        else if(itemType.equals("b")) {
        	return new Bomb(x,y);
        }
        else if(itemType.equals("s")) {
        	return new FloorSwitch(x,y);
        }
        else if(itemType.equals("K")) {
            return new Key(x,y,Color.YELLOW);
        }
        else if(itemType.equals("k")) {
            return new Key(x,y,Color.RED);
        }
        else if(itemType.equals("D")) {
            return new Door(x,y,Color.YELLOW);
        }
        else if(itemType.equals("d")) {
            return new Door(x,y,Color.RED);
        }
        else if(itemType.equals("8")) {
            return new Key(x,y,Color.BLUE);
        }
        else if(itemType.equals("9")) {
            return new Door(x,y,Color.BLUE);
        }
        else if(itemType.equals("t")) {
            return new Treasure(x,y);
        }
        else if(itemType.equals("l")) {
            return new Sword(x,y);
        }
        else if(itemType.equals("1")) {
            return new InvincibilityPotion(x,y);
        }
        else if(itemType.equals("2")) {
            return new HoverPotion(x,y);
        }
        else if(itemType.equals("a")) {
            return new Arrow(x,y);
        }
        return null;
     }
}
