package model;

import java.util.ArrayList;

public class BruteForce {
	
	private Point[] points;
	private  ArrayList<Point> individualPermutation; 
	private int p;
	private double shortestDistance;
	
	public BruteForce (Point[] points, int p) {
		this.points = points;
		this.p = p;
		individualPermutation = new ArrayList<Point>();
	}
	
	public void start () {
		this.calculatePermutations(points, p);
	}
	
	private double calculateTotalDistance(ArrayList<Point> points) {
		double sum = 0.0;
		for (int i = 0; i < individualPermutation.size(); i++) {
			if(i <  individualPermutation.size() - 1) {
				sum = sum + Math.hypot(points.get(i).getX()-points.get(i+1).getX(), 
						points.get(i).getY()-points.get(i+1).getY());	
			}
		}
		return sum;
	}
	
	private void calculatePermutations(Point[] points, int p) {
		if (p >= points.length - 1) {
			for (int i = 0; i < points.length - 1; i++) {
				individualPermutation.add(points[i]);
			}
			if (points.length > 0) {
				individualPermutation.add(points[points.length -1]);
			}
			
			shortestDistance = calculateTotalDistance(individualPermutation);
			System.out.println(shortestDistance);
			individualPermutation.clear();
			return;
		}

		for (int i = p; i < points.length; i++) {
			Point t = points[p];
			points[p] = points[i];
			points[i] = t;
			calculatePermutations(points, p + 1);
			t = points[p];
			points[p] = points[i];
			points[i] = t;
		}
	}
}
