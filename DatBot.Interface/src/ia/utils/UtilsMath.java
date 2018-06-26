package ia.utils;

import java.util.ArrayList;
import java.util.List;

import ia.Intelligence;
import ia.entities.entity.Entity;
import ia.fight.astar.AStarMap;
import ia.fight.astar.ExampleFactory;
import ia.fight.astar.ExampleNode;
import ia.map.MapIA;
import ia.map.Position;
import protocol.network.Network;

public class UtilsMath {

	Network network;
	Intelligence ia;

	public UtilsMath(Network network, Intelligence ia)
	{
		this.network = network;
		this.ia = ia;
	}

	public List<Position> getPath(Position start, Position target, boolean DIAGONAL)
	{
		if (DIAGONAL) AStarMap.CANMOVEDIAGONALY = true;
		AStarMap<ExampleNode> myMap = new AStarMap<ExampleNode>(34, 34, new ExampleFactory());

		for (int i = 0; i < this.network.getMap().getCells().size(); i++) {
			if (!this.network.getMap().getCells().get(i).isMov()) {
				Position pos = MapIA.reshapeToIA(i);
				myMap.setWalkable(pos.getX(), pos.getY(), false);
			}
		}
		
		for (Entity i : ia.getEntities())
		{
			if(i != ia.getMain()){
				myMap.setWalkable(i.getPosition().getX(), i.getPosition().getY(), false);
			}
		}

		List<ExampleNode> path = myMap.findPath(start.getX(), start.getY(), target.getX(), target.getY());

		if (DIAGONAL) AStarMap.CANMOVEDIAGONALY = false;

		ArrayList<Position> positions = new ArrayList<>();

		for (ExampleNode aPath : path)
		{
			positions.add(new Position(aPath.getxPosition(), aPath.getyPosition()));
		}

		return positions;
	}
}
