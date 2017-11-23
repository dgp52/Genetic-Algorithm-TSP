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
	Canvas brutecanvas, geneticcanvas;
	
	@FXML
	AnchorPane bparent, gparent;
	
	@FXML
	protected void initialize() {
		resizeCanvasToParent();
		
		brutecanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 
                new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
            	double x = event.getX();
            	double y = event.getY();
            	GraphicsContext brutegc = brutecanvas.getGraphicsContext2D();
            	GraphicsContext geneticgc = geneticcanvas.getGraphicsContext2D();
        		drawBrute(brutegc,x,y);
        		drawGenetic(geneticgc,x,y);
            }
        });
	}
	
	private void resizeCanvasToParent () {
		//Resize canvas to parent size
		brutecanvas.widthProperty().bind(bparent.widthProperty()); 
		brutecanvas.heightProperty().bind(bparent.heightProperty()); 
		
		geneticcanvas.widthProperty().bind(gparent.widthProperty()); 
		geneticcanvas.heightProperty().bind(gparent.heightProperty()); 
	}
	
	private void drawBrute(GraphicsContext gc, double x, double y) {
	    gc.setFill(Color.WHITE);
	    gc.fillOval(x-5, y-5, 10, 10);
	}
	
	 private void drawGenetic(GraphicsContext gc, double x, double y) {
	    gc.setFill(Color.WHITE);
	    gc.fillOval(x-5, y-5, 10, 10);
	}

}
