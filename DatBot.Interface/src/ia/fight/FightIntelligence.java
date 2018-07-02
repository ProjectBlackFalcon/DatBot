package ia.fight;

import java.util.List;

import ia.entities.Spell;
import ia.entities.entity.Entity;
import ia.entities.entity.MainEntity;
import ia.map.Position;
import ia.map.TransformedCell;
import ia.utils.UtilsProtocol;
import utils.d2p.map.CellData;

public class FightIntelligence {
	
	UtilsProtocol protocol;
	
	
	public FightIntelligence(UtilsProtocol protocol) {
		this.protocol = protocol;
	}
	
	/**
	 * Returns true if the specified position is targetable by the caster given the spell. Takes into account the range, the line of sight
	 * and the straight line cast.
	 * @param caster PlayingEntity that casts the spell
	 * @param spell Spell cast
	 * @param cell Cell targeted
	 * @return boolean
	 * 
	 * @author jikiw
	 */
	public static boolean isCellTargetableBySpell(MainEntity caster, Spell spell, Position cell,  TransformedCell[][] cells){
		
		if(spell == null){
			return false;
		}
				
		int distance = Position.distance(caster.getPosition(), cell);
		
		if(spell.isRangeCanBeBoosted()) {
			if(distance < spell.getMinRange() || distance > spell.getRange() + caster.getAdditionalInfo().getRange().getTotal()) {
				return false;
			}
		}else {
			if(distance < spell.getMinRange() || distance > spell.getRange()) {
				return false;
			}
		}
		
		
		if(spell.isCastInLine() && caster.getPosition().getX() != cell.getX() && caster.getPosition().getY() != cell.getY()){
			return false;
		}
		
		if(spell.isCastTestLos()) {
			return visibility(caster.getPosition(), cell, cells);
		}else {
			return true;
		}
	}
    
    
	/**
	 * Returns true if the first position is visible from the second position, according to the obstacles set on the map.
	 * @param p1 first position
	 * @param p2 second position
	 * @param map ArrayList of int ArrayList that contains every block on the map.
	 * @return the visibility between the two positions
	 */
	public static boolean visibility(Position p1, Position p2, TransformedCell[][] map){
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
		    	
	        if (!map[x][y].isLos()){
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

	public void getBestTurn(Entity roxxor, List<Entity> entities, List<CellData> cells) throws Exception {
	}

}
