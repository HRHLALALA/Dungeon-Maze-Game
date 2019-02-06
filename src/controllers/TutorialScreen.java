package controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
/**
 * this is the tutorial handler, when a button is pressed the corresponding image is loaded up explaining details on the game
 * @author Eddie
 *
 */
public class TutorialScreen extends Controller {
	
    public TutorialScreen(Stage s) {
    	super(s, "How To Play", "UI/TutorialScreen.fxml");
    	this.imageView1 = new ImageView();
    	this.imageView2 = new ImageView();
    	this.imageView3 = new ImageView();
    	this.imageView4 = new ImageView();
    }

    @FXML
    private ImageView imageView1;
	@FXML
    private ImageView imageView2;
	@FXML
    private ImageView imageView3;
	@FXML
    private ImageView imageView4;
	@FXML
    private Button backBtn;
	@FXML
    private Button ControlBtn1;
	@FXML
    private Button ControlBtn2;
	@FXML
    private Button ControlBtn3;
	@FXML
    private Button ControlBtn4;
	@FXML
    private Button ObjectiveBtn1;
	@FXML
    private Button ObjectiveBtn2;
	@FXML
    private Button ObjectiveBtn3;
	@FXML
    private Button WinBtn1;
	@FXML
    private Button WinBtn2;
	@FXML
    private Button WinBtn3;
	@FXML
    private Button ObstacleBtn1;
	@FXML
    private Button ObstacleBtn2;
	@FXML
    private Button ObstacleBtn3;
	@FXML
    private Button ItemBtn2;
	@FXML
    private Button ItemBtn3;
	@FXML
    private Button EnemyBtn3;
	
	@FXML
	private Button DesignBtn;
	@FXML
	private Button DragNDrop;
	@FXML
	private Button SaveMap;
	@FXML 
	private Button TestMap;
	
    //handler
	/**
	 * catches control button actions and displays correct png according to which tab the button is pressed on
	 * @param action
	 */
	@FXML
	public void handleControlBtn(ActionEvent action) {
		if(action.getSource().equals(ControlBtn1)) {
			this.imageView1.setImage(new Image("Tutorials/Controls.png"));
		}else if(action.getSource().equals(ControlBtn2)) {
			this.imageView2.setImage(new Image("Tutorials/Controls.png"));
		}else if(action.getSource().equals(ControlBtn3)) {
			this.imageView3.setImage(new Image("Tutorials/Controls.png"));
		}
	}
	/**
	 * catches Objective button actions and displays correct png according to which tab the button is pressed on
	 * @param action
	 */
	@FXML
	public void handleObjectiveBtn(ActionEvent action) {
		if(action.getSource().equals(ObjectiveBtn1)) {
			this.imageView1.setImage(new Image("Tutorials/easytutorial.png"));
		}else if(action.getSource().equals(ObjectiveBtn2)) {
			this.imageView2.setImage(new Image("Tutorials/mediumtutorial.png"));
		}else if(action.getSource().equals(ObjectiveBtn3)) {
			this.imageView3.setImage(new Image("Tutorials/hardtutorial.png"));
		}
	}
	/**
	 * catches win button actions and displays correct png according to which tab the button is pressed on
	 * @param action
	 */
	@FXML
	public void handleWinBtn(ActionEvent action) {
		if(action.getSource().equals(WinBtn1)) {
			this.imageView1.setImage(new Image("Tutorials/easywin.png"));
		}else if(action.getSource().equals(WinBtn2)) {
			this.imageView2.setImage(new Image("Tutorials/WinCriteria.png"));
		}else if(action.getSource().equals(WinBtn3)) {
			this.imageView3.setImage(new Image("Tutorials/WinCriteria.png"));
		}
	}
	/**
	 * catches obstacle button actions and displays correct png according to which tab the button is pressed on
	 * @param action
	 */
	@FXML
	public void handleObstacleBtn(ActionEvent action) {
		if(action.getSource().equals(ObstacleBtn1)) {
			this.imageView1.setImage(new Image("Tutorials/easyObstacles.png"));
		}else if(action.getSource().equals(ObstacleBtn2)) {
			this.imageView2.setImage(new Image("Tutorials/Obstacles.png"));
		}else if(action.getSource().equals(ObstacleBtn3)) {
			this.imageView3.setImage(new Image("Tutorials/Obstacles.png"));
		}
	}
	/**
	 * catches Item button actions and displays correct png according to which tab the button is pressed on
	 * @param action
	 */
	@FXML
	public void handleItemBtn(ActionEvent action) {
		if(action.getSource().equals(ItemBtn2)) {
			this.imageView2.setImage(new Image("Tutorials/Items.png"));
		}else if(action.getSource().equals(ItemBtn3)) {
			this.imageView3.setImage(new Image("Tutorials/Items.png"));
		}
	}
	/**
	 * catches Enemy button actions and displays correct png according to which tab the button is pressed on
	 * @param action
	 */
	@FXML
	public void handleEnemyBtn(ActionEvent action) {
		if(action.getSource().equals(EnemyBtn3)) {
			this.imageView3.setImage(new Image("Tutorials/hardenemy.png"));
		}
	}
	@FXML
	public void handleDesignBtn(ActionEvent action) {
		if(action.getSource().equals(DesignBtn)) {
			this.imageView4.setImage(new Image("Tutorials/DesignMap.png"));
		}else if(action.getSource().equals(DragNDrop)) {
			this.imageView4.setImage(new Image("Tutorials/editMap.png"));
		}else if(action.getSource().equals(SaveMap)) {
			this.imageView4.setImage(new Image("Tutorials/savemap.png"));
		}else if(action.getSource().equals(TestMap)) {
			this.imageView4.setImage(new Image("Tutorials/runningmap.png"));
		}
	}
	
	/**
	 * catches Back button actions and goes back to homescreen 
	 * 
	 */
    @FXML
    public void handleBackBtn() {
    	Controller c = this.getPreviousController();
    	c.setPreviousController(this);
    	c.setStage(this.getStage());
    	c.restart();
    }
    @Override
    public void start() {
    	super.start();
    	this.getStage().setMinHeight(570);
    	this.getStage().setMinWidth(770);
    	this.imageView1.setImage(new Image("Tutorials/Controls.png"));
    	this.imageView2.setImage(new Image("Tutorials/Controls.png"));
    	this.imageView3.setImage(new Image("Tutorials/Controls.png"));
    	this.imageView4.setImage(new Image("Tutorials/DesignMap.png"));
    }
}
