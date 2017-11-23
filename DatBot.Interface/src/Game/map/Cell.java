package Game.map;

import java.util.ArrayList;
import java.util.List;

import Game.map.elements.BasicElement;

public class Cell {
    public int CellId;
    public List<BasicElement> Elements = new ArrayList<BasicElement>();
    public int ElementsCount;
}
