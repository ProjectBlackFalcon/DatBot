package ia.fight.map;

import java.util.ArrayList;

import ia.fight.brain.Position;

public class LineOfSight {
	
	/**
	 * Returns true if the first position is visible from the second position, according to the obstacles set on the map.
	 * @param x0 x of the first position
	 * @param y0 y of the first position
	 * @param x1 x of the second position
	 * @param y1 y of the second position
	 * @param map ArrayList of int ArrayList that contains every block on the map.
	 * @return the visibility between the two positions
	 */
	public static boolean visibility(Position p1, Position p2, int[][] map){
		int x0 = p1.getX();
		int y0 = p1.getY();
		int x1 = p2.getX();
		int y1 = p2.getY();
		
	    boolean clear = true;
	    int dx = Math.abs(x1 - x0);
	    int dy = Math.abs(y1 - y0);
	    int x = x0;
	    int y = y0;
	    int n = -1 + dx + dy;
	    int x_inc = (x1 > x0 ? 1 : -1);
	    int y_inc = (y1 > y0 ? 1 : -1);
	    int error = dx - dy;
	    dx *= 2;
	    dy *= 2;
	
	    for (int i = 0; i < 1; i++){
	
	        if (error > 0)
	        {
	            x += x_inc;
	            error -= dy;
	        }
	
	        else if (error < 0)
	        {
	            y += y_inc;
	            error += dx;
	        }
	
	        else {
	            x += x_inc;
	            error -= dy;
	            y += y_inc;
	            error += dx;
	            n--;
	        }
	    }
	
	    while (n > 0 && clear){
	
	        if (map[y][x] == 1 || map[y][x] == 3 || map[y][x] == 4){
	            clear = false;
	        }
	
	        else {
	
	            if (error > 0)
	            {
	                x += x_inc;
	                error -= dy;
	            }
	
	            else if (error < 0)
	            {
	                y += y_inc;
	                error += dx;
	            }
	
	            else {
	                x += x_inc;
	                error -= dy;
	                y += y_inc;
	                error += dx;
	                n--;
	            }
	
	            n--;
	        }
	    }
	
	    long stop = System.currentTimeMillis();
	    
	    return clear;
	}
}
