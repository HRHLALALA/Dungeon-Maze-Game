package controllers;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Optional;

import Game.Coward;
import Game.DesignSystem;
import Game.Enemy;
import Game.GameSystem;
import Game.Hound;
import Game.Hunter;
import Game.Item;
import Game.ItemMove;
import Game.Map;
import Game.MoveDown;
import Game.MoveLeft;
import Game.MoveRight;
import Game.MoveUp;
import Game.Player;
import Game.Strategist;
import Game.Objects.Arrow;
import Game.Objects.Bomb;
import Game.Objects.Boulder;
import Game.Objects.Door;
import Game.Objects.Exit;
import Game.Objects.FloorSwitch;
import Game.Objects.HoverPotion;
import Game.Objects.InvincibilityPotion;
import Game.Objects.ItemFactory;
import Game.Objects.Key;
import Game.Objects.Pit;
import Game.Objects.Sword;
import Game.Objects.Treasure;
import Game.Objects.Wall;
import javafx.animation.PathTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Dialog;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DesignScreen extends Controller{
	
	private DesignSystem designSystem;
	@FXML private ToggleGroup itemchoices;
	private ImageView tileImg;
	@FXML private ImageView arrowImg;
	@FXML private ImageView exitImg;
	@FXML private ImageView playerImg;
	@FXML private ImageView houndImg;
	@FXML private ImageView pitImg;
	@FXML private ImageView doorImg;
	@FXML private ImageView wallImg;
	@FXML private ImageView hoverImg;
	
	@FXML private GridPane itemPane;
	private ArrayList<Node> tiles = new ArrayList<Node>();
	private ImageView selectImg;
	@FXML private GridPane mapPane;
    public DesignScreen(Stage s,DesignSystem sys) {
        super(s, "Design Mode","UI/DesignMode.fxml");
        this.designSystem = sys;
    }

    @FXML
    public void initialize() {
    	this.getStage().setMinHeight(600);
    	this.getStage().setMinWidth(1000);
    	this.getStage().setMaxHeight(600);
    	this.getStage().setMaxWidth(1000);
        Map map = designSystem.getMap();
        map.printMap("temp1");
        for(int i = 0;i<20;i++) {
            for(int j = 0; j <20;j++) {
                Image image = new Image("Resource/tile.png");
                tileImg = new ImageView();
                tileImg.setImage(image);
                tileImg.setFitHeight(32);
                tileImg.setFitWidth(40);
                mapPane.add(tileImg, i, j);
                tiles.add(tileImg);
                if(i==0 || j==0||i==19||j==19) {
                	image = new Image("Resource/Wall.png");
                    tileImg = new ImageView();
                    tileImg.setImage(image);
                    tileImg.setFitHeight(30);
                    tileImg.setFitWidth(33);
                    mapPane.add(tileImg, i, j);
                    map.addItem(new Wall(i,j));
                }
                Item item = map.checkStaticItems(i, j);
                if(item != null)
                     mapPane.add(item.getImage(), i, j);
                item = map.checkDynamicItems(i, j);
                if(item!=null) {
                    mapPane.add(item.getImage(), i, j);
                }
            }

        }
        Player p = map.getPlayer();
        if(p!=null) mapPane.add(p.getImage(),p.getX(),p.getY());

        ArrayList<Node> itemList = new ArrayList<Node>();
        for(int i=0;i<4;i++) {
        	for(int j=0;j<6;j++) {
        		Node n = itemPane.getChildren().get(j+i*6);
        		if(n instanceof ImageView) {
        			itemList.add(n);
	        		ImageView image = imageCopy((ImageView)n);
	    			image.setCursor(Cursor.OPEN_HAND);
	    			makeDraggable(image);
	    			itemPane.add(image, i,j);
        		}
        	}
        }
        for(Node n:itemList) {
        	this.itemPane.getChildren().remove(n);
        }
        makeDroppable();
        mapPane.autosize();
        
    }
    
	private void makeDroppable() {
		GridPane map = this.mapPane;
		DesignSystem sys = this.designSystem;
		for(Node n:map.getChildren()) {
			n.setOnDragOver(new EventHandler<DragEvent>() {
			public void handle(DragEvent event) {
					if (event.getDragboard().hasImage()) {
						event.acceptTransferModes(TransferMode.ANY);
					}
					event.consume();
				}
			});
			n.setOnDragDropped(new EventHandler<DragEvent>() {
				public void handle(DragEvent event) {
					ImageView newImg;
					Dragboard db = event.getDragboard();
					if (db.hasImage()) {
						int j = mapPane.getRowIndex(n);
						int i = mapPane.getColumnIndex(n);
						if(sys.getMap().checkCoordinate(i, j)==null) {
							ItemFactory fact = new ItemFactory();
							Item item =fact.getItem(selectImg.getId(),i, j);
							if(sys.getMap().getPlayer()==null|| !item.isPlayer()) {
								newImg = imageCopy(new ImageView(db.getImage()));
								mapPane.add(newImg, i, j);
								item.setExtraImage(newImg);
								sys.addItem(item);
							}
						}
						
					}
					event.consume();
				}
			});
		}
	}
	@FXML
	public void runMap() {
		GameSystem sys = this.designSystem.runMap();
		if(sys.getPlayer()==null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Oh no");
			alert.setHeaderText(null);
			alert.setContentText("You should place a player before you start!");
			alert.showAndWait();
		}
		else {
			this.designSystem.getMap().printMap("temp");
			sys.initialiseMap("DIYmaps/temp.txt");
			GameScreen game = new GameScreen(this.getStage());
			game.setPreviousController(this);
			game.setSystem(sys);
			game.start();
		}
	}

    private void makeDraggable(ImageView node) {
		node.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				Dragboard db = node.startDragAndDrop(TransferMode.ANY);
				ClipboardContent content = new ClipboardContent();
				selectImg = imageCopy(node);
				content.putImage(node.getImage());
				db.setContent(content);
				event.consume();
			}
		});
	}
    
    private ImageView imageCopy(ImageView srcImage) {
		ImageView image = new ImageView(srcImage.getImage());
		image.setFitWidth(30);
		image.setFitHeight(33);
		image.setId(srcImage.getId());
		return image;
	}
    
    protected Map getMap() {
    	return designSystem.getMap();
    }

    
    @FXML
    public void handleHomeBtn() {
    	HomeScreen home = new HomeScreen(this.getStage());
    	home.setPreviousController(this);
    	home.start();
    }
    
    @FXML
    public void printMap() {
    	GameSystem sys = this.designSystem.runMap();
		if(sys.getPlayer()==null) {
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Oh no");
			alert.setHeaderText(null);
			alert.setContentText("You should place a player before you start!");
			alert.showAndWait();
		}
		else {
			TextInputDialog d = new TextInputDialog();
			d.setHeaderText("What's your name of your file?");
			Optional<String> result =d.showAndWait();
			try{
			    sys.getMap().printMap(result.get());
			}
			catch (Exception e){
			    return;
			}
		}
    }
    
    @FXML
    public void handleBackwordBtn() {
    	Item item = this.designSystem.undo();
    	updateMapPane();
    }
    public void updateMapPane() {
    	Map map = this.designSystem.getMap();
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
    	if(p!=null)mapPane.add(playerImg,p.getX(),p.getY());
    }
   
}
