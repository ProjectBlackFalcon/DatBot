package ia.map;

public class TransformedCell{
	Position pos;
	private boolean Los;
	private boolean Mov;
	private boolean nonWalkableDuringFight;
	
	public TransformedCell(int x, int y, boolean Los, boolean Mov, boolean nonWalkableDuringFight) {
		pos = new Position(x, y);
		this.Los = Los;
		this.Mov = Mov;
		this.nonWalkableDuringFight = nonWalkableDuringFight;
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

	public boolean isNonWalkableDuringFight() {
		return nonWalkableDuringFight;
	}

	public void setNonWalkableDuringFight(boolean nonWalkableDuringFight) {
		this.nonWalkableDuringFight = nonWalkableDuringFight;
	}

	@Override
	public String toString() {
		return "TransformedCell [pos=" + pos + ", Los=" + Los + ", Mov=" + Mov + ", nonWalkableDuringFight=" + nonWalkableDuringFight + "]";
	}
}