package controllers;

import java.io.File;

import Game.GameSystem;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class HomeScreen extends Controller {

	private static int lastGameLevel = 1;
	private static String lastGameDiffculity = "Medium";
	
    public HomeScreen(Stage s) {
    	super(s, "Home Screen", "UI/HomeScreen.fxml");
    }
    
    @FXML
    private Button exitBtn;
    @FXML
    private Button startBtn;
    @FXML
    private Button loadBtn; 
    @FXML
    private Button designBtn;
    @FXML
    private Button learnBtn;
    @FXML
    private Button settingsBtn;

    @FXML
    public void exitGame() {
    	System.exit(0);
    }

    @FXML
    public void initialize() {
        this.getStage().setMinHeight(570);
        this.getStage().setMinWidth(770);
        this.getStage().setMaxHeight(570);
        this.getStage().setMaxWidth(770);
    }
    @FXML
    /**
     * Go to the mode selection screen when this button is pressed
     */
    public void startGame() {
        Controller start_1 = new Start1(this.getStage());
        start_1.setPreviousController(this.getPreviousController());
        start_1.start();
    }

    @FXML
    /**
     * Load the last played game when this button is pressed
     */
    public void loadGame() {
    	Controller cont = this.getPreviousController();
    	if (cont == null) {
	    	GameSystem sys = new GameSystem();
	    	sys.initialiseMap("src/Map/"+String.join("/", HomeScreen.getLastGameDiffculity(), String.valueOf(HomeScreen.getLastGameLevel()+".txt")));
	    	//sys.initialiseMap("src/Map/9.txt");
	        GameScreen game = new GameScreen(this.getStage());
	        game.setSystem(sys);
	        game.setPreviousController(this);
	        game.start();
    	}
    	else {
    		cont.setStage(this.getStage());
    		cont.restart();
    	}
    }
    
    @FXML
    public void handleChooseBtn() {
    	FileChooser fileChooser = new FileChooser();
    	fileChooser.setTitle("Which file do you need");
    	fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files","*.txt"));
    	File selectedFile = fileChooser.showOpenDialog(getStage());
    	if(selectedFile == null) return;
    	try {
    		GameSystem sys = new GameSystem();
    		sys.initialiseMap(selectedFile.getPath());
    		GameScreen game = new GameScreen(this.getStage());
    		game.setPreviousController(this);
    		game.setSystem(sys);
    		game.start();
    	}
    	catch(Exception e){
    		Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Oh no");
			alert.setHeaderText(null);
			alert.setContentText("It is not a map file, please choose another file");
			alert.showAndWait();
    	}
    }
    
    @FXML
    public void designGame() {
    	DesignMap1 d1 = new DesignMap1(this.getStage());
    	d1.start();
    }
    
    @FXML
    public void learnGame() {
    	TutorialScreen tutSc = new TutorialScreen(this.getStage());
    	tutSc.setPreviousController(this);
    	tutSc.start();
    }
    
    /**
     * 
     * @return the level of the last game
     */
	public static int getLastGameLevel() {
		return lastGameLevel;
	}

	/**
	 * 
	 * @param lastGameLevel
	 */
	public static void setLastGameLevel(int lastGameLevel) {
		HomeScreen.lastGameLevel = lastGameLevel;
	}

	/**
	 * 
	 * @return	the difficulty of the last game
	 */
	public static String getLastGameDiffculity() {
		return lastGameDiffculity;
	}

	/**
	 * 
	 * @param lastGameDiffculity
	 */
	public static void setLastGameDiffculity(String lastGameDiffculity) {
		HomeScreen.lastGameDiffculity = lastGameDiffculity;
	}
	

}
