package controller;


import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
public class GeneticAlgoTSPController {
	
	@FXML
	Canvas brutecanvas;
	
	@FXML
	AnchorPane bparent;
	
	@FXML
	protected void initialize() {
		//Resize canvas to parent size
		brutecanvas.widthProperty().bind(bparent.widthProperty()); 
		brutecanvas.heightProperty().bind(bparent.heightProperty()); 
		brutecanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 
                new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
            	double x = event.getX();
            	double y = event.getY();
            	GraphicsContext gc = brutecanvas.getGraphicsContext2D();
        		draw(gc,x,y);
            }
        });
	}
	
	 private void draw(GraphicsContext gc, double x, double y) {
		    gc.setFill(Color.WHITE);
		    gc.fillOval(x-5, y-5, 10, 10);
	}

}
