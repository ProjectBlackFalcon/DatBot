package ia.map;

import java.util.List;

import ia.entities.entity.Entity;
import ia.entities.entity.MainEntity;
import ia.fight.astar.ExampleNode;
import utils.d2p.map.CellData;

public class MapIA {
	TransformedCell[][] cells;
	
	public MapIA() {
		
	}

    public List<Integer> getStartPosAvailable() {
        return startPosAvailable;
    }

    public void setStartPosAvailable(List<Integer> startPosAvailable) {
        this.startPosAvailable = startPosAvailable;
    }

    private List<Integer> startPosAvailable;
	
	public MapIA(List<CellData> cells) {
		this.cells = reshapeToIA(cells);
	}
	
	public void updateCells(List<Entity> entities) {
		
	}
	
	private static TransformedCell[][] reshapeToIA(List<CellData> cells){
		TransformedCell[][] transformedCells = new TransformedCell[34][34];
		
		for(int i = 0; i < transformedCells.length; i++) {
			for(int j = 0; j < transformedCells[0].length; j++) {
				transformedCells[i][j] = new TransformedCell(i, j, true, false, false);
			}
		}
		
		for(int i = 0; i < cells.size(); i++) {
			Position pos = reshapeToIA(i);
			transformedCells[pos.getX()][pos.getY()] = new TransformedCell(pos.getX(), pos.getY(), cells.get(i).isLos(), cells.get(i).isMov(), cells.get(i).isNonWalkableDuringFight());
		}
		
		
		return transformedCells;
	}
	
	public static TransformedCell[][] getCleanCells(List<CellData> cells, List<Entity> entities){
		
		TransformedCell[][] cleanCells = MapIA.reshapeToIA(cells);
		
		for(Entity entity : entities){
			cleanCells[entity.getPosition().getX()][entity.getPosition().getY()].setLos(false);
			cleanCells[entity.getPosition().getX()][entity.getPosition().getY()].setMov(false);
		}
				
		return cleanCells;
	}
	
	public static Position reshapeToIA(int startCellId) {
		int input_i = startCellId % 14;
		int input_j = startCellId / 14;
		
		int output_i, output_j;
		
		if(input_j % 2 == 0) {
			output_i = input_i + input_j / 2;
			output_j = 13 + (input_j / 2) - input_i;
		}else {
			output_i = 1 + input_i + input_j / 2;
			output_j = 13 + input_j / 2 - input_i;
		}

		if(output_i <= 33 & output_j <= 33) {
			return new Position(output_i,output_j);
		}else {
			return new Position(0,0);
		}
	}
	
	
	boolean hasLineOfSight(Position p1, Position p2) {
		return false;
	}
	
	List<ExampleNode> getPath(Position p1, Position p2){
		return null;
	}
	

	
	public static int reshapeToNetwork(int i, int j) {
		int result = 0;
		if (i + j / 2 == 0) {
			result = i + 13 * (i + j - 13) + (i + j - 14) / 2;
		}
		else {
			result = i + 13 * (i + j - 13) + (i + j - 13) / 2;
		}
		return result;
	}
	
	public static int reshapeToNetwork(Position pos) {
		int i = pos.getX();
		int j = pos.getY();
		int result = 0;
		if (i + j / 2 == 0) {
			result = i + 13 * (i + j - 13) + (i + j - 14) / 2;
		}
		else {
			result = i + 13 * (i + j - 13) + (i + j - 13) / 2;
		}
		return result;
	}
}
