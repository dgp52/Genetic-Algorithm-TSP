package model;

import java.util.ArrayList;
import java.util.List;

public class Points {
	private List<Point> points;

	private double fitnessValue;
	private double distance;

	public Points() {
		points = new ArrayList<>();
	}

	// region helper methods
	public void addPoint(Point point) {
		points.add(point);
	}

	public void clearPoints() {
		points.clear();
	}

	public void calculateFitnessValue() {
		double sum = 0.0;
		for (int i = 0; i < points.size(); i++) {
			if (i < points.size() - 1) {
				sum = sum + Math.hypot(points.get(i).getX() - points.get(i + 1).getX(),
						points.get(i).getY() - points.get(i + 1).getY());
			}
		}

		distance = sum;
		// Sum == 0 -> in case where distance/sum is zero.
		fitnessValue = ((double) 1 / (sum == 0 ? 1 : sum)) * 500;
	}

	// endregion

	// region getters
	public Point getLastPoint() {
		Point p;
		if (points.size() > 0) {
			p = points.get(points.size() - 1);
		} else {
			p = null;
		}
		return p;
	}

	public Point getSecondLastPoint() {
		Point p;
		if (points.size() > 1) {
			p = points.get(points.size() - 2);
		} else {
			p = null;
		}
		return p;
	}

	public int getSize() {
		return points.size();
	}

	public List<Point> getPoints() {
		return points;
	}

	public double getFitnessValue() {
		return this.fitnessValue;
	}

	public double getDistance() {
		return distance;
	}
	// endregion

	// region setters
	public void setFitnessValue(double fv) {
		this.fitnessValue = fv;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public void setPoints(List<Point> points) {
		this.points = points;
	}
	// endregion

	// region print
	public void printPoints() {
		for (Point p : points) {
			System.out.print("(" + p.toString() + ")");
		}
		System.out.println("");
	}

	public void printFitnessValue() {
		System.out.println(fitnessValue);
	}
	// endregion

	@Override
	public String toString() {
		return "(" + points.toString() + ") ";
	}
}
