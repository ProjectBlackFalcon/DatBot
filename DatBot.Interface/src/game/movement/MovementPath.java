package game.movement;

import java.util.ArrayList;
import java.util.List;

import game.map.MapPoint;

public class MovementPath {
	
    public MapPoint CellEnd;
    public MapPoint CellStart;
    public List<PathElement> Cells = new ArrayList<PathElement>();


    // Methods
    public void Compress()
    {
        if (Cells.size() > 0)
        {
            int i = Cells.size() - 1;
            while (i > 0)
            {
                if (Cells.get(i).Orientation == Cells.get(i - 1).Orientation)
                {
                    Cells.remove(i);
                    i -= 1;
                }
                i -= 1;
            }
        }
    }
}
