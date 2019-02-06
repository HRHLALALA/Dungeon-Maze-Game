package controllers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Controller {
	private Stage stage;
    private String screenTitle;
    private String fxmlFile;
    private FXMLLoader fxmlLoader;
    private Controller previousController;

    /**
	 * @return the previousController
	 */
	public Controller getPreviousController() {
		return previousController;
	}

	/**
	 * @param previousController the previous controller to set
	 */
	public void setPreviousController(Controller previousController) {
		this.previousController = previousController;
	}
	/**
	 * @param stage the stage to set
	 */
	public void setStage(Stage stage) {
		this.stage = stage;
	}
	/**
	 * Restart the screen
	 */
	public void restart() {
		this.fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(this.fxmlFile));
		start();
	}
	/**
	 * 
	 * @param stage The stage to set 
	 * @param screenTitle the title to set
	 * @param fxmlFile the fxml path
	 */
	public Controller(Stage stage, String screenTitle, String fxmlFile) {
        this.stage = stage;
        this.screenTitle = screenTitle;
        this.fxmlFile = fxmlFile;
        this.fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource(fxmlFile));
    }
	/**
	 * @return the current stage
	 */
    public Stage getStage() {
        return stage;
    }
    /**
     * Start the screen
     */
    public void start() {
        stage.setTitle(screenTitle);
        fxmlLoader.setController(this);
        try {
            Parent root = fxmlLoader.load();
            Scene sc = new Scene(root);
            stage.setScene(sc);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
