package game.map;

public class CellData {
	public void setArrow(long arrow) {
		Arrow = arrow;
	}
	public void setBlue(boolean blue) {
		Blue = blue;
	}
	public void setFarmCell(boolean farmCell) {
		FarmCell = farmCell;
	}
	public void setFloor(long floor) {
		Floor = floor;
	}
	public void setHavenbagCell(boolean havenbagCell) {
		HavenbagCell = havenbagCell;
	}
	public void setLos(boolean los) {
		Los = los;
	}
	public void setLosmov(long losmov) {
		Losmov = losmov;
	}
	public void setMapChangeData(long mapChangeData) {
		MapChangeData = mapChangeData;
	}
	public void setMov(boolean mov) {
		Mov = mov;
	}
	public void setMoveZone(long moveZone) {
		MoveZone = moveZone;
	}
	public void setNonWalkableDuringFight(boolean nonWalkableDuringFight) {
		NonWalkableDuringFight = nonWalkableDuringFight;
	}
	public void setNonWalkableDuringRP(boolean nonWalkableDuringRP) {
		NonWalkableDuringRP = nonWalkableDuringRP;
	}
	public void setRed(boolean red) {
		Red = red;
	}
	public void setSpeed(long speed) {
		Speed = speed;
	}
	public void setVisible(boolean visible) {
		Visible = visible;
	}
	
	public long getArrow() {
		return Arrow;
	}
	public boolean isBlue() {
		return Blue;
	}
	public boolean isFarmCell() {
		return FarmCell;
	}
	public long getFloor() {
		return Floor;
	}
	public boolean isHavenbagCell() {
		return HavenbagCell;
	}
	public boolean isLos() {
		return Los;
	}
	public long getLosmov() {
		return Losmov;
	}
	public long getMapChangeData() {
		return MapChangeData;
	}
	public boolean isMov() {
		return Mov;
	}
	public long getMoveZone() {
		return MoveZone;
	}
	public boolean isNonWalkableDuringFight() {
		return NonWalkableDuringFight;
	}
	public boolean isNonWalkableDuringRP() {
		return NonWalkableDuringRP;
	}
	public boolean isRed() {
		return Red;
	}
	public long getSpeed() {
		return Speed;
	}
	public boolean isVisible() {
		return Visible;
	}

	public long Arrow;
	public boolean Blue;
	public boolean FarmCell;
	public long Floor;
	public boolean HavenbagCell;
	public boolean Los;
	public long Losmov = 3;
	public long MapChangeData;
	public boolean Mov;
	public long MoveZone;
	public boolean NonWalkableDuringFight;
	public boolean NonWalkableDuringRP;
	public boolean Red;
	public long Speed;
	public boolean Visible;
	
	
}
