package model;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.paint.Color;

public class GeneticAlgo implements Runnable {

	private Population p;
	private Points bestPath;
	private int generation;
	private volatile boolean stop = false;
	private ObservableList<Point> listPoints;
	private GraphicsContext gc;
	public static final int DRAW_CENTER = 5;

	@FXML
	Label gd, generationL;

	@FXML
	ListView<Point> gDistance;

	@FXML
	Canvas gcanvas;

	public GeneticAlgo(List<Point> pp, int populationSize, int gen, ListView<Point> listd, Canvas canvas, Label l,
			Label generation) {
		this.p = new Population(pp, populationSize);
		this.bestPath = new Points();
		this.generation = gen;
		this.gd = l;
		this.gcanvas = canvas;
		this.gDistance = listd;
		this.gc = gcanvas.getGraphicsContext2D();
		this.generationL = generation;
		bestPath.setDistance(0.0);
	}

	// region genetic algorithm
	public void start() {
		int i = 0;
		p.createPopulation();
		while (i < generation && !stop) {
			p.calculateFitness();
			bestPath();
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					generationL.setText("Generation: " + p.getGeneration());
				}
			});
			p.naturalSelection();
			i++;
		}

		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				listPoints = FXCollections.observableList(bestPath.getPoints());
				gDistance.setItems(listPoints);
				gd.setText("Distance: " + bestPath.getDistance());
				gc.clearRect(0, 0, gcanvas.getWidth(), gcanvas.getHeight());

				for (int i = 0; i < bestPath.getSize() - 1; i++) {
					drawPoint(gc, bestPath.getPoints().get(i), Color.GOLD);
					drawALine(gc, bestPath.getPoints().get(i + 1), bestPath.getPoints().get(i));
				}
			}
		});

		stop = true;
	}
	// endregion

	// region helper methods
	private void drawPoint(GraphicsContext gc, Point point, Color c) {
		gc.setFill(c);
		gc.setStroke(c);
		gc.setLineWidth(3);
		gc.fillOval(point.getX(), point.getY(), 10, 10);
	}

	public void drawALine(GraphicsContext gc, Point last, Point secondLast) {
		if (bestPath.getSize() > 1) {
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

	public void bestPath() {
		for (Points pp : p.getPopulation()) {
			if (bestPath.getDistance() == 0.0) {
				bestPath.setDistance(pp.getDistance());
				bestPath.setPoints(pp.getPoints());
			} else if (pp.getDistance() < bestPath.getDistance()) {
				bestPath.setDistance(pp.getDistance());
				bestPath.setPoints(pp.getPoints());
			}
		}
	}

	@Override
	public void run() {
		while (!stop) {
			start();
		}
	}
	// endregion

	// region print
	public void printBestPath() {
		System.out.println(" Distance: " + bestPath.getDistance() + "====" + bestPath.getPoints().toString());
	}
	// endregion

	// region setters
	public void setStop(boolean stop) {
		this.stop = stop;
	}
	// endregion

	// region getters
	public boolean getStop() {
		return stop;
	}
	// endregion
}
