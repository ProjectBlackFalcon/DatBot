package game.movement;

public class CellInfo {
	public CellInfo(double heuristic, int[] parent, boolean opened, boolean closed, int x, int y) {
		Heuristic = heuristic;
		Parent = parent;
		Opened = opened;
		Closed = closed;
		X = x;
		Y = y;
	}

	public double Heuristic;
	public int[] Parent;
	public boolean Opened;
	public boolean Closed;
	public int MovementCost;
	public int X;
	public int Y;
}
