package game.map;

import java.util.ArrayList;
import java.util.List;

import game.movement.MovementPath;
import game.movement.PathElement;

public class MapMovementAdapter {

    public static List<Integer> GetServerMovement(MovementPath Path)
    {
    	List<Integer> list = new ArrayList<Integer>();
        int orientation = 0;
        for (PathElement element : Path.Cells) {
            orientation = element.Orientation;
            list.add((((orientation & 7) << 12) | (element.Cell.CellId & 4095)));
		}
        list.add((((orientation & 7) << 12) | (Path.CellEnd.CellId & 4095)));
        return list;
    }
}
