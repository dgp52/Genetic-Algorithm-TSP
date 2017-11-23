package model;

import java.util.ArrayList;
import java.util.List;

import controller.GeneticAlgoTSPController;

public class Points {
	private List<Point> points;
	
	public Points() {
		points = new ArrayList<>();
	}
	
	public void addPoint(Point point) {
		points.add(point);
	}
	
	public double[] getAllx() {
		double x[] = new double[points.size()];
		for(int i =0; i < x.length; i++) {
			Point p = points.get(i);
			x[i] = p.getX()+GeneticAlgoTSPController.DRAW_CENTER;
		}
		return x;
	}
	
	public double[] getAlly() {
		double y[] = new double[points.size()];
		for(int i =0; i < y.length; i++) {
			Point p = points.get(i);
			y[i] = p.getY()+GeneticAlgoTSPController.DRAW_CENTER;
		}
		return y;
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

}
