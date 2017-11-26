package model;

import java.util.ArrayList;
import java.util.List;

public class Points {
	private List<Point> points;

	public Points() {
		points = new ArrayList<>();
	}
	
	public void addPoint(Point point) {
		points.add(point);
	}
	
	public Point getLastPoint() {
		Point p;
		if(points.size() > 0) {
			p = points.get(points.size()-1);
		} else {
			p = null;
		}
		return p;
	}
	
	public Point getSecondLastPoint() {
		Point p;
		if(points.size() > 1) {
			p = points.get(points.size()-2);
		} else {
			p = null;
		}
		return p;
	}
	
	public int getSize() {
		return points.size();
	}
	
	public void printPoints() {
		for(Point p: points) {
			System.out.print("("+p.toString()+")");
		}
		System.out.println("");
	}
	
	public List<Point> getPoints() {
		return points;
	}

}
