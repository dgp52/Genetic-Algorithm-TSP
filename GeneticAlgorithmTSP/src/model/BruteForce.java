package model;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;

public class BruteForce implements Runnable {

	private Point[] points;
	private double shortestDistance = -1.0;
	private ArrayList<Point> shortestPath;
	private int counter, permutationsCounter = 0, p = 0;
	private volatile boolean stop = false;
	private ObservableList<Point> listPoints;
	private GraphicsContext brutegc;
	public static final int DRAW_CENTER = 5;

	@FXML
	Label bPercentage, bTime, bd;

	@FXML
	ListView<Point> bDistance;

	@FXML
	Canvas canvas;

	public BruteForce(Point[] points, Label label, Label btime, ListView<Point> bDistance, Canvas brutecanvas,
			Label bd) {
		this.points = points;
		this.counter = counter(points.length);
		this.shortestPath = new ArrayList<Point>();
		this.bPercentage = label;
		this.bTime = btime;
		this.bDistance = bDistance;
		this.canvas = brutecanvas;
		this.brutegc = brutecanvas.getGraphicsContext2D();
		this.bd = bd;
	}

	// region bruteforce
	public void start() {
		long startTime = System.nanoTime();
		this.calculatePermutations(points, p);
		long endTime = System.nanoTime();
		long elapsedTime = endTime - startTime;
		double seconds = (double) elapsedTime / 1000000000.0;

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				bTime.setText("Seconds: " + seconds);
				listPoints = FXCollections.observableList(shortestPath);
				bDistance.setItems(listPoints);
				bd.setText("Distance: " + shortestDistance);
				brutegc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

				for (int i = 0; i < shortestPath.size() - 1; i++) {
					drawBrute(brutegc, shortestPath.get(i), Color.GOLD);
					drawALine(brutegc, shortestPath.get(i + 1), shortestPath.get(i));
				}
			}
		});

		stop = true;
	}
	// endregion

	// region helper methods
	private void drawBrute(GraphicsContext gc, Point point, Color c) {
		gc.setFill(c);
		gc.setStroke(c);
		gc.setLineWidth(3);
		gc.fillOval(point.getX(), point.getY(), 10, 10);
	}

	private void drawALine(GraphicsContext gc, Point last, Point secondLast) {
		if (shortestPath.size() > 1) {
			Point pLast = last;
			Point pSecondLast = secondLast;

			double xVal[] = new double[2];
			xVal[0] = pSecondLast.getX() + DRAW_CENTER;
			xVal[1] = pLast.getX() + DRAW_CENTER;

			double yVal[] = new double[2];
			yVal[0] = pSecondLast.getY() + DRAW_CENTER;
			yVal[1] = pLast.getY() + DRAW_CENTER;

			gc.strokePolyline(xVal, yVal, 2);
		}
	}

	private double calculateTotalDistance(ArrayList<Point> points) {
		double sum = 0.0;
		for (int i = 0; i < points.size(); i++) {
			if (i < points.size() - 1) {
				sum = sum + Math.hypot(points.get(i).getX() - points.get(i + 1).getX(),
						points.get(i).getY() - points.get(i + 1).getY());
			}
		}
		return sum;
	}

	private int factorial(int n) {
		return (n == 0 || n == 1) ? 1 : n * factorial(n - 1);
	}

	private int counter(int length) {
		return factorial(length) / length;
	}

	@SuppressWarnings("unchecked")
	private void calculatePermutations(Point[] points, int p) {
		float percentage = 100 * ((float) permutationsCounter) / counter;
		String fPercentage = String.format("%2.02f", percentage);
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				bPercentage.setText("Percentage: " + fPercentage + "%");
			}
		});

		if (p >= points.length - 1 && permutationsCounter < counter && !stop) {
			permutationsCounter++;
			ArrayList<Point> individualPermutation = new ArrayList<Point>();
			for (int i = 0; i < points.length - 1; i++) {
				individualPermutation.add(points[i]);
			}
			if (points.length > 0) {
				individualPermutation.add(points[points.length - 1]);
			}

			// Add the starting point at the end
			individualPermutation.add(individualPermutation.get(0));

			double tempDistance = calculateTotalDistance(individualPermutation);
			if (shortestDistance < 0) {
				shortestDistance = tempDistance;
				shortestPath = (ArrayList<Point>) individualPermutation.clone();
			} else if (tempDistance < shortestDistance) {
				shortestDistance = tempDistance;
				shortestPath = (ArrayList<Point>) individualPermutation.clone();
			}

			individualPermutation.clear();
			return;
		}

		if (permutationsCounter < counter && !stop) {
			for (int i = p; i < points.length; i++) {
				Point t = points[p];
				points[p] = points[i];
				points[i] = t;
				calculatePermutations(points, p + 1);
				t = points[p];
				points[p] = points[i];
				points[i] = t;
			}
		} else {
			return;
		}
	}

	@Override
	public void run() {
		while (!stop) {
			start();
		}

		if (stop) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					bPercentage.setText("Percentage: %");
				}
			});
		}
	}
	// enregion

	// region getters
	public boolean getStop() {
		return stop;
	}
	// endregion

	// region setters
	public void setStop(boolean stop) {
		this.stop = stop;
	}
	// endregion
}
