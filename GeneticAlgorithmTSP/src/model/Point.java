package model;

public class Point {
	private double x;
	private double y;

	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}

	// region getters
	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}
	// endregion

	// region setters
	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}
	// endregion

	@Override
	public String toString() {
		return "(" + String.valueOf(x) + "," + String.valueOf(y) + ") ";
	}

}
