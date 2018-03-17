package ia.map;

public class Position {

	int x, y;
	
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public static int distance(Position p1, Position p2) {
		return Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY());
	}
	
	public String toString(){
		return getX()+","+getY();
	}
	
	public boolean deepEquals(Position p) {
		return this.getX() == p.getX() && this.getY() == p.getY();
	}

	public Object toJSON() {
		// TODO Auto-generated method stub
		return null;
	}
}
