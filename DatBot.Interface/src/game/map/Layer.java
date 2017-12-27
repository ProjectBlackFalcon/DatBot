package game.map;

import java.util.ArrayList;
import java.util.List;

public class Layer {
    public List<Cell> Cells = new ArrayList<Cell>();
    public long CellsCount;
    public long LayerId;
    
	public List<Cell> getCells() {
		return Cells;
	}
	public void setCells(List<Cell> cells) {
		Cells = cells;
	}
	public long getCellsCount() {
		return CellsCount;
	}
	public void setCellsCount(long cellsCount) {
		CellsCount = cellsCount;
	}
	public long getLayerId() {
		return LayerId;
	}
	public void setLayerId(long layerId) {
		LayerId = layerId;
	}
    

}
