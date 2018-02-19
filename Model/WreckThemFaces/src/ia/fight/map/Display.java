package ia.fight.map;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Display {
	
	int[][] map;

	public static void show(int[][] map) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setSize(660, 660);
		Display display = new Display(map);
		frame.add(display.new Panel());
		frame.setVisible(true);
	}
	
	public Display(int[][] map) {
		this.map = map;
	}
	
	public class Panel extends JPanel{
		
		@Override
		public void paint(Graphics g) {
			for(int i = 0; i < map.length; i++) {
				for(int j = 0; j < map[0].length; j++) {
					switch(map[i][j]) {
					case 0:
						g.setColor(Color.white);
						break;
					case 1:
						g.setColor(Color.black);
						break;
					case 2:
						g.setColor(Color.gray);
						break;
					case 3:
						g.setColor(Color.blue);
						break;
						
					}
					
					g.fillRect(i*20, j*20, 20, 20);
				}
			}
		}
		
	}
	
}
