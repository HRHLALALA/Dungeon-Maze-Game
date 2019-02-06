package controllers;

import java.util.ArrayList;
import Game.GameSystem;
import Game.Item;
import Game.Map;
import Game.MoveDown;
import Game.MoveLeft;
import Game.MoveRight;
import Game.MoveUp;
import Game.Player;
import Game.Objects.Exit;
import javafx.animation.PathTransition;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import winnningCondions.WinningCondition;
/**
 * This class is responsible for handling the game screen and interacts with the game System and update between the System and the user
 * @author Core Dump
 *
 */

public class GameScreen extends Controller{
    
	private ImageView mapImg;
	private ImageView playerImg;
	private ImageView tileImg;
	private GameSystem initSys;
	private GameSystem sys;
    @FXML Label swordNum;
    @FXML Label arrowNum;
    @FXML Label bombNum;
    @FXML Label yellowKeyNum;
    @FXML Label blueKeyNum;
    @FXML Label redKeyNum;
    @FXML Label invinciblePotionNum;
    @FXML Label hoverPotionNum;
    @FXML Label treasureNum;
	@FXML private GridPane mapPane;
	private ArrayList<Node> tiles = new ArrayList<Node>();
	@FXML private GridPane bagPane;
    public GameScreen(Stage s) {
    	super(s,"Game Screen","UI/GameScreen.fxml");
    }
    /**
     * initialises the gameplay screen for the game
     */
    @FXML
    public void initialize() {
    	this.getStage().setMinHeight(800);
    	this.getStage().setMinWidth(900);
    	this.getStage().setMaxHeight(800);
    	this.getStage().setMaxWidth(900);
    	Map map = sys.getMap();
    	for(int i = 0;i<20;i++) {
            for(int j = 0; j <20;j++) {
                Item item = map.checkStaticItems(i, j);
                Image image = new Image("Resource/tile.png");
    		    tileImg = new ImageView();
    		    tileImg.setImage(image);
    		    tileImg.setFitHeight(32);
    		    tileImg.setFitWidth(40);
    			mapPane.add(tileImg, i, j);
    			tiles.add(tileImg);
                if(item != null) {
                    mapPane.add(item.getImage(), i, j);
                }
                item = map.checkDynamicItems(i, j);
                if(item!=null) {
                	mapPane.add(item.getImage(), i, j);
                }
            }
        }
    	Player p = map.getPlayer();
    	this.playerImg = p.getImage();
    	mapPane.add(playerImg,p.getX(),p.getY());
    	mapPane.autosize();
    	GridPane winningPane = new GridPane();
    	Label win;
    	int i=0;
    	if(map.checkItems(Exit.class)) {
    		win = new Label();
    		win.setText("* Go to the exit");
    		winningPane.add(win, 0, i);
    	}
    	else {
	    	for(WinningCondition c:map.getWinningConditions()) {
	    		int code = c.getWinningCode();
	    		win = new Label();
	    		switch(code) {
	    		case 2: win.setText("* Open all switches");break;
	    		case 3: win.setText("* Kill all enemies");break;
	    		case 4: win.setText("* Collect all treasure");break;
	    		}
	    		winningPane.add(win, 0, i);
	    		i++;
	    	}
    	}
    	bagPane.add(winningPane, 10, 1);
    }
    
    public void setSystem(GameSystem sys) {
    	this.sys = sys;
    	this.initSys = sys;
    }
    /**
     * handles the key event listeners, and sort the system to do accordingly 
     * @param event the event that happened on the keyboard
     */
    @FXML
	public void handleKeyPressed(KeyEvent event) {
    	Player player = sys.getPlayer();
    	PathTransition anim = new PathTransition();
		anim.setNode(playerImg);
		anim.setDelay(new Duration(2) );
		Line line = new Line();
		Bounds bound = playerImg.localToParent(playerImg.getBoundsInLocal());
		line.setStartX(bound.getMinX());
		line.setStartY(bound.getMinY());
		switch (event.getCode()) {
		case UP:
			sys.movePlayer(new MoveUp());
			break;
		case DOWN:
			sys.movePlayer(new MoveDown());
			break;
		case LEFT:
			sys.movePlayer(new MoveLeft());
			break;
		case RIGHT:
			sys.movePlayer(new MoveRight());
			break;
		case E:
			sys.pickItem();
			break;
		case B:
			sys.useBomb();
			break;
		case A:
			sys.useArrow();break;
		case I:
			sys.useInvinciblePotion();break;
		case H:
			sys.useHoverPotion();break;		
		default: 
			break;
		}
		event.consume();
        arrowNum.setText(Integer.toString(player.checkBagItemQuantity("ARROW")));
        swordNum.setText(Integer.toString(player.checkBagItemQuantity("SWORD")));
        bombNum.setText(Integer.toString(player.checkBagItemQuantity("BOOM")));
        hoverPotionNum.setText(Integer.toString(player.checkBagItemQuantity("HOVERPOTION")));
        invinciblePotionNum.setText(Integer.toString(player.checkBagItemQuantity("INVINCIBILITYPOITION")));
        treasureNum.setText(Integer.toString(player.checkBagItemQuantity("TREASURE")));
        redKeyNum.setText(Integer.toString(player.checkBagItemQuantity("red")));
        yellowKeyNum.setText(Integer.toString(player.checkBagItemQuantity("yellow")));
        blueKeyNum.setText(Integer.toString(player.checkBagItemQuantity("blue")));
        sys.bombUpdate();
        sys.getMap().reloadItems();
        mapPane.getChildren().remove(playerImg);
        mapPane.add(playerImg, player.getX(), player.getY());
        sys.moveEnemies();
        sys.getPlayer().stayPotion();
        sys.updateSwitchState();
        ButtonType Exit = new ButtonType("Back to Menu");
        ButtonType Restart = new ButtonType("Restart");
        ButtonType Next = new ButtonType("Previous");
        this.updateMapPane();
        if(sys.checkWinning()) { 
            Alert errorAlert = new Alert(AlertType.NONE,"",Exit, Restart,Next);
            errorAlert.setTitle("Congratulations!");
            errorAlert.setHeaderText("Dungeon Complete!");
            errorAlert.setContentText("Well Done!");
            errorAlert.showAndWait().ifPresent(response -> {
                if(response == Exit) {
                    Controller cont = new Start1(this.getStage());
                    cont.setPreviousController(this);
                    cont.start();
                }else if(response == Restart) {
                    this.sys.restart();
                    this.setSystem(sys);
                    this.initialize();
                }else if(response == Next) {
                	Controller c = this.getPreviousController();
                	c.setPreviousController(this);
                	c.setStage(this.getStage());
                	c.restart();
                }
                
            });
        }
        if(sys.checkLoseCondition()) {
            Alert errorAlert = new Alert(AlertType.NONE,"",Exit, Restart);
            errorAlert.setHeaderText("Game Over!");
            errorAlert.setContentText("Unfortunate! Try Again!");
            errorAlert.showAndWait().ifPresent(response -> {
                if(response == Exit) {
                    Controller cont = new Start1(this.getStage());
                    cont.setPreviousController(this);
                    cont.start();
                }else if(response == Restart) {
                    this.sys.restart();
                    this.setSystem(sys);
                    this.initialize();
                }
            });
            
        }
	}

    
    /**
     * handles the home button action from the game screen, returns to homescreen
     */
    @FXML
    public void handleHomeBtn() {
    	Controller cont = new HomeScreen(this.getStage());
    	cont.setPreviousController(this);
    	cont.restart();
    }
    /**
     * handles the exit button action from gamescreen, closes the entire stage
     */
    @FXML
    public void handleExitBtn() {
    	this.getStage().close();
    }

    /**
     * handles the Restart button action from game screen, Restarts the map from beginning
     */
    @FXML
    public void handleRestartBtn(){
    	this.sys.restart();
    	this.setSystem(sys);
    	this.initialize();
    }

    /**
     * handles the Back button action from gamescreen, goesback to the previous controller
     */
    @FXML
    public void handleLoadBtn() {
    	Controller c = this.getPreviousController();
    	c.setPreviousController(this);
    	c.setStage(this.getStage());
    	c.restart();
    }

    
    public void updateMapPane() {
    	Map map = sys.getMap();
    	for(int i = 0;i<20;i++) {
            for(int j = 0; j <20;j++) {
            	Node node = tiles.get(j*20+i);
            	mapPane.getChildren().remove(node);
        		mapPane.add(node, i, j);
                Item item = map.checkStaticItems(i, j);

                if(item != null) {
                	mapPane.getChildren().remove(item.getImage());
                    mapPane.add(item.getImage(), i, j);
                }
                item = map.checkDynamicItems(i, j);
                if(item!=null) {
                	mapPane.getChildren().remove(item.getImage());
                	mapPane.add(item.getImage(), i, j);
                }
            }
        }
    	Player p = map.getPlayer();
    	mapPane.getChildren().remove(playerImg);
    	if(p!=null) mapPane.add(playerImg,p.getX(),p.getY());
    }
    
    
}
