package controllers;

import javafx.application.Application;
import javafx.stage.Stage;
public class Main extends Application {
	
    @Override
    public void start(Stage primaryStage) {
    	primaryStage.setResizable(false);
    	Controller c = new HomeScreen(primaryStage);
    	c.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
