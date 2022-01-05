package math;

public class Vector2D {
	private double x,y;
	public Vector2D(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public Vector2D() {
		x=0;
		y=0;
	}

	public double getMagnitude() {
		return Math.sqrt(x*x+y*y);
	}
	
	public Vector2D setDirection(double angle) {
		double magnitude = getMagnitude();
		return new Vector2D(Math.cos(angle)*magnitude,Math.sin(angle)*magnitude);
	}
	
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	public double getY() {
		return y;
	}

	public void setY(double y) {
		this.y = y;
	}

	public Vector2D add(Vector2D vel) {
		return new Vector2D(x + vel.getX(),y+vel.getY());
	}
	
	public Vector2D resta(Vector2D vel) {
		return new Vector2D(x - vel.getX(),y-vel.getY());
	}
	
	public Vector2D scale(double val) {
		return new Vector2D(x*val,y*val);
	}
	
	public Vector2D limit(double val) {
		if(getMagnitude()>val)
			return this.unit().scale(val);
		return this;
	}
	
	public Vector2D unit() {
		double magnitude = getMagnitude();
		return new Vector2D(x/magnitude,y/magnitude);
	}
}
