package model;

import java.util.ArrayList;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class BruteForce implements Runnable {

	private Point[] points;
	private int p = 0;
	private double shortestDistance = -1.0;
	private ArrayList<Point> shortestPath;
	private int counter;
	private int permutationsCounter = 0;
	private volatile boolean stop = false;

	@FXML
	Label bPercentage;

	public BruteForce(Point[] points, Label label) {
		this.points = points;
		this.counter = counter(points.length);
		this.shortestPath = new ArrayList<Point>();
		this.bPercentage = label;
	}

	public void start() {
		this.calculatePermutations(points, p);
		System.out.println("****" + shortestDistance);
		for (Point pp : shortestPath) {
			System.out.println(pp.toString());
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
				bPercentage.setText(fPercentage + "%");
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
			
			System.out.println(tempDistance);
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

	public boolean getStop() {
		return stop;
	}

	public void setStop(boolean stop) {
		this.stop = stop;
	}

	@Override
	public void run() {
		while (!stop) {
			start();
		}
		
		if(stop) {
			Platform.runLater(new Runnable() {
				@Override
				public void run() {
					bPercentage.setText("");
				}
			});
		}
	}
}
