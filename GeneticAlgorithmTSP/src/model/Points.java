package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Points {
	private List<Point> points;
	
	public Points() {
		points = new ArrayList<>();
	}
	
	public void addPoint(Point point) {
		points.add(point);
	}
	
	public void printPoints() {
		for(Point p: points) {
			System.out.print(p.toString());
		}
	}

}
