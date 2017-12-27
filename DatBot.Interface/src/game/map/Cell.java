package game.map;

import java.util.ArrayList;
import java.util.List;

import game.map.elements.BasicElement;
import game.map.elements.GraphicalElement;

public class Cell {
    public List<GraphicalElement> Elements = new ArrayList<GraphicalElement>();
    public long CellId;
    public long ElementsCount;
    
	public List<GraphicalElement> getElements() {
		return Elements;
	}
	public void setElements(List<GraphicalElement> elements) {
		Elements = elements;
	}
	public long getCellId() {
		return CellId;
	}
	public void setCellId(long cellId) {
		CellId = cellId;
	}
	public long getElementsCount() {
		return ElementsCount;
	}
	public void setElementsCount(long elementsCount) {
		ElementsCount = elementsCount;
	}

}
