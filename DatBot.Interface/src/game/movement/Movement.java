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
        if(list.size() == 0) return null; // Can't go in this direction (Obstacles)
        Random r = new Random();
        int randomCellId = list.get(r.nextInt(list.size()));
//		this.getNetwork().append("Moving...",false);	
//		this.getNetwork().append("Direction : " + direction,false);
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
        	int closestCellId = getClosetChangedMapAvailableCell(cellId, list);
        	if(closestCellId == -1){
        		return null;
        	}
        	this.network.append("Cell " + cellId + " can't be used to change map : now using cell " + closestCellId);
            CellMovement move = MoveToCell(closestCellId);
            return new MapMovement(move, neighbourId,this.getNetwork());
        }
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
	public int getClosetChangedMapAvailableCell(int cell, List<Integer> listCellAvailable){
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
	
	
// TRIED TO GET THE WAY BUT NOT WORKING
	public void goToMap(int xStart, int yStart, int x, int y, List<int[]> blocked) throws Exception{
    	while(!this.network.getInfo().isWaitForMov()){
    		Thread.sleep(500);
		}
		if(x == this.network.getInfo().getCoords()[0] && y == this.network.getInfo().getCoords()[1]){
//			this.getNetwork().append("Vous �tes arriv� !",false);
			return;
		}
		
		int xCurrentMap = this.network.getInfo().getCoords()[0] + 95; 
		int yCurrentMap = this.network.getInfo().getCoords()[1] + 100;
//		int xNew = 0; int yNew = 0; int xStartNew = 0; int yStartNew = 0;
//		
//		// Need only positiv values
//		if(x >= -95 && y >= -100 && xStart >= -95 && yStart >= -100){
//			xNew = x + 95; yNew = y + 100;
//			xStartNew = xStart + 95; yStartNew = yStart + 100;		
//		}
		
		if(blocked == null){
			blocked = new ArrayList<int[]>();
			blocked.add(new int[] {-8+95,-28+100});
			blocked.add(new int[] {-9+95,-28+100});
		}
//		this.network.append("Size blocked : " + blocked.size());
//		this.network.append("Map : " + (xCurrentMap) + ";" + (yCurrentMap));
//		
//		for (int[] is : blocked) {
//			this.network.append("2	" + (is[0]) + ";" + (is[1]));
//		}
		
		boolean north = false;
		boolean south = false;
		boolean east = false;
		boolean west = false;	
		int index = 0 ;
		for(int k = 0; k < blocked.size() ; k++){
			if(blocked.get(k)[0] == xCurrentMap && blocked.get(k)[1] == yCurrentMap-1){
				north = true;
				index++;
			}
			if(blocked.get(k)[0] == xCurrentMap && blocked.get(k)[1] == yCurrentMap+1){
				south = true;
				index++;
			}
			if(blocked.get(k)[0] == xCurrentMap+1 && blocked.get(k)[1] == yCurrentMap){
				east = true;
				index++;
			}
			if(blocked.get(k)[0] == xCurrentMap-1 && blocked.get(k)[1] == yCurrentMap){
				west = true;
				index++;
			}
		}
		if(index == 3){
			blocked.add(new int[] {xCurrentMap, yCurrentMap});
			String way = "";
			if(!north){
				way = "North";
				yCurrentMap--;
			}
			if(!south){
				way = "South";
				yCurrentMap++;
			}
			if(!east){
				way = "East";
				xCurrentMap++;
			}
			if(!west){
				way = "West";
				xCurrentMap--;
			}
			MapMovement mov = ChangeMap(way);
			mov.PerformChangement();
			goToMap(xCurrentMap, yCurrentMap, x, y, blocked);
		}
		
        new Astar(xCurrentMap, yCurrentMap, x + 95, y + 100, blocked, true);
        
//		for (int[] is : blocked) {
//			this.network.append("2	" + (is[0]-95) + ";" + (is[1]-100));
//		}
		        
        for (int i = 0; i < Astar.pathString.size() ; i++) {
        	while(!this.network.getInfo().isWaitForMov()){
        		Thread.sleep(500);
    		}
    		if(x == this.network.getInfo().getCoords()[0] && y == this.network.getInfo().getCoords()[1]){
//    			this.getNetwork().append("Vous �tes arriv� !",false);
    			return;
    		}
    		xCurrentMap = this.network.getInfo().getCoords()[0] + 95; 
    		yCurrentMap = this.network.getInfo().getCoords()[1] + 100;
			MapMovement mov = ChangeMap(Astar.pathString.get(i));
			if (mov == null) {
//				this.getNetwork().append("D�placement impossible ! Un obstacle bloque le chemin !",false);
//				this.getNetwork().append("Cr�ation d'un nouveau chemin...",false);
				if(Astar.pathString.get(i).equals("North")){
					if(x+95 == xCurrentMap && y+100 == yCurrentMap-1){
						blocked.add(new int[] {xCurrentMap, yCurrentMap});
						goToOtherAvailableDirection(xCurrentMap, yCurrentMap, x, y, blocked);
					} else {
						blocked.add(new int[] {xCurrentMap, yCurrentMap-1});
						goToMap(xCurrentMap, yCurrentMap, x, y, blocked);
					}
				}
				else if(Astar.pathString.get(i).equals("South")){
					if(x+95 == xCurrentMap && y+100 == yCurrentMap+1){
						blocked.add(new int[] {xCurrentMap, yCurrentMap});
						goToOtherAvailableDirection(xCurrentMap, yCurrentMap, x, y, blocked);
					} else {
						blocked.add(new int[] {xCurrentMap, yCurrentMap+1});
						goToMap(xCurrentMap, yCurrentMap, x, y, blocked);
					}
				}
				else if(Astar.pathString.get(i).equals("East")){
					if(x+95 == xCurrentMap+1 && y+100 == yCurrentMap){
						blocked.add(new int[] {xCurrentMap, yCurrentMap});
						goToOtherAvailableDirection(xCurrentMap, yCurrentMap, x, y, blocked);
					} else {
						blocked.add(new int[] {xCurrentMap+1, yCurrentMap});
						goToMap(xCurrentMap, yCurrentMap, x, y, blocked);
					}
				}
				else if(Astar.pathString.get(i).equals("West")){
					if(x+95 == xCurrentMap-1 && y+100 == yCurrentMap){
						blocked.add(new int[] {xCurrentMap, yCurrentMap});
						goToOtherAvailableDirection(xCurrentMap, yCurrentMap, x, y, blocked);
					} else {
						blocked.add(new int[] {xCurrentMap-1, yCurrentMap});
						goToMap(xCurrentMap, yCurrentMap, x, y, blocked);
					}
				}
			}
			else {
				mov.PerformChangement();
			}			
		}
    	while(!this.network.getInfo().isWaitForMov()){
    		Thread.sleep(500);
		}
		if(x == this.network.getInfo().getCoords()[0] && y == this.network.getInfo().getCoords()[1]){
//			this.getNetwork().append("Vous �tes arriv� !",false);
			return;
		}
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
	
	private void goToOtherAvailableDirection(int xCurrentMap, int yCurrentMap, int x, int y, List<int[]> blocked) throws Exception{
		MapMovement mov;
		if((mov = ChangeMap("North"))!=null){
			mov.PerformChangement();
			goToMap(xCurrentMap, --yCurrentMap, x, y, blocked);
		}
		else if((mov = ChangeMap("South"))!=null){
			mov.PerformChangement();
			goToMap(xCurrentMap, ++yCurrentMap, x, y, blocked);
		}
		else if((mov = ChangeMap("East"))!=null){
			mov.PerformChangement();
			goToMap(++xCurrentMap, yCurrentMap, x, y, blocked);
		}
		else if((mov = ChangeMap("West"))!=null){
			mov.PerformChangement();
			goToMap(--xCurrentMap, yCurrentMap, x, y, blocked);
		}
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
