package model;

public class BruteForce {
	
	private Point[] points;
	private int p;
	
	public BruteForce (Point[] points, int p) {
		this.points = points;
		this.p = p;
	}
	
	public void start () {
		this.calculatePermutations(points, p);
	}

	private void calculatePermutations(Point[] points, int p) {
		if (p >= points.length - 1) {
			System.out.print("[");
			for (int i = 0; i < points.length - 1; i++) {
				//other points
			}
			if (points.length > 0) {
				//last point
			}
			//Calculate the distance between all the points 
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
