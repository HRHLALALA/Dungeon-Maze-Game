package controllers;

import javafx.fxml.FXML;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Game.DesignSystem;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class DesignMap1 extends Controller {
	private boolean win1 = false;
	private boolean win2 = false;
	private boolean win3 = false;
	private boolean win4 = false;
	
    public DesignMap1(Stage s) {
    	super(s, "Choose Winning Conditions", "UI/DesignMap-1.fxml");
    }

    @FXML
	private CheckBox wincon1;
    @FXML
	private CheckBox wincon2;
    @FXML
	private CheckBox wincon3;
    @FXML
	private CheckBox wincon4;
    @FXML
	private Button nextBtn;
	@FXML
	private Button backBtn;
	@FXML
    private Button startDesigns;
	
	@FXML
    public void initialize() {
        this.getStage().setMinHeight(435);
        this.getStage().setMinWidth(600);
        this.getStage().setMaxHeight(435);
        this.getStage().setMaxWidth(600);
    }
	
	@FXML
	public void handleWin1() {
		if(win1 == false) {
			win1 = true;
		}else {
			win1 = false;
		}
	}
	
	@FXML
	public void handleWin2() {
		if(win2 == false) {
			win2 = true;
		}else {
			win2 = false;
		}
	}
	
	@FXML
	public void handleWin3() {
		if(win3 == false) {
			win3 = true;
		}else {
			win3 = false;
		}
	}
	
	@FXML
	public void handleWin4() {
		if(win4 == false) {
			win4 = true;
		}else {
			win4 = false;
		}
	}
	
	@FXML
	public void startDesign() {
		DesignSystem sys = new DesignSystem();
		sys.newMap(20);
		DesignScreen m = new DesignScreen(this.getStage(),sys);
		//store in win con selection
		if(win2 == true) {
			m.getMap().loadWinningConndition(2);
		}
		if(win3 == true) {
			m.getMap().loadWinningConndition(3);
		}
		if(win4 == true) {
			m.getMap().loadWinningConndition(4);
		}
	    m.start();
	}
	
	@FXML
    public void handleBackBtn() {
    	Controller c = new HomeScreen(this.getStage());
    	c.start();
    }
    
}
