package ia.fight.map;

import java.util.ArrayList;

import ia.fight.brain.Position;

public class Map {
	ArrayList<int[]> obstacles;
	ArrayList<ArrayList<Integer>> blocks;
	ArrayList<int[]> jsonBlocks;
	
	public Map(ArrayList<int[]> jsonBlocks) {
		blocks = new ArrayList<>();
		int[][] mapPattern;

		obstacles = new ArrayList<>();

		for(int i = 0; i < jsonBlocks.size(); i++) {
			if(jsonBlocks.get(i)[2] != 0) {
				obstacles.add(jsonBlocks.get(i));
			}
		}
		
		mapPattern = new int[33][2];

		// map width
		int maxSize = 33;
		
		// creating map
		for (int line = 0; line < mapPattern.length; line++){
		    ArrayList<Integer> newLine = new ArrayList<>();
		
		    for (int column = 0; column < mapPattern[line][0]; column++){
		        newLine.add(0);
		    }
		
		    for (int column = mapPattern[line][0]; column < (mapPattern[line][0] + mapPattern[line][1]); column++){
		        newLine.add(0);
		    }
		
		    for (int column = mapPattern[line][0] + mapPattern[line][1]; column < maxSize; column++){
		        newLine.add(0);
		    }
		
		    blocks.add(newLine);
		}

		for (int i = 0; i < obstacles.size(); i++){
			
			int x = obstacles.get(i)[0];
		    int y = obstacles.get(i)[1];
		    int type = obstacles.get(i)[2];
		    blocks.get(x).set(y, type);
		}

		this.jsonBlocks = jsonBlocks;
	}
	
	public int getBlockType(Position p) {
		for(int i = 0; i < this.obstacles.size(); i++) {
			if(obstacles.get(i)[0] == p.getX() && obstacles.get(i)[1] == p.getY()) {
				return obstacles.get(i)[2];
			}
		}
		
		return 0;
	}
	
	public ArrayList<ArrayList<Integer>> getNewBlockArray() {
		blocks = new ArrayList<>();
		int[][] mapPattern = new int[33][2];
		int maxSize = 33;

		for (int line = 0; line < mapPattern.length; line++){
		    ArrayList<Integer> newLine = new ArrayList<>();
		
		    for (int column = 0; column < mapPattern[line][0]; column++){
		        newLine.add(0);
		    }
		
		    for (int column = mapPattern[line][0]; column < (mapPattern[line][0] + mapPattern[line][1]); column++){
		        newLine.add(0);
		    }
		
		    for (int column = mapPattern[line][0] + mapPattern[line][1]; column < maxSize; column++){
		        newLine.add(0);
		    }
		
		    blocks.add(newLine);
		}
		
		for (int i = 0; i < obstacles.size(); i++){
			
		    int y = obstacles.get(i)[0];
		    int x = obstacles.get(i)[1];
		    int type = obstacles.get(i)[2];
		    blocks.get(x).set(y, type);
		}
		
		return blocks;
	}
	
	public ArrayList<int[]> getJSONBlocks() {
		return jsonBlocks;
	}

	public void setJSONBlocks(ArrayList<int[]> jsonBlocks) {
		this.jsonBlocks = jsonBlocks;
	}

	public ArrayList<int[]> getObstacles() {
		return obstacles;
	}
	
	public void setObstacles(ArrayList<int[]> obstacles) {
		this.blocks = getNewBlockArray();
		this.obstacles = obstacles;
		
		for (int i = 0; i < obstacles.size(); i++){
		    int y = obstacles.get(i)[0];
		    int x = obstacles.get(i)[1];
		    int type = obstacles.get(i)[2];
		    blocks.get(x).set(y, type);
		}
	}
	
	public int[][] getBlocks() {
		int[][] blocks = new int[this.blocks.size()][];
		
		for(int i = 0; i < this.blocks.size(); i++) {
			int[] tempBlocks = new int[this.blocks.get(i).size()];
			for(int j = 0; j < tempBlocks.length; j++) {
				tempBlocks[j] = this.blocks.get(i).get(j);
			}
			
			blocks[i] = tempBlocks;
		}
		
		return blocks;
	}
	
	public ArrayList<ArrayList<Integer>> getBlocksArrayList() {
		return this.blocks;
	}
	
	public ArrayList<int[]> getWalkablePositions() {
		ArrayList<int[]> walkablePositions = new ArrayList<>();
		
		for(int i = 0; i < this.blocks.size(); i++) {
			for(int j = 0; j < this.blocks.get(i).size(); j++) {
				if(this.blocks.get(i).get(j) == 0) {
					walkablePositions.add(new int[] {i, j});
				}
			}
		}

		return walkablePositions;
	}
	
	public ArrayList<int[]> getWalkablePositionsFrom(Position position, int movementPoints) {
		ArrayList<int[]> walkablePositions = new ArrayList<>();
		
		for(int i = 0; i < this.blocks.size(); i++) {
			for(int j = 0; j < this.blocks.get(i).size(); j++) {
				if(this.blocks.get(i).get(j) == 0 && Position.distance(position, new Position(i, j)) <= movementPoints) {
					walkablePositions.add(new int[] {i, j});
				}
			}
		}

		return walkablePositions;
	}
	
	public boolean isPositionWalkable(Position position) {
		return this.blocks.get(position.getX()).get(position.getY()) == 0;
	}
	
	public void setBlocks(ArrayList<ArrayList<Integer>> blocks) {
		this.blocks = blocks;
	}
	
	public void printObstacles() {
		for(int i = 0; i < this.obstacles.size(); i++) {
			System.out.println("["+this.obstacles.get(i)[0]+";"+this.obstacles.get(i)[1]+"] "+this.obstacles.get(i)[2]);
		}
	}
}
