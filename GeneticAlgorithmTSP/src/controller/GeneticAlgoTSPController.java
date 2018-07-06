package controller;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import model.BruteForce;
import model.CustomFile;
import model.Point;
import model.Points;

public class GeneticAlgoTSPController {

	private Points points;
	private GraphicsContext brutegc, geneticgc, brutePointGC;
	public static final int DRAW_CENTER = 5;
	private List<Point> p = new ArrayList<>();
	private CustomFile xFile, yFile, allPoints;
	private ObservableList<Point> listPoints;
	private Thread thread;
	private BruteForce bruteForce;

	@FXML
	Canvas brutecanvas, geneticcanvas;

	@FXML
	AnchorPane bparent, gparent;

	@FXML
	Button btn, startalgo, clearbtn;

	@FXML
	ListView<Point> pointsList, bDistance;

	@FXML
	Label gPointCount, bPointCount, bPercentage, gPercentage, bTime, gTime, bD, gd;

	@FXML
	protected void initialize() {
		resizeCanvasToParent();
		initializePoints();
		initializeGraphicsContext();
		brutecanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {

				/*
				 * Enable start btn if two points are on canvas. Therefore, in order to start
				 * the algorithm you must have at least two points.
				 */

				Color color = Color.WHITE;
				if (points.getSize() >= 1) {
					startalgo.setDisable(false);
				} else {
					color = Color.GOLD;					
				}
				points.addPoint(new Point(event.getX() - DRAW_CENTER, event.getY() - DRAW_CENTER));
				drawBruteGeneticPoints(color);
				
				listPoints = FXCollections.observableList(points.getPoints());
				pointsList.setItems(listPoints);
				pointsList.getSelectionModel().select(points.getSize() - 1);
				pointsList.scrollTo(points.getSize() - 1);

				gPointCount.setText("Points: " + String.valueOf(points.getSize()));
				bPointCount.setText("Points: " + String.valueOf(points.getSize()));

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

	private void resizeCanvasToParent() {
		// Resize canvas to parent size
		brutecanvas.widthProperty().bind(bparent.widthProperty());
		brutecanvas.heightProperty().bind(bparent.heightProperty());

		geneticcanvas.widthProperty().bind(gparent.widthProperty());
		geneticcanvas.heightProperty().bind(gparent.heightProperty());
	}

	private void drawBruteGeneticPoints(Color c) {
		drawBrute(brutePointGC, new Point(points.getLastPoint().getX(), points.getLastPoint().getY()), c);
		drawGenetic(geneticgc, new Point(points.getLastPoint().getX(), points.getLastPoint().getY()), c);
	}

	private void drawBrute(GraphicsContext gc, Point point, Color c) {
		gc.setFill(c);
		gc.setStroke(c);
		gc.setLineWidth(3);
		gc.fillOval(point.getX(), point.getY(), 10, 10);
	}

	private void drawGenetic(GraphicsContext gc, Point point, Color c) {
		gc.setFill(c);
		gc.setStroke(c);
		gc.setLineWidth(3);
		gc.fillOval(point.getX(), point.getY(), 10, 10);
	}

	public void drawALine(GraphicsContext gc) {
		if (points.getSize() > 1) {
			Point pLast = points.getLastPoint();
			Point pSecondLast = points.getSecondLastPoint();

			double xVal[] = new double[2];
			xVal[0] = pSecondLast.getX() + DRAW_CENTER;
			xVal[1] = pLast.getX() + DRAW_CENTER;

			double yVal[] = new double[2];
			yVal[0] = pSecondLast.getY() + DRAW_CENTER;
			yVal[1] = pLast.getY() + DRAW_CENTER;

			gc.strokePolyline(xVal, yVal, 2);
		}
	}

	public void handle(ActionEvent handler) throws IOException, NoSuchAlgorithmException {
		Button b = (Button) handler.getSource();
		if (b == btn) {
		} else if (b == startalgo) {
			// Get all points and convert it to an array of double
			Point p[] = new Point[points.getSize()];
			p = points.getPoints().toArray(p);
			runBruteForce(p);
		} else if (b == clearbtn) {
			// Clear Canvas
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
			bTime.setText("");
			bD.setText("");
			
			bDistance.getItems().clear();
			bDistance.getSelectionModel().clearSelection();
			
			//Stop the thread
			if(bruteForce != null) {
				bruteForce.setStop(true);	
			}
		}
	}

	public void runBruteForce(Point[] ps) {
		xFile = new CustomFile("data/xPermutation.txt");
		yFile = new CustomFile("data/yPermutation.txt");
		allPoints = new CustomFile("data/points.txt");
		
		bruteForce = new BruteForce(ps, bPercentage, bTime, bDistance, brutecanvas, bD);
		thread = new Thread(bruteForce);
		thread.setName("Brute Force");
		thread.start();
	}
}
