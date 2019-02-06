package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Start1 extends Controller {

    public Start1(Stage s) {
    	super(s, "Choose Difficulty", "UI/Start-1.fxml");
    }

    @FXML
    private Button easyBtn;
    @FXML
    private Button mediumBtn;
    @FXML
    private Button hardBtn; 
    @FXML
    private Button exitBtn;
    @FXML
    private Button backBtn;
    
    @FXML
    public void initialize() {
        this.getStage().setMinHeight(435);
        this.getStage().setMinWidth(600);
        this.getStage().setMaxHeight(435);
        this.getStage().setMaxWidth(600);
    }
    
    @FXML
    public void handleEasyBtn() {
    	this.selectLevel("Easy");
    }
    
    @FXML
    public void handleMediumBtn() {
    	this.selectLevel("Medium");
    }
    
    @FXML
    public void handleHardBtn() {
    	this.selectLevel("Hard");
    }

    @FXML
    public void handleBackBtn() {
        HomeScreen home = new HomeScreen(this.getStage());
        home.setPreviousController(this.getPreviousController());
        home.start();
    }
    
    /**
     * Pass the difficulty selected by user to the level selecting screen
     * @param difficulty
     */
    public void selectLevel(String difficulty) {
    	Start2 start_2 = new Start2(this.getStage(), difficulty);
        start_2.start();
    }
}
