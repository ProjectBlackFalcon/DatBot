package game.movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.SwingUtilities;

import game.Info;
import game.map.MapMovement;
import protocol.network.Network;
import utils.Astar;

public class Movement{
	
	private Network network;
	
	public Movement(Network network){
		this.network = network;
	}
	
	public CellMovement MoveToCell(int cellId) throws Exception{
		if(this.network.getMap().getCells().get(cellId).isMov()){
			if(this.network.getInfo().isJoinedFight()){
				CellMovement mov = new CellMovement(new Pathfinder(this.network).findPath(this.network.getInfo().getCellId(),cellId,false,false), this.getNetwork());
				return mov;
			} else {
				CellMovement mov = new CellMovement(new Pathfinder(this.network).findPath(this.network.getInfo().getCellId(),cellId), this.getNetwork());
				return mov;
			}
		} else {
			return null;
		}
	}
	
	public MapMovement ChangeMap(String direction) throws Exception{
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
        for (int i = 0; i < this.network.getMap().getCells().size() - 1; i++){
            if ((this.network.getMap().getCells().get(i).getMapChangeData() & num2) > 0 && this.network.getMap().NothingOnCell(i) && noObstacle(i)){
            	list.add(i);
            }
        }
        if(list.size() == 0) return null; 
        Random r = new Random();
        int randomCellId = list.get(r.nextInt(list.size()));
        CellMovement move = MoveToCell(randomCellId);
        return new MapMovement(move, neighbourId,this.getNetwork());
	}
	
	public MapMovement ChangeMap(int cellId, String direction) throws Exception{
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
        if (this.network.getMap().NothingOnCell(cellId) && noObstacle(cellId) && (this.network.getMap().getCells().get(cellId).getMapChangeData() & num2) > 0){ 
            CellMovement move = MoveToCell(cellId);
            return new MapMovement(move, neighbourId,this.getNetwork());
        } else if (this.network.getInfo().getCellId() == cellId){
            return new MapMovement(null, neighbourId,this.getNetwork());
        } else {
        	// Get available cell for the direction choosed
            List<Integer> list = new ArrayList<Integer>();
            for (int i = 0; i < this.network.getMap().getCells().size() - 1; i++){
                if ((this.network.getMap().getCells().get(i).getMapChangeData() & num2) > 0 && this.network.getMap().NothingOnCell(i) && noObstacle(i)){
                	list.add(i);
                }
            }
            if(list.size() == 0 ){
            	return null;
            }
        	int closestCellId = getClosetChangedMapAvailableCell(cellId, list);
        	if(closestCellId == -1){
        		return null;
        	}
        	this.network.append("Cell " + cellId + " can't be used to change map : now using cell " + closestCellId);
            CellMovement move = MoveToCell(closestCellId);
            return new MapMovement(move, neighbourId,this.getNetwork());        }
	}
	
	public boolean moveOver() throws InterruptedException{
		int indexTimeout = 0;
		while(!this.network.getInfo().isWaitForMov()){
			Thread.sleep(1000);
			indexTimeout++;
			if(indexTimeout == 30){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Get the closest cellid from the original cellid
	 * @param cell : int
	 * @param listCellAvailable : List<int>
	 * @return closestCell : int
	 */
	public static int getClosetChangedMapAvailableCell(int cell, List<Integer> listCellAvailable){
		int min = 9999;
		int newCell = -1;
		for (Integer integer : listCellAvailable) {
			if(Math.abs(integer - cell) < min){
				min = Math.abs(integer - cell);
				newCell = integer;
			}
		}
		return newCell;
	}
	
	private boolean noObstacle(int random){
        List<int[]> blocked = new ArrayList<int[]>();
        for (int i = 0; i < this.network.getInfo().getCells().size(); i++){
        	for (int j = 0; j < this.network.getInfo().getCells().get(0).size() ; j++){
        		if (((Number) this.network.getInfo().getCells().get(i).get(j)).intValue() == 1  || ((Number) this.network.getInfo().getCells().get(i).get(j)).intValue() == 2  ){
        			blocked.add(new int[]{j,i});
        		}
        	}
        }
        if(this.network.getInfo().isJoinedFight()){
            for(int i = 0 ; i < this.network.getMonsters().getMonsters().size() ; i++){
            	int cellId = this.network.getMonsters().getMonsters().get(i).getDisposition().getCellId();
            	blocked.add(new int[]{cellId/14,cellId%14});
            }
        }
        new Astar(this.network.getInfo().getCellId()%14,this.network.getInfo().getCellId()/14, random%14, random/14, blocked,false);
        if(Astar.path == null) 
        	return false; // Can't go in this direction (Obstacles)
        else 
        	return true;
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
