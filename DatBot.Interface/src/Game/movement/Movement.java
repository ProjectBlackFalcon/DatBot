package Game.movement;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.SwingUtilities;

import Game.Info;
import Game.map.Map;
import Game.map.MapMovement;
import protocol.network.Network;
import utils.Astar;
import utils.JSON;

public class Movement{
	
	private Network network;
	
	public Movement(Network network){
		this.network = network;
	}
	
	public CellMovement MoveToCell(int cellId) throws Exception{
		if(Map.getCells().get(cellId).Mov){
			CellMovement mov = new CellMovement(new Pathfinder().findPath(this.network.getInfo().getCellId(),cellId), this.getNetwork());
			return mov;
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
                neighbourId = (int) Map.TopNeighbourId;
                num2 = 64;
                break;
            case "South":
                neighbourId = (int) Map.BottomNeighbourId;
                num2 = 4;
                break;
            case "East":
                neighbourId = (int) Map.RightNeighbourId;
                num2 = 1;
                break;
            case "West":
                neighbourId = (int) Map.LeftNeighbourId;
                num2 = 16;
                break;
        }
        if (num2 == -1 || neighbourId < 0) return null;

        List<Integer> list = new ArrayList<Integer>();
        for (int i = 0; i < Map.Cells.size() - 1; i++){
            if ((Map.Cells.get(i).MapChangeData & num2) > 0 && Map.NothingOnCell(i) && noObstacle(i)){
            	list.add(i);
            }
        }
        if(list.size() == 0) return null; // Can't go in this direction (Obstacles)
        Random r = new Random();
        int randomCellId = list.get(r.nextInt(list.size()));
		this.getNetwork().append("D�placement...",false);	
		this.getNetwork().append("Direction : " + direction,false);
        CellMovement move = MoveToCell(randomCellId);
        return new MapMovement(move, neighbourId,this.getNetwork());
	}
	
	public MapMovement ChangeMap(int cellId, String direction) throws Exception{
        int neighbourId = -1;
        int num2 = -1;
        switch (direction)
        {
            case "n":
                neighbourId = (int) Map.TopNeighbourId;
                num2 = 64;
                break;
            case "s":
                neighbourId = (int) Map.BottomNeighbourId;
                num2 = 4;
                break;
            case "e":
                neighbourId = (int) Map.RightNeighbourId;
                num2 = 1;
                break;
            case "w":
                neighbourId = (int) Map.LeftNeighbourId;
                num2 = 16;
                break;
        }
        if (num2 == -1 || neighbourId < 0) return null;
        System.out.println((Map.Cells.get(cellId).MapChangeData & num2) > 0);
        System.out.println(Map.NothingOnCell(cellId));
        System.out.println(noObstacle(cellId));
        if (Map.NothingOnCell(cellId) && noObstacle(cellId)){  //(Map.Cells.get(cellId).MapChangeData & num2) > 0 && 
        	this.getNetwork().append("D�placement...",false);	
        	this.getNetwork().append("Direction : " + direction,false);
            CellMovement move = MoveToCell(cellId);
            return new MapMovement(move, neighbourId,this.getNetwork());
        } else if (this.network.getInfo().getCellId() == cellId){
            return new MapMovement(null, neighbourId,this.getNetwork());
        } else {
        	return null;
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
	
	
// TRIED TO GET THE WAY BUT NOT WORKING
	public void goToMap(int xStart, int yStart, int x, int y, List<int[]> blocked) throws Exception{
    	while(!this.network.getInfo().isWaitForMov()){
    		Thread.sleep(500);
		}
		if(x == this.network.getInfo().getCoords()[0] && y == this.network.getInfo().getCoords()[1]){
			this.getNetwork().append("Vous �tes arriv� !",false);
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
//		System.out.println("Size blocked : " + blocked.size());
//		System.out.println("Map : " + (xCurrentMap) + ";" + (yCurrentMap));
//		
//		for (int[] is : blocked) {
//			System.out.println("2	" + (is[0]) + ";" + (is[1]));
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
//			System.out.println("2	" + (is[0]-95) + ";" + (is[1]-100));
//		}
		        
        for (int i = 0; i < Astar.pathString.size() ; i++) {
        	while(!this.network.getInfo().isWaitForMov()){
        		Thread.sleep(500);
    		}
    		if(x == this.network.getInfo().getCoords()[0] && y == this.network.getInfo().getCoords()[1]){
    			this.getNetwork().append("Vous �tes arriv� !",false);
    			return;
    		}
    		xCurrentMap = this.network.getInfo().getCoords()[0] + 95; 
    		yCurrentMap = this.network.getInfo().getCoords()[1] + 100;
			MapMovement mov = ChangeMap(Astar.pathString.get(i));
			if (mov == null) {
				this.getNetwork().append("D�placement impossible ! Un obstacle bloque le chemin !",false);
				this.getNetwork().append("Cr�ation d'un nouveau chemin...",false);
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
			this.getNetwork().append("Vous �tes arriv� !",false);
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
