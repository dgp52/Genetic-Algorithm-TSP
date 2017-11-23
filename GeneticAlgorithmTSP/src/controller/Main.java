package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class Main extends Application{

	/**
	 * primary stag
	 */
	public static Stage primaryStage;

	@Override
	public void start(Stage primaryStage) {
	    Main.primaryStage = primaryStage;
		try {
			BorderPane root = (BorderPane) FXMLLoader.load(getClass().getResource("view/GeneticAlgoTSP.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(Main.class.getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setTitle("Genetic Algorithm TSP");
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args String
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
