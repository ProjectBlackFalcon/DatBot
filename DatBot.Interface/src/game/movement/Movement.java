package game.movement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import javax.swing.SwingUtilities;

import game.Info;
import game.map.MapMovement;
import protocol.network.Network;
import utils.Astar;

public class Movement {

	private Network network;

	public Movement(Network network)
	{
		this.network = network;
	}

	public CellMovement MoveToCell(int cellId) throws Exception
	{
		if (this.network.getMap().getCells().get(cellId).isMov())
		{
			if (this.network.getInfo().isJoinedFight())
			{
				CellMovement mov = new CellMovement(new Pathfinder(this.network).findPath(this.network.getIntelligence().getMain().getInfo().getDisposition().getCellId(), cellId, false, false), this.getNetwork());
				return mov;
			}
			else
			{
				CellMovement mov = new CellMovement(new Pathfinder(this.network).findPath(this.network.getInfo().getCellId(), cellId), this.getNetwork());
				return mov;
			}
		}
		else
		{
			return null;
		}
	}

	public MapMovement ChangeMap(String direction) throws Exception
	{
		int neighbourId = -1;
		int num2 = -1;
		switch (direction)
		{
			case "North":
				neighbourId = (int) this.network.getMap().getTopNeighbourId();
				num2 = 64;
			break;
			case "South":
				neighbourId = (int) this.network.getMap().getBottomNeighbourId();
				num2 = 4;
			break;
			case "East":
				neighbourId = (int) this.network.getMap().getRightNeighbourId();
				num2 = 1;
			break;
			case "West":
				neighbourId = (int) this.network.getMap().getLeftNeighbourId();
				num2 = 16;
			break;
		}
		if (num2 == -1 || neighbourId < 0) return null;

		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i < this.network.getMap().getCells().size() - 1; i++)
		{
			if ((this.network.getMap().getCells().get(i).getMapChangeData() & num2) > 0 && this.network.getMap().NothingOnCell(i) && noObstacle(i))
			{
				list.add(i);
			}
		}
		if (list.size() == 0) return null;
		Random r = new Random();
		int randomCellId = list.get(r.nextInt(list.size()));
		CellMovement move = MoveToCell(randomCellId);
		return new MapMovement(move, neighbourId, this.getNetwork());
	}

	public MapMovement ChangeMap(int cellId, String direction) throws Exception
	{
		int neighbourId = -1;
		int num2 = -1;
		switch (direction)
		{
			case "n":
				neighbourId = (int) this.network.getMap().getTopNeighbourId();
				num2 = 64;
			break;
			case "s":
				neighbourId = (int) this.network.getMap().getBottomNeighbourId();
				num2 = 4;
			break;
			case "e":
				neighbourId = (int) this.network.getMap().getRightNeighbourId();
				num2 = 1;
			break;
			case "w":
				neighbourId = (int) this.network.getMap().getLeftNeighbourId();
				num2 = 16;
			break;
		}
		if (num2 == -1 || neighbourId < 0) return null;

		if (this.network.getInfo().getCellId() == cellId)
		{
			this.network.getLog().writeActionLogMessage("changeMap_1", "Already on cellId");
			return new MapMovement(null, neighbourId, this.getNetwork());
		}
		else if (canChangeMap(cellId) && canMoveTo(cellId) && isDirection(cellId, direction) && isAvalaibleCorner(cellId, num2))
		{
			CellMovement move = MoveToCell(cellId);
			return new MapMovement(move, neighbourId, this.getNetwork());
		}
		else
		{
			List<Integer> list = new ArrayList<>();
			for (int i = 0; i < this.network.getMap().getCells().size(); i++)
			{
				if (canChangeMap(i) && isDirection(i, direction) && isAvalaibleCorner(i, num2) && canMoveTo(i))
				{
					list.add(i);
				}
			}
			if (list.isEmpty())
			{
				this.network.getLog().writeActionLogMessage("changeMap_1", "No available cell for this direction");
				return null;
			}
			int closestCellId = getClosetChangedMapAvailableCell(cellId, list);
			if (closestCellId == -1)
			{
				this.network.getLog().writeActionLogMessage("changeMap_1", "No closest available cell for this direction");
				return null;
			}
			CellMovement move = MoveToCell(closestCellId);
			if (move.path == null) { return null; }
			this.network.getLog().writeActionLogMessage("changeMap_1", String.format("Cell %s not valid, choosing %s", cellId, closestCellId));
			this.network.append(String.format("Cell %s not valid, choosing %s", cellId, closestCellId));
			return new MapMovement(move, neighbourId, this.getNetwork());
		}
	}

	public boolean moveOver() throws InterruptedException
	{
		int indexTimeout = 0;
		while (!this.network.getInfo().isWaitForMov())
		{
			Thread.sleep(1000);
			indexTimeout++;
			if (indexTimeout == 30) { return false; }
		}
		return true;
	}

	public boolean canChangeMap(int cellId)
	{
		return this.network.getMap().getCells().get(cellId).getMapChangeData() != 0;
	}

	/**
	 * Verifiy that the cell can lead to the right direction
	 * 
	 * @param cellId
	 * @param dir
	 * @return boolean : true if direction and cell are good
	 */
	public boolean isDirection(int cellId, String dir)
	{
		boolean b = false;
		switch (dir)
		{
			case "n":
				if (cellId <= 27) b = true;
			break;
			case "s":
				if (546 <= cellId && cellId <= 559) b = true;
			break;
			case "w":
				if (cellId % 14 == 0) b = true;
			break;
			case "e":
				if (cellId % 14 == 13) b = true;
			break;
		}
		return b;
	}

	/**
	 * Check if the cell is a corner and return if it can be used in that
	 * direction
	 * 
	 * @param cellId
	 * @param dir
	 * @return true if usable cell in that direction
	 */
	private boolean isAvalaibleCorner(int cellId, int dir)
	{
		List<Integer> cellsCornered = Arrays.asList(0, 1, 14, 28,13, 26, 27, 41,518, 532, 533, 546, 558, 559, 545, 531);
		if (cellsCornered.contains(cellId))
		{
			this.network.getLog().writeActionLogMessage("changeMap_1", "Cell is corner, changemap on corner denied");
//			return (this.network.getMap().getCells().get(cellId).getMapChangeData() & dir) > 0;
			return false;
		}
		else
		{
			return true;
		}
	}

	/**
	 * Player can move to cell
	 * 
	 * @param cellId
	 * @return boolean
	 */
	public boolean canMoveTo(int cellId)
	{
		return this.network.getMap().getCells().get(cellId).isMov() && noObstacle(cellId);
	}

	/**
	 * Get the closest cellid from the original cellid
	 * 
	 * @param cell
	 *            : int
	 * @param listCellAvailable
	 *            : List<int>
	 * @return closestCell : int
	 */
	public int getClosetChangedMapAvailableCell(int cell, List<Integer> listCellAvailable)
	{
		int min = 9999;
		int newCell = -1;
		for (Integer integer : listCellAvailable)
		{
			if (Math.abs(integer - cell) < min)
			{
				if (integer == 1 || integer == 12 || integer == 14 || integer == 15 || integer == 26 || integer == 27 
					|| integer == 542 || integer == 543 || integer == 553 || integer == 554 || integer == 547 || integer == 558)
				{
					if(listCellAvailable.size() > 1) {
						continue;
					}
				}
				min = Math.abs(integer - cell);
				newCell = integer;
			}
		}
		return newCell;
	}

	private boolean noObstacle(int random)
	{
		List<int[]> blocked = new ArrayList<>();
		for (int i = 0; i < this.network.getMap().getCells().size(); i++)
		{
			if (!this.network.getMap().getCells().get(i).isMov())
			{
				blocked.add(new int[] { i % 14, i / 14 });
			}
		}
		if (this.network.getInfo().isJoinedFight())
		{
			for (int i = 0; i < this.network.getMonsters().getMonsters().size(); i++)
			{
				int cellId = this.network.getMonsters().getMonsters().get(i).getDisposition().getCellId();
				blocked.add(new int[] { cellId % 14, cellId / 14 });
			}
		}
		Astar a;
		if (this.getNetwork().getInfo().isJoinedFight())
		{
			a = new Astar(this.network.getInfo().getCellId() % 14, this.network.getInfo().getCellId() / 14, random % 14, random / 14, blocked, false);
		}
		else
		{
			a = new Astar(this.network.getInfo().getCellId() % 14, this.network.getInfo().getCellId() / 14, random % 14, random / 14, blocked, true);
		}
		return a.getPath() != null;
	}

	public Network getNetwork()
	{
		return network;
	}

	public void setNetwork(Network network)
	{
		this.network = network;
	}
}
