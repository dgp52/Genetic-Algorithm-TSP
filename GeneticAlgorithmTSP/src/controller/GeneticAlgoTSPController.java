package controller;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import model.Point;
import model.Points;
public class GeneticAlgoTSPController {
	
	private Points points;
	private GraphicsContext brutegc, geneticgc;
	public static final int DRAW_CENTER = 5;
	
	@FXML
	Canvas brutecanvas, geneticcanvas;
	
	@FXML
	AnchorPane bparent, gparent;
	
	@FXML
	Button btn;
	
	@FXML
	protected void initialize() {
		resizeCanvasToParent();
		initializePoints();
		initializeGraphicsContext();
		brutecanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
            	points.addPoint(new Point(event.getX()-DRAW_CENTER, event.getY()-DRAW_CENTER));
        		drawBrute(brutegc,new Point(event.getX()-DRAW_CENTER, event.getY()-DRAW_CENTER));
        		drawGenetic(geneticgc,new Point(event.getX()-DRAW_CENTER, event.getY()-DRAW_CENTER));
        		drawPath();
            }
        });
	}
	
	private void initializePoints() {
		points = new Points();
	}
	
	private void initializeGraphicsContext() {
		brutegc = brutecanvas.getGraphicsContext2D();
    	geneticgc = geneticcanvas.getGraphicsContext2D();
	}
	
	private void resizeCanvasToParent () {
		//Resize canvas to parent size
		brutecanvas.widthProperty().bind(bparent.widthProperty()); 
		brutecanvas.heightProperty().bind(bparent.heightProperty()); 
		
		geneticcanvas.widthProperty().bind(gparent.widthProperty()); 
		geneticcanvas.heightProperty().bind(gparent.heightProperty()); 
	}
	
	private void drawBrute(GraphicsContext gc, Point point) {
	    gc.setFill(Color.WHITE);
	    gc.setStroke(Color.WHITE);
	    gc.setLineWidth(3);
	    gc.fillOval(point.getX(),point.getY(), 10, 10);
	}
	
	private void drawPath() {
		brutegc.strokePolyline(points.getAllx(), points.getAlly(), points.getSize());
	}
	
	 private void drawGenetic(GraphicsContext gc, Point point) {
	    gc.setFill(Color.WHITE);
	    gc.fillOval(point.getX(), point.getY(), 10, 10);
	}
	 
	 public void handle(ActionEvent handler) throws IOException, NoSuchAlgorithmException {
		Button b = (Button) handler.getSource();
		if (b == btn) {
			points.printPoints();
		}
	 }

}
