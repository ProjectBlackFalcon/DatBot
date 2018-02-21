package ia.fight.map;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ia.fight.astar.AStarMap;
import ia.fight.astar.ExampleFactory;
import ia.fight.astar.ExampleNode;
import ia.fight.brain.Game;
import ia.fight.brain.Position;

public class Map {
	
	public static final int WALKABLE = 0;
	public static final int BLOCK = 1;
	public static final int HOVER = 2;
	public static final int BLUE_TEAM_ENTITY = 3;
	public static final int RED_TEAM_ENTITY = 4;
	
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
	
	public boolean isPositionAccessible(Position actualPosition, Position desiredPosition, int MP){
		int xDist = Math.abs(actualPosition.getX()-desiredPosition.getX());
		int yDist = Math.abs(actualPosition.getY()-desiredPosition.getY());
		
		Map map = this;

		if(xDist+yDist > MP || xDist+yDist == 0){
			return false;
		}
		
        AStarMap<ExampleNode> myMap = new AStarMap<ExampleNode>(33, 33, new ExampleFactory());
        for(int i = 0; i < 33; i++){
        	for(int j = 0; j < 33; j++){
        		myMap.setWalkable(i, j, map.isPositionWalkable(new Position(i, j)));
        	}
        }

        if(desiredPosition.getX() > 32) {
        	desiredPosition = new Position(32, desiredPosition.getY());
        }
        
        if(desiredPosition.getY() > 32) {
        	desiredPosition = new Position(desiredPosition.getY(), 32);
        }
        
        List<ExampleNode> path;
        
        try {
        	path = myMap.findPath(actualPosition.getX(),actualPosition.getY(),desiredPosition.getX(),desiredPosition.getY());
        }catch(Exception e) {
        	return false;
        }
        
    
        if(path.size() < 1 || path.size() > MP){
        	return false;
        }else{
        	return true;
        }
	}
	
	public ArrayList<Position> getShortestPath(String team, Position p1, Position p2){
		int xDist = Math.abs(p1.getX()-p2.getX());
		int yDist = Math.abs(p1.getY()-p2.getY());

		Map map = this;

		if(xDist+yDist == 0){
			return null;
		}
		
        AStarMap<ExampleNode> myMap = new AStarMap<ExampleNode>(33, 33, new ExampleFactory());
        for(int i = 0; i < 33; i++){
        	for(int j = 0; j < 33; j++){
        		myMap.setWalkable(i, j, map.isPositionWalkable(team, new Position(i, j)));
        	}
        }
        
        myMap.setWalkable(p2.getX(), p2.getY(), true);

        List<ExampleNode> path = myMap.findPath(p1.getX(),p1.getY(),p2.getX(),p2.getY());
        ArrayList<Position> positionPath = new ArrayList<>();
        
        for(int i = 0; i < path.size(); i++) {
        	positionPath.add(new Position(path.get(i).getxPosition(), path.get(i).getyPosition()));
        }
        
		return positionPath;
	}
	
	public boolean isPositionWalkable(Position position) {
		try {
			return this.blocks.get(position.getY()).get(position.getX()) == 0;
		}catch(java.lang.IndexOutOfBoundsException e) {
			return false;
		}
		
	}
	
	public boolean isPositionWalkable(String team, Position position) {
		int teamNumber = team.equals("red") ? 3 : 4;
		
		try {
			if(this.blocks.get(position.getY()-1).get(position.getX()) == teamNumber) {
				return false;
			}
		}catch(Exception e) {}
		
		
		try {
			if(this.blocks.get(position.getY()+1).get(position.getX()) == teamNumber) {
				return false;
			}
		}catch(Exception e) {}
		
		
		try {
			if(this.blocks.get(position.getY()).get(position.getX()-1) == teamNumber) {
				return false;
			}
		}catch(Exception e) {}
		
		
		try {
			if(this.blocks.get(position.getY()).get(position.getX()+1) == teamNumber) {
				return false;
			}
		}catch(Exception e) {}
		
		
		return this.blocks.get(position.getY()).get(position.getX()) == 0;
	}
	
	public void setBlocks(ArrayList<ArrayList<Integer>> blocks) {
		this.blocks = blocks;
	}
	
	public void printObstacles() {
		for(int i = 0; i < this.obstacles.size(); i++) {
			Game.println("["+this.obstacles.get(i)[0]+";"+this.obstacles.get(i)[1]+"] "+this.obstacles.get(i)[2]);
		}
	}
	
	JFrame frame;
	
	public void show() {
		if(frame != null) {
			if(!frame.isVisible()) {
				frame.setVisible(true);
			}
		}else {
			frame = new JFrame();
			frame.setUndecorated(false);
			frame.setSize(762, 762);
			frame.add(this.new Panel());
			frame.setVisible(true);
			frame.setLocation(1200, 200);
		}
		
	}
	
	public void hide() {
		frame.setVisible(false);
	}
	
	public class Panel extends JPanel{
		@Override
		public void paint(Graphics g) {
			for(int i = 0; i < 33; i++) {
				for(int j = 0; j < 33; j++) {
					switch(getBlockType(new Position(i, j))){
					case WALKABLE:
						g.setColor(Color.white);
						break;
					case BLOCK:
						g.setColor(Color.GRAY);
						break;
					case HOVER:
						g.setColor(Color.black);
						break;
					case BLUE_TEAM_ENTITY:
						g.setColor(Color.blue);
						break;
					case RED_TEAM_ENTITY:
						g.setColor(Color.red);
						break;
					}
					
					g.fillRect(i*20, j*20, (i+1)*20, (j+1)*20);
					g.setColor(Color.black);
					g.drawRect(i*20, j*20, (i+1)*20, (j+1)*20);
				}
			}
		}
	}
}
