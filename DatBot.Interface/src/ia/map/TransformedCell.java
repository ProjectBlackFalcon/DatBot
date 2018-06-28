package ia.map;

public class TransformedCell{
	Position pos;
	private boolean Los;
	private boolean Mov;
	
	public TransformedCell(int x, int y, boolean Los, boolean Mov) {
		pos = new Position(x, y);
		this.Los = Los;
		this.Mov = Mov;
	}

	public Position getPos() {
		return pos;
	}

	public void setPos(Position pos) {
		this.pos = pos;
	}

	public boolean isLos() {
		return Los;
	}

	public void setLos(boolean los) {
		Los = los;
	}

	public boolean isMov() {
		return Mov;
	}

	public void setMov(boolean mov) {
		Mov = mov;
	}
}