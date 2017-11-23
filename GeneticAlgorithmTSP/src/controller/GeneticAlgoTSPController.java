package controller;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;

public class GeneticAlgoTSPController {
	
	@FXML
	Canvas brutecanvas;
	
	@FXML
	protected void initialize() {
		System.out.println(brutecanvas.getId());
		brutecanvas.setStyle("-fx-background-color: red");
	}

}
