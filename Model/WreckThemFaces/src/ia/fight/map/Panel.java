package ia.fight.map;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JPanel;

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
	int[] selectedCase = new int[2];
	ArrayList<int[]> obstacles;
	Map mapObject;

	
	Panel(Map map) {
		mapObject = map;
		this.obstacles = map.getObstacles();
		this.map = map.getBlocks();
		selectedCase[0] = 16;
		selectedCase[1] = 17;
		
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				if(e.getX()/20 < 33) {
					selectedCase[0] = e.getX()/20;
				}
				if(e.getY()/20 < 33) {
					selectedCase[1] = e.getY()/20;
				}
				repaint();
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {}
		});		
		setFocusable(true);
	}
	
	void update(ArrayList<PlayingEntity> playingEntities){
		for(int i = obstacles.size()-1; i >= 0; i--) {
			if(obstacles.get(i)[2] == 3 || obstacles.get(i)[2] == 4) {
				mapObject.getBlocks()[obstacles.get(i)[0]][obstacles.get(i)[1]] = 0;
				obstacles.remove(i);
			}
		}
		
		for(int i = 0; i < playingEntities.size(); i++) {
			Position p = playingEntities.get(i).getPosition();
			if(playingEntities.get(i).getTeam().equals("red")) {
				obstacles.add(new int[] {p.getX(), p.getY(), 3, playingEntities.get(i).isNpc() ? 1 : 0});
			}else {
				obstacles.add(new int[] {p.getX(), p.getY(), 4, playingEntities.get(i).isNpc() ? 1 : 0});
			}
		}
		
		mapObject.setObstacles(obstacles);
		map = mapObject.getBlocks();
	}
	
	@Override
	public void paint(Graphics g) {
		
		for(int i = 0; i < 33; i++) {
			for(int j = 0; j < 33; j++) {
				if(LineOfSight.visibility(new Position(selectedCase[0], selectedCase[1]), new Position(i, j), map))
					g.setColor(Color.white);
				else
					g.setColor(new Color(200, 255, 255));
				
				g.fillRect(1+i*20, 1+j*20, 20, 20);
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
			g.fillRect(1+obstacles.get(i)[0]*20, 1+obstacles.get(i)[1]*20, 20, 20);
			
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
		g.fillRect(1+selectedCase[0]*20, 1+selectedCase[1]*20, 20, 20);
		
		for(int i = 0; i < 33; i++) {
			for(int j = 0; j < 33; j++) {
				if(Game.map.isPositionAccessible(new Position(selectedCase[0], selectedCase[1]), new Position(i, j), 6)) {
					g.setColor(Color.green);
				}else {
					g.setColor(Color.black);
				}
				g.drawRect(5+i*20, 5+j*20, 11, 11);
			}
		}
		g.setColor(Color.black);
		
		for(int i = 0; i < 33; i++) {
			for(int j = 0; j < 33; j++) {
				g.drawRect(1+i*20, 1+j*20, 20, 20);
			}	
		}
	}
}
