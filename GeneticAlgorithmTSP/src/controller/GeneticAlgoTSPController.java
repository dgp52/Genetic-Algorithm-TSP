package controller;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
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
    private File file;
    private PrintWriter xFileWriter, yFileWriter;
    private ObservableList<Point> listPoints;

	@FXML
	Canvas brutecanvas, geneticcanvas;
	
	@FXML
	AnchorPane bparent, gparent;
	
	@FXML
	Button btn, startalgo, clearbtn;
	
	@FXML
	ListView<Point> pointsList;
	
	@FXML
	Label gPointCount, bPointCount;
	
	@FXML
	protected void initialize() {
		resizeCanvasToParent();
		initializePoints();
		initializeGraphicsContext();
		brutecanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
            	
            /*Enable start btn if two points are on canvas. 
             * Therefore, in order to start the algorithm you must have at least two points.*/
            	if(points.getSize() >= 1) {
            		startalgo.setDisable(false);
            	}
            	
            	points.addPoint(new Point(event.getX()-DRAW_CENTER, event.getY()-DRAW_CENTER));
            	drawBruteGeneticPoints(Color.WHITE);
            	
            	listPoints = FXCollections.observableList(points.getPoints());
            	pointsList.setItems(listPoints);
            	pointsList.getSelectionModel().select(points.getSize()-1);
            	pointsList.scrollTo(points.getSize()-1);
            	
            	gPointCount.setText(String.valueOf(points.getSize()));
            	bPointCount.setText(String.valueOf(points.getSize()));
            	
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
			//Clear Canvas
        	brutegc.clearRect(0, 0, brutecanvas.getWidth(), brutecanvas.getHeight());
        	geneticgc.clearRect(0, 0, geneticcanvas.getWidth(), geneticcanvas.getHeight());
        	points.clearPoints();
        	startalgo.setDisable(true);
        	
        	listPoints.clear();
        	pointsList.getItems().clear();
        	pointsList.getSelectionModel().clearSelection();
        	listPoints = FXCollections.observableList(points.getPoints());
        	pointsList.setItems(listPoints);
        	
        	gPointCount.setText("0");
        	bPointCount.setText("0");
		}
	 }
	 
	 public void createXFile() {
		 boolean fileExist = true;
			try {
				file = new File("data/xPermutations.txt");
				fileExist = file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(!fileExist) {
				PrintWriter writer;
				try {
					writer = new PrintWriter(file);
					writer.print("");
					writer.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} 
			
			 try {
				 xFileWriter = new PrintWriter(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	 }
	 
	 public void createYFile() {
		 boolean fileExist = true;
			try {
				file = new File("data/ypermutations.txt");
				fileExist = file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			if(!fileExist) {
				PrintWriter writer;
				try {
					writer = new PrintWriter(file);
					writer.print("");
					writer.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			} 
			
			 try {
				 yFileWriter = new PrintWriter(file);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	 }
	 
	 
	 public void runBruteForce(Point[] arr){
		createXFile();
		createYFile();
 		bruteForce(arr, 0);
 		xFileWriter.close();
 		yFileWriter.close();
 		draw();
	 }
	 
	 private int factorial(int n){    
		  if (n == 0) {   
		    return 1;  
		  } else  {  
		    return(n * factorial(n-1)); 
		  }
	 }    
	 
	 private void draw () {
		 Task<Void> task = new Task<Void>() {
             @Override 
             public Void call() throws Exception {
            	 
            	 int numPoints = factorial(points.getSize());
            	 for(int i = 0; i < numPoints; i++) {
            		 
            	 }
            	 
//            	 try (Stream<String> lines = Files.lines(Paths.get("data/xPermutations.txt"))) {
//            		    line32 = lines.skip(31).findFirst().get();
//            		}
//            	 brutegc.clearRect(0, 0, brutecanvas.getWidth(), brutecanvas.getHeight()); 
//            	 brutegc.strokePolyline(xVal, yVal, points.getSize());
                 return null ;
             }
         };
		new Thread(task).start();
	 }
	 
	 //TODO add a method to read the coordinates line by line then draw the line 
	 
	private void bruteForce(Point[] arr, int index){
	    if(index >= arr.length - 1){
	        for(int i = 0; i < arr.length - 1; i++){
	            p.add(arr[i]);
	            xFileWriter.print(arr[i].getX()+DRAW_CENTER + " ");
	            yFileWriter.print(arr[i].getY()+DRAW_CENTER + " ");
	        }
	        if(arr.length > 0) {
	            p.add(arr[arr.length - 1]);
	            xFileWriter.print(arr[arr.length - 1].getX()+DRAW_CENTER + " ");
	            yFileWriter.print(arr[arr.length - 1].getY()+DRAW_CENTER + " ");
	        }
	        
	        xFileWriter.println("");
	        yFileWriter.println("");
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
