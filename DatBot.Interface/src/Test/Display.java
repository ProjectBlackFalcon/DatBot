package Test;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import ia.entities.entity.Entity;
import ia.entities.entity.MainEntity;
import ia.map.MapIA;
import ia.map.Position;
import ia.map.TransformedCell;
import ia.utils.UtilsMath;
import utils.GameData;
import utils.d2p.MapManager;
import utils.d2p.map.CellData;

public class Display {

	
	static JFrame frame;
	
	public static void main(String[] args) throws IOException{
		new MapManager(GameData.getPathDatBot() + "\\DatBot.Interface\\utils\\maps");
//		Display.displayMap(MapIA.reshapeToIA(MapManager.FromId(153880322).getCells()));
		List<Entity> entities = new ArrayList<>();
		Display.displayPath(MapManager.FromId(191102980).getCells(), entities);
	}

	public static void displayMap(TransformedCell[][] cells){
		frame = new JFrame();
		frame.add(new Panel(cells));
		frame.setSize(685, 685);
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setLocation(50, 50);
	}
	
	public static void displayMap(List<CellData> cells, List<Entity> entities){
		frame = new JFrame();
		frame.add(new Panel(cells, entities));
		frame.setSize(685, 685);
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setLocation(50, 50);
	}
	
	public static void displayPath(List<CellData> cells, List<Entity> entities){
		frame = new JFrame();
		frame.add(new Panel(cells, entities, true));
		frame.setSize(685, 685);
		frame.setUndecorated(true);
		frame.setVisible(true);
		frame.setLocation(50, 50);
	}
	
	public static class Panel extends JPanel{
		
		TransformedCell[][] cells;
		List<Position> path;
		Position p1;
		Position p2;
		boolean diag = false;
		
		public Panel(TransformedCell[][] cells) {
			this.cells = cells;
		}
		
		public Panel(List<CellData> cells, List<Entity> entities) {
			this.cells = MapIA.getCleanCells(cells, entities);
		}
		
		public Panel(List<CellData> cells, List<Entity> entities, boolean drawPath) {
			this.cells = MapIA.getCleanCells(cells, entities);
			
			System.out.println("Drawing path");
			
			this.setFocusable(true);
			
			this.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
					System.out.println(e.getButton());
					if(e.getButton() == 1){
						p1 = new Position(e.getX()/20, e.getY()/20);
					}else if(e.getButton() == 3){
						p2 = new Position(e.getX()/20, e.getY()/20);
					}
					
					if(e.getButton() == 2){
						diag = !diag;
					}
					
					if(p1 != null && p2 != null){
						path = UtilsMath.getPath(MapIA.getCleanCells(cells, entities), p1, p2, diag);
						System.out.println("Creating path between " + p1+" "+p2);
					}
					
					repaint();
					
					System.out.println("Clicked");
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
				}
			});
		}
		
		@Override
		public void paint(Graphics g) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 680, 680);
			g.setColor(Color.LIGHT_GRAY);
			for(int i = 0; i < 34; i++){
				for(int j = 0; j < 34; j++){
					g.setColor(Color.CYAN);
					g.drawRect(i*20, j*20, 20, 20);
					if((this.cells[i][j].isLos() && !this.cells[i][j].isMov()) || this.cells[i][j].isNonWalkableDuringFight()){
						g.setColor(Color.BLACK);
						g.fillRect(i*20+1, j*20+1, 19, 19);
					}
					
					if(!this.cells[i][j].isMov() && !this.cells[i][j].isLos() && !this.cells[i][j].isNonWalkableDuringFight()){
						g.setColor(Color.GRAY);
						g.fillRect(i*20+1, j*20+1, 19, 19);
					}
					
					if(this.cells[i][j].isMov() && this.cells[i][j].isLos() && !this.cells[i][j].isNonWalkableDuringFight()){
						g.setColor(Color.WHITE);
						g.fillRect(i*20+1, j*20+1, 19, 19);
					}
					
				}
			}
			
			if(this.path != null){
				for(int i = 0; i < path.size(); i++){
					g.setColor(new Color(255, 200, 200));
					g.fillRect(path.get(i).getX()*20+1, path.get(i).getY()*20+1, 19, 19);
				}
				
				
			}
			
			if(p1 != null){
				g.setColor(Color.red);
				g.fillRect(p1.getX()*20+1, p1.getY()*20+1, 19, 19);
			}
			
			if(p2 != null){
				g.setColor(Color.blue);
				g.fillRect(p2.getX()*20+1, p2.getY()*20+1, 19, 19);
			}
		}
	}
}
