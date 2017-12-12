package controller;


import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import model.Point;
import model.Points;
public class GeneticAlgoTSPController {
	
	private Points points;
	private GraphicsContext brutegc, geneticgc, brutePointGC;
	public static final int DRAW_CENTER = 5;
	private List<Point> p = new ArrayList<>();

	@FXML
	Canvas brutecanvas, geneticcanvas;
	
	@FXML
	AnchorPane bparent, gparent;
	
	@FXML
	Button btn, startalgo, clearbtn;
	
	@FXML
	protected void initialize() {
		resizeCanvasToParent();
		initializePoints();
		initializeGraphicsContext();
		brutecanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
            	
            /*Enable start btn if two points are on canvas. Therefore, in order to 
        	start the algorithm you must have at least two points.*/
            	if(points.getSize() >= 1) {
            		startalgo.setDisable(false);
            	}
            	
            	points.addPoint(new Point(event.getX()-DRAW_CENTER, event.getY()-DRAW_CENTER));
            	drawBruteGeneticPoints(Color.WHITE);
            	
        		drawALine(brutegc);
        		drawALine(geneticgc);
            }
        });
	}
	
	private void initializePoints() {
		points = new Points();
	}
	
	private void initializeGraphicsContext() {
		brutegc = brutecanvas.getGraphicsContext2D();
    	geneticgc = geneticcanvas.getGraphicsContext2D();
    	brutePointGC = brutecanvas.getGraphicsContext2D();
	}
	
	private void resizeCanvasToParent () {
		//Resize canvas to parent size
		brutecanvas.widthProperty().bind(bparent.widthProperty()); 
		brutecanvas.heightProperty().bind(bparent.heightProperty()); 
		
		geneticcanvas.widthProperty().bind(gparent.widthProperty()); 
		geneticcanvas.heightProperty().bind(gparent.heightProperty()); 
	}
	
	private void drawBruteGeneticPoints(Color c) {
		drawBrute(brutePointGC,new Point(points.getLastPoint().getX(),points.getLastPoint().getY()),c);
		drawGenetic(geneticgc,new Point(points.getLastPoint().getX(),points.getLastPoint().getY()),c);
	}
	
	private void drawBrute(GraphicsContext gc, Point point, Color c) {
	    gc.setFill(c);
	    gc.setStroke(c);
	    gc.setLineWidth(3);
	    gc.fillOval(point.getX(),point.getY(), 10, 10);
	}
	
	private void drawGenetic(GraphicsContext gc, Point point, Color c) {
	    gc.setFill(c);
	    gc.setStroke(c);
	    gc.setLineWidth(3);
	    gc.fillOval(point.getX(), point.getY(), 10, 10);
	}
	
	public void drawALine(GraphicsContext gc) {
		if(points.getSize() > 1) {
			Point pLast = points.getLastPoint();
			Point pSecondLast = points.getSecondLastPoint();
			
			double xVal[] = new double[2];
			xVal[0] = pSecondLast.getX()+DRAW_CENTER;
			xVal[1] = pLast.getX()+DRAW_CENTER;
			
			double yVal[] = new double[2];
			yVal[0] = pSecondLast.getY()+DRAW_CENTER;
			yVal[1] = pLast.getY()+DRAW_CENTER;
			
			gc.strokePolyline(xVal, yVal, 2);
		}
	}
	 
	 public void handle(ActionEvent handler) throws IOException, NoSuchAlgorithmException {
		Button b = (Button) handler.getSource();
		if (b == btn) {
		} else if(b == startalgo) {
			//Get all points and convert it to an array of double
			Point p[] = new Point[points.getSize()];
			p = points.getPoints().toArray(p);
			
			runBruteForce(p);
		} else if (b == clearbtn) {
        	brutegc.clearRect(0, 0, brutecanvas.getWidth(), brutecanvas.getHeight());
        	points.clearPoints();
		}
	 }
	 
	 public void runBruteForce(Point[] arr){
 		bruteForce(arr, 0);
	 }
	 
	private void bruteForce(Point[] arr, int index){
	    if(index >= arr.length - 1){
	        for(int i = 0; i < arr.length - 1; i++){
	            p.add(arr[i]);
	        }
	        if(arr.length > 0) {
	            p.add(arr[arr.length - 1]);
	        }
	        
	        //Draw the Path
	        double xVal[] = new double[p.size()];
	        for(int i =0; i < points.getSize(); i++) {
	        	Point xp = p.get(i);
	        	xVal[i] = xp.getX()+DRAW_CENTER;
	        }
	        
	        double yVal[] = new double[p.size()];
	        for(int i =0; i < points.getSize(); i++) {
	        	Point yp = p.get(i);
	        	yVal[i] = yp.getY()+DRAW_CENTER;
	        }
	 
	        System.out.println(Arrays.toString(xVal));
	        System.out.println(Arrays.toString(yVal));

	        new AnimationTimer() {
	            @Override
	            public void handle(long now) {
	            	brutegc.clearRect(0, 0, brutecanvas.getWidth(), brutecanvas.getHeight()); 
		        	brutegc.strokePolyline(xVal, yVal, points.getSize());
	            }
	        }.start();
	        
	        
	        return;
	    }
	
	    for(int i = index; i < arr.length; i++){
	        Point t = arr[index];
	        arr[index] = arr[i];
	        arr[i] = t;
	        bruteForce(arr, index+1);
	        t = arr[index];
	        arr[index] = arr[i];
	        arr[i] = t;
	    }
	}
}
