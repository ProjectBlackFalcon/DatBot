package ia.map;

import java.util.List;

import ia.entities.entity.Entity;
import ia.fight.astar.ExampleNode;
import utils.d2p.map.CellData;

public class MapIA {
	TransformedCell[][] cells;
	
	public MapIA(List<CellData> cells) {
		this.cells = reshapeToIA(cells);
	}
	
	public void updateCells(List<Entity> entities) {
		
	}
	
	//TODO Do the stuff
	public TransformedCell[][] reshapeToIA(List<CellData> cells){
		TransformedCell[][] transformedCells = new TransformedCell[33][33];
		for(int i = 0; i < cells.size(); i++) {
			int[] pos = reshapeToIA(i);
			transformedCells[pos[0]][pos[1]] = new TransformedCell(pos[0], pos[1], cells.get(i).isLos(), cells.get(i).isMov());
		}
		
		return transformedCells;
	}
	
	public static int[] reshapeToIA(int startCellId) {
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

		if(output_i < 33 & output_j < 33) {
			return new int[] {output_i, output_j};
		}else {
			return new int[] {0, 0};
		}
	}
	
	boolean hasLineOfSight(Position p1, Position p2) {
		return false;
	}
	
	List<ExampleNode> getPath(Position p1, Position p2){
		return null;
	}
	
	public class TransformedCell{
		Position pos;
		private boolean Los;
		private boolean Mov;
		
		public TransformedCell(int x, int y, boolean Los, boolean Mov) {
			pos = new Position(x, y);
			this.Los = Los;
			this.Mov = Mov;
		}
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
}
