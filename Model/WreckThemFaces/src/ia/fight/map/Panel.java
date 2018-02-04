package ia.fight.map;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
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
	ArrayList<ArrayList<String>> brainText;
	int brainTextIndex = 0;

	public final static int FIGHT_WIDTH = 662;
	public final static int FIGHT_HEIGHT = 662;
	public final static int BRAIN_WIDTH = 1000-FIGHT_WIDTH;
	public final static int BRAIN_HEIGHT = FIGHT_HEIGHT;
	
	Panel(Map map) {
		brainText = new ArrayList<>();
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
					obstacles.add(new int[] {p.getX(), p.getY(), 3, playingEntities.get(i).isNpc() ? 1 : 0});
				}else {
					obstacles.add(new int[] {p.getX(), p.getY(), 4, playingEntities.get(i).isNpc() ? 1 : 0});
				}
			}
		}
		
		mapObject.setObstacles(obstacles);
		map = mapObject.getBlocks();
	}
	
	
	
	public void updateBrainText(ArrayList<String> s) {
		System.out.println("ADDING TO BRAIN TEXT");
		brainText.add(s);
		brainTextIndex = brainText.size()-1;
	}
	
	@Override
	public void paint(Graphics g) {
		paintFight(g);
		paintBrain(g);
	}
	
	public void paintBrain(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(FIGHT_WIDTH, 0, BRAIN_WIDTH+FIGHT_WIDTH, BRAIN_HEIGHT);
		g.setColor(Color.red);
		g.fillRect(FIGHT_WIDTH, 0, 5, FIGHT_HEIGHT);
		g.fillRect(FIGHT_WIDTH+BRAIN_WIDTH-5, 0, 5, FIGHT_HEIGHT);
		g.fillRect(FIGHT_WIDTH, 0, FIGHT_WIDTH+BRAIN_WIDTH, 5);
		g.fillRect(FIGHT_WIDTH, BRAIN_HEIGHT-5, FIGHT_WIDTH+BRAIN_WIDTH, BRAIN_HEIGHT);
		g.setColor(Color.black);
		for(int i = 0; i < brainText.get(brainTextIndex).size(); i++) {
			g.drawString(brainText.get(brainTextIndex).get(i), FIGHT_WIDTH+10, 30+i*15);
		}
		
		g.drawString((brainTextIndex+1)+"/"+brainText.size(), FIGHT_WIDTH+10, FIGHT_HEIGHT-10);
		
		g.setColor(Color.CYAN);
		g.fillRect(FIGHT_WIDTH+BRAIN_WIDTH-6-50, BRAIN_HEIGHT-6-20, 50, 20);
		g.fillRect(FIGHT_WIDTH+BRAIN_WIDTH-7-100, BRAIN_HEIGHT-6-20, 50, 20);
		g.setColor(Color.BLUE);
		g.drawLine(FIGHT_WIDTH+BRAIN_WIDTH-6-25, BRAIN_HEIGHT-6-10, FIGHT_WIDTH+BRAIN_WIDTH-6-25-5, BRAIN_HEIGHT-6-10-5);
		g.drawLine(FIGHT_WIDTH+BRAIN_WIDTH-6-25, BRAIN_HEIGHT-6-10, FIGHT_WIDTH+BRAIN_WIDTH-6-25-5, BRAIN_HEIGHT-6-5);
		
		g.drawLine(FIGHT_WIDTH+BRAIN_WIDTH-6-75-5, BRAIN_HEIGHT-6-10, FIGHT_WIDTH+BRAIN_WIDTH-6-75, BRAIN_HEIGHT-6-10-5);
		g.drawLine(FIGHT_WIDTH+BRAIN_WIDTH-6-75-5, BRAIN_HEIGHT-6-10, FIGHT_WIDTH+BRAIN_WIDTH-6-75, BRAIN_HEIGHT-6-5);
	}
	
	public void paintFight(Graphics g) {
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
				if(mapObject.isPositionAccessible(new Position(selectedCase[0], selectedCase[1]), new Position(i, j), 6)) {
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
