package ia.fight.map;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import ia.fight.astar.AStarMap;
import ia.fight.astar.ExampleFactory;
import ia.fight.astar.ExampleNode;
import ia.fight.brain.Game;
import ia.fight.brain.PlayingEntity;
import ia.fight.brain.Position;

public class Panel extends JPanel{

	int[][] map;
	
	int newLine;
	int currentLineY;
	int currentColumnX;
	int updatingLineY;
	int updatingColumnX;
	String mapAppend = "";
	boolean targetFixed = false;
	int targetLineY;
	int targetColumnX;
	int[] buffer;
	int bufferCursor;
	int[] selectedCell = new int[2];
	int[] clickedCell = new int[2];
	int[] clickedRightCell = new int[2];
	
	ArrayList<Cell> cells;
	ArrayList<Position> selectedStartPositions;
	ArrayList<Position> challengers;
	ArrayList<Position> defenders;
	boolean showStartingPositions = true;
	
	ArrayList<int[]> trail;
	ArrayList<int[]> obstacles;
	AStarMap<ExampleNode> myMap;
	
	Map mapObject;
	ArrayList<ArrayList<String>> brainText;
	int brainTextIndex = 0;

	public final static int OFFSETY = 0;
	public final static int FIGHT_WIDTH = 660;
	public final static int FIGHT_HEIGHT = 660;
	public final static int BRAIN_WIDTH = 1000-FIGHT_WIDTH;
	public final static int BRAIN_HEIGHT = FIGHT_HEIGHT;
	public final static int MENU_WIDTH = 1000;
	public final static int MENU_HEIGHT = 100;
	
	Panel(Map map) {
		
		myMap = new AStarMap<ExampleNode>(33, 33, new ExampleFactory());
        for(int i = 0; i < 33; i++){
        	for(int j = 0; j < 33; j++){
        		myMap.setWalkable(i, j, map.isPositionWalkable(new Position(i, j)));
        	}
        }
		
        selectedStartPositions = new ArrayList<>();
        challengers = new ArrayList<>();
        defenders = new ArrayList<>();
		brainText = new ArrayList<>();
		trail = new ArrayList<>();
		cells = new ArrayList<>();
		mapObject = map;
		this.obstacles = map.getObstacles();
		this.map = map.getBlocks();
		selectedCell[0] = 16;
		selectedCell[1] = 17;
		clickedCell[0] = -1;
		clickedCell[1] = -1;
		clickedRightCell[0] = -1;
		clickedRightCell[1] = -2;
		
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if(e.getX()/20 < 33) {
					selectedCell[0] = e.getX()/20;
				}
				if(e.getY()/20 < 33) {
					selectedCell[1] = e.getY()/20;
				}
	
				repaint();
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {}
		});		
		
		addMouseListener(new MouseListener() {
			
			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				
				if(e.getButton() == MouseEvent.BUTTON1) {
					if(e.getX()/20 < 33) {
						trail.clear();
						clickedCell[0] = e.getX()/20;
					}
					if(e.getY()/20 < 33) {
						trail.clear();
						clickedCell[1] = e.getY()/20;
					}
				}else if(e.getButton() == MouseEvent.BUTTON3) {
					if(e.getX()/20 < 33) {
						trail.clear();
						clickedRightCell[0] = e.getX()/20;
					}
					if(e.getY()/20 < 33) {
						trail.clear();
						clickedRightCell[1] = e.getY()/20;
					}
				}
				
				if(clickedCell[0] != clickedRightCell[0] || clickedCell[1] != clickedRightCell[1]) {
					if(clickedCell[0] >= 0 && clickedCell[0] < 33 &&
							clickedCell[1] >= 0 && clickedCell[1] < 33 &&
							clickedRightCell[0] >= 0 && clickedRightCell[0] < 33 &&
							clickedRightCell[1] >= 0 && clickedRightCell[1] < 33) {
						Game.println("Creating path");
						List<ExampleNode> path;
				        path = myMap.findPath(clickedCell[0],clickedCell[1],clickedRightCell[0],clickedRightCell[1]);
				        for(int i = 0; i < path.size(); i++) {
				        	trail.add(new int[] {path.get(i).getxPosition(), path.get(i).getyPosition()});
				        }
					}
				}
				
				repaint();
				
				if(e.getX() > FIGHT_WIDTH+BRAIN_WIDTH-6-50 && e.getX() < FIGHT_WIDTH+BRAIN_WIDTH-6 && e.getY() > BRAIN_HEIGHT-6-20 && e.getY() < BRAIN_HEIGHT-6) {
					
					if(brainTextIndex < brainText.size()-1) {
						brainTextIndex++;
						repaint();
					}
				}
				
				if(e.getX() > FIGHT_WIDTH+BRAIN_WIDTH-6-100 && e.getX() < FIGHT_WIDTH+BRAIN_WIDTH-6-50 && e.getY() > BRAIN_HEIGHT-6-20 && e.getY() < BRAIN_HEIGHT-6) {
					if(brainTextIndex > 0) {
						brainTextIndex--;
						repaint();
					}
				}
			}
		});
		
		setFocusable(true);
	}
	
	void update(ArrayList<PlayingEntity> playingEntities){
		try {
			for(int i = obstacles.size()-1; i >= 0; i--) {
				if(obstacles.get(i)[2] == 3 || obstacles.get(i)[2] == 4) {
					mapObject.getBlocks()[obstacles.get(i)[0]][obstacles.get(i)[1]] = 0;
					obstacles.remove(i);
				}
			}
			
			if(playingEntities != null) {
				for(int i = 0; i < playingEntities.size(); i++) {
					Position p = playingEntities.get(i).getPosition();
					if(playingEntities.get(i).getTeam().equals("red")) {
						obstacles.add(new int[] {p.getX(), p.getY(), 3, 0});
					}else {
						obstacles.add(new int[] {p.getX(), p.getY(), 4, 0});
					}
				}
			}
			
			myMap = new AStarMap<ExampleNode>(33, 33, new ExampleFactory());
	        for(int i = 0; i < 33; i++){
	        	for(int j = 0; j < 33; j++){
	        		myMap.setWalkable(i, j, mapObject.isPositionWalkable(new Position(i, j)));
	        	}
	        }
	        
			mapObject.setObstacles(obstacles);
			map = mapObject.getBlocks();
		}catch(Exception e) {e.printStackTrace();}

	}
	
	public void update(ArrayList<Position> challengers, ArrayList<Position> defenders) {
		this.challengers = challengers;
		this.defenders = defenders;
		repaint();
	}
	
	public void updateStartingPositions(ArrayList<Position> positions) {
		this.selectedStartPositions = positions;
		repaint();
	}
	
	public void updateBrainText(ArrayList<String> s) {
		brainText.add(s);
		brainTextIndex = brainText.size()-1;
	}
	
	@Override
	public void paint(Graphics g) {
		paintFight(g);
		paintBrain(g);
		paintMenu(g);
	}
	
	public void showCell(Position pos, String txt, Color color) {
		boolean found = false;
		for(int i = 0; i < cells.size(); i++) {
			if(cells.get(i).pos.deepEquals(pos)) {
				cells.get(i).txt = txt;
				cells.get(i).color = color;
				found = true;
			}
		}
		
		if(!found)
			cells.add(new Cell(pos, txt, color));
		
		repaint();
	}
	
	public void clearCells() {
		cells.clear();
		repaint();
	}
	
	public void hideCell(Position pos) {
		for(int i = 0; i < cells.size(); i++) {
			if(cells.get(i).pos.deepEquals(pos)) {
				cells.remove(i);
			}
		}
		repaint();
	}
	
	public void showStartingPositions() {
		this.showStartingPositions = true;
		repaint();
	}
	
	public void hideStartingPositions() {
		this.showStartingPositions = false;
		repaint();
	}
	
	public void paintMenu(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillRect(10, OFFSETY+BRAIN_HEIGHT-30, 50, 20);
		g.fillRect(70, OFFSETY+BRAIN_HEIGHT-30, 50, 20);
		g.setColor(Color.WHITE);
		g.drawRect(10, OFFSETY+BRAIN_HEIGHT-30, 50, 20);
		g.drawRect(70, OFFSETY+BRAIN_HEIGHT-30, 50, 20);
	}
	
	public void paintBrain(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(FIGHT_WIDTH, OFFSETY+0, BRAIN_WIDTH+FIGHT_WIDTH, BRAIN_HEIGHT);
		g.setColor(Color.BLACK);
		g.fillRect(FIGHT_WIDTH, OFFSETY+0, 5, FIGHT_HEIGHT);
		g.fillRect(FIGHT_WIDTH+BRAIN_WIDTH-5, OFFSETY+0, 5, FIGHT_HEIGHT);
		g.fillRect(FIGHT_WIDTH, OFFSETY+0, FIGHT_WIDTH+BRAIN_WIDTH, 5);
		g.fillRect(FIGHT_WIDTH, OFFSETY+BRAIN_HEIGHT-5, FIGHT_WIDTH+BRAIN_WIDTH, BRAIN_HEIGHT);
		g.setColor(Color.black);
		for(int i = 0; i < brainText.get(brainTextIndex).size(); i++) {
			g.drawString(brainText.get(brainTextIndex).get(i), FIGHT_WIDTH+10, OFFSETY+30+i*15);
		}
		
		g.drawString((brainTextIndex+1)+"/"+brainText.size(), FIGHT_WIDTH+10, OFFSETY+FIGHT_HEIGHT-10);
		
		g.setColor(Color.BLACK);
		g.fillRect(FIGHT_WIDTH+BRAIN_WIDTH-6-50, OFFSETY+BRAIN_HEIGHT-6-20, 50, 20);
		g.fillRect(FIGHT_WIDTH+BRAIN_WIDTH-7-100, OFFSETY+BRAIN_HEIGHT-6-20, 50, 20);
		g.setColor(Color.WHITE);
		g.drawLine(FIGHT_WIDTH+BRAIN_WIDTH-6-25, OFFSETY+BRAIN_HEIGHT-6-10, FIGHT_WIDTH+BRAIN_WIDTH-6-25-5, OFFSETY+BRAIN_HEIGHT-6-10-5);
		g.drawLine(FIGHT_WIDTH+BRAIN_WIDTH-6-25, OFFSETY+BRAIN_HEIGHT-6-10, FIGHT_WIDTH+BRAIN_WIDTH-6-25-5, OFFSETY+BRAIN_HEIGHT-6-5);
		
		g.drawLine(FIGHT_WIDTH+BRAIN_WIDTH-6-75-5, OFFSETY+BRAIN_HEIGHT-6-10, FIGHT_WIDTH+BRAIN_WIDTH-6-75, OFFSETY+BRAIN_HEIGHT-6-10-5);
		g.drawLine(FIGHT_WIDTH+BRAIN_WIDTH-6-75-5, OFFSETY+BRAIN_HEIGHT-6-10, FIGHT_WIDTH+BRAIN_WIDTH-6-75, OFFSETY+BRAIN_HEIGHT-6-5);
	}
	
	public void paintFight(Graphics g) {
		for(int i = 0; i < 33; i++) {
			for(int j = 0; j < 33; j++) {
				if(LineOfSight.visibility(new Position(selectedCell[0], selectedCell[1]), new Position(i, j), map))
					g.setColor(Color.white);
				else
					g.setColor(new Color(200, 255, 255));
				
				g.fillRect(i*20, OFFSETY+j*20, 20, 20);
			}	
		}
		
		for(int i = 0; i < obstacles.size(); i++) {
			if(obstacles.get(i)[2] == 1) {
				g.setColor(Color.gray);
			}else if(obstacles.get(i)[2] == 2){
				g.setColor(Color.black);
			}else if(obstacles.get(i)[2] == 3){
				g.setColor(Color.red);
			}else if(obstacles.get(i)[2] == 4){
				g.setColor(Color.blue);
			}
			g.fillRect(obstacles.get(i)[0]*20, OFFSETY+obstacles.get(i)[1]*20, 20, 20);
			
			if(obstacles.get(i)[2] == 3 || obstacles.get(i)[2] == 4){
				g.setColor(Color.white);
				/*
				if(obstacles.get(i)[3] == 0) {
					g.drawString("P", 6+obstacles.get(i)[0]*20, 15+1+obstacles.get(i)[1]*20);
				}else{
					g.drawString("N", 6+obstacles.get(i)[0]*20, 15+1+obstacles.get(i)[1]*20);
				}*/
			}
		}
		
		g.setColor(Color.green);
		g.fillRect(selectedCell[0]*20, OFFSETY+selectedCell[1]*20, 20, 20);
		
		g.setColor(Color.CYAN);
		g.fillRect(clickedCell[0]*20, OFFSETY+clickedCell[1]*20, 20, 20);
		
		g.setColor(Color.MAGENTA);
		g.fillRect(clickedRightCell[0]*20, OFFSETY+clickedRightCell[1]*20, 20, 20);
		
		g.setColor(new Color(0, 0, 0, 150));
		for(int i = 0; i < trail.size(); i++) {
			g.fillRect(trail.get(i)[0]*20+5, OFFSETY+trail.get(i)[1]*20+5, 10, 10);
		}
		/*
		for(int i = 0; i < 33; i++) {
			for(int j = 0; j < 33; j++) {
				if(mapObject.isPositionAccessible(new Position(selectedCell[0], selectedCell[1]), new Position(i, j), 6)) {
					g.setColor(Color.green);
				}else {
					g.setColor(Color.black);
				}
				g.drawRect(4+i*20, OFFSETY+4+j*20, 11, 11);
			}
		}*/
		g.setColor(Color.black);
		
		for(int i = 0; i < 33; i++) {
			for(int j = 0; j < 33; j++) {
				g.drawRect(i*20, OFFSETY+j*20, 20, 20);
			}	
		}
		
		if(this.showStartingPositions) {
			g.setColor(new Color(255,0,0,50));
			for(int i = 0; i < challengers.size(); i++) {
				g.setColor(new Color(255,0,0,50));
				for(int j = 0; j < selectedStartPositions.size(); j++) {
					if(challengers.get(i).getX() == selectedStartPositions.get(j).getX() && challengers.get(i).getY() == selectedStartPositions.get(j).getY()) {
						g.setColor(new Color(255, 0, 0, 255));
					}
				}
				g.fillRect(challengers.get(i).getX()*20+1, challengers.get(i).getY()*20+1, 19, 19);
			}
			
			for(int i = 0; i < defenders.size(); i++) {
				g.setColor(new Color(0,0,255,100));
				for(int j = 0; j < selectedStartPositions.size(); j++) {
					if(defenders.get(i).getX() == selectedStartPositions.get(j).getX() && defenders.get(i).getY() == selectedStartPositions.get(j).getY()) {
						g.setColor(new Color(0, 0, 255, 255));
					}
				}
				g.fillRect(defenders.get(i).getX()*20+1, defenders.get(i).getY()*20+1, 19, 19);
			}
		}
		
		for(int i = 0; i < cells.size(); i++) {
			g.setColor(cells.get(i).color);
			g.fillRect(cells.get(i).pos.getX()*20+1, cells.get(i).pos.getY()*20+1, 19, 19);
			g.setColor(Color.black);
			g.drawString(cells.get(i).txt, cells.get(i).pos.getX()*20+3, cells.get(i).pos.getY()*20+12);
		}
	}
	
	public class Cell{
		
		Position pos;
		String txt;
		Color color;
		
		public Cell(Position pos, String txt, Color color) {
			this.pos = pos;
			this.txt = txt;
			this.color = color;
		}
	}
}
