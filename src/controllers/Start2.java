package controllers;
import java.io.FileNotFoundException;

import Game.GameSystem;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Start2 extends Controller {

	private int level;
	private String difficulty;
	private String mapPath;
	
    public Start2(Stage s, String d) {
    	super(s, "Level Screen", "UI/Start-2.fxml");
    	this.imageView = new ImageView();
    	this.setDifficulty(d);
    	this.setLevel(1);
    }
    
    @FXML
    private ToggleButton handleLevel1Btn;
    @FXML
    private ToggleButton handleLevel2Btn;
    @FXML
    private ToggleButton handleLevel3Btn; 
    @FXML
    private ToggleButton handleLevel4Btn;
    @FXML
    private ToggleButton handleLevel5Btn; 
    @FXML
    private ToggleButton handleLevel6Btn;
    @FXML
    private Button handleBackBtn; 
    @FXML
    private Button handleExitBtn;
	@FXML
	private Button backBtn;
	@FXML
	private Button startBtn;
	@FXML
    private ImageView imageView;

	@FXML
    public void initialize() {
        this.getStage().setMinHeight(435);
        this.getStage().setMinWidth(600);
        this.getStage().setMaxHeight(435);
        this.getStage().setMaxWidth(600);
    }
	
	@FXML
    public void handleLevel1Btn() {
    	this.setLevel(1);
    }
    
    @FXML
    public void handleLevel2Btn() {
    	this.setLevel(2);
    }
    
    @FXML
    public void handleLevel3Btn() {
    	this.setLevel(3);
    }
    
    @FXML
    public void handleLevel4Btn() {
    	this.setLevel(4);
    }
    @FXML
    public void handleLevel5Btn() {
    	this.setLevel(5);
    }
    
    @FXML
    public void handleLevel6Btn() {
    	this.setLevel(6);
    }

    @FXML
    public void handleBackBtn() {
        Start1 start_1 = new Start1(this.getStage());
        start_1.setPreviousController(this.getPreviousController());
        start_1.start();
    }
    
    @FXML void handleStartBtn() {
    	HomeScreen.setLastGameDiffculity(this.difficulty);
    	HomeScreen.setLastGameLevel(this.level);
    	GameSystem sys = new GameSystem();
    	sys.initialiseMap(this.mapPath);
    	GameScreen gameScreen = new GameScreen(this.getStage());
    	gameScreen.setSystem(sys);
    	gameScreen.setPreviousController(this);
    	gameScreen.start();
    }
    
    @FXML
    public void handleExitBtn() {
        this.getStage().close();
    }
    
    /**
     * 
     * @return	the level selected by user
     */
    public int getLevel() {
		return level;
	}

    /**
     * The map preview is displayed after the mode and level are selected
     * @param level
     */
	public void setLevel(int level) {
		this.level = level;
		this.mapPath = "Map/"+String.join("/", this.difficulty, String.valueOf(this.level+".txt"));
		this.imageView.setImage(new Image("Map/"+String.join("/", this.difficulty, String.valueOf(this.level+".png"))));
	}
	
	/**
	 * 
	 * @return	the mode selected by user
	 */
    public String getDifficulty() {
		return difficulty;
	}

    /**
     * 
     * @param difficulty
     */
	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

}
