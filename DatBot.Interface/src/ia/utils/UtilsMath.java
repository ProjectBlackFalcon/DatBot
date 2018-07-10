package ia.utils;

import java.util.ArrayList;
import java.util.List;

import ia.entities.entity.Entity;
import ia.fight.astar.AStarMap;
import ia.fight.astar.ExampleFactory;
import ia.fight.astar.ExampleNode;
import ia.map.Position;
import ia.map.TransformedCell;

public class UtilsMath {

	public static List<Position> getPath(TransformedCell[][] cells, Position start, Position target, boolean diagonal) {
		AStarMap<ExampleNode> myMap = new AStarMap<>(34, 34, new ExampleFactory(), diagonal);

		for (int i = 0; i < cells.length; i++) {
			for (int j = 0; j < cells.length; j++) {
				if ((!cells[i][j].isMov() && (start.getX() != i || start.getY() != j) && (target.getX() != i || target.getY() != j)) || cells[i][j].isNonWalkableDuringFight()) {
					myMap.setWalkable(i, j, false);
				}
			}
		}

		List<ExampleNode> path = myMap.findPath(start.getX(), start.getY(), target.getX(), target.getY());

		ArrayList<Position> positions = new ArrayList<>();

		for (ExampleNode aPath : path) {
			positions.add(new Position(aPath.getxPosition(), aPath.getyPosition()));
		}

		return positions;
	}
	
	public static boolean isPositionAccessible(List<Position> path, Entity entity){
		return path.size() <= entity.getInfo().getStats().getMovementPoints() && path.size() > 0;
	}
}
