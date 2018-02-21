package ia.fight.structure.spells.spelltypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import ia.fight.brain.Game;
import ia.fight.brain.PlayingEntity;
import ia.fight.brain.Position;
import ia.fight.map.Map;
import ia.fight.structure.spells.Spell;
import ia.fight.structure.spells.SpellObject;

public class Pushback extends Spell{
	
	private int cellAmount;
	private SpellObject superSpell;
	
	public Pushback(int cellAmount, SpellObject superSpell) {
		this.cellAmount = cellAmount;
		this.superSpell = superSpell;
	}

	
	
	public void applySpell(PlayingEntity pe, PlayingEntity target, boolean trueDamage, int intensity, Position center) {
		Position victim = target.getPosition();
		
		int x1 = center.getX();
		int y1 = center.getY();
		
		int x2 = victim.getX();
		int y2 = victim.getY();

		int xDist = 0;
		int yDist = 0;
		
		if(x2-x1>0){
			xDist = this.cellAmount;
		}
		
		if(x2-x1>0){
			xDist = this.cellAmount;
		}else if(x2-x1 < 0){
			xDist = -this.cellAmount;
		}
		
		if(y2-y1>0){
			yDist = this.cellAmount;
		}else if(y2-y1 < 0){
			yDist = -this.cellAmount;
		}
		
		ArrayList<Position> concernedPositions = new ArrayList<>();
		
		if(xDist == 0){
			if(yDist < 0){
				for(int i = yDist; i < 0; i++){
					concernedPositions.add(new Position(victim.getX(), victim.getY()+i));
				}
			}else if(yDist > 0){
				for(int i = 1; i <= yDist; i++){
					concernedPositions.add(new Position(victim.getX(), victim.getY()+i));
				}
			}
		}else if(yDist == 0){
			if(xDist < 0){
				for(int i = xDist; i < 0; i++){
					concernedPositions.add(new Position(victim.getX()+i, victim.getY()));
				}
			}else if(xDist > 0){
				for(int i = 1; i <= xDist; i++){
					concernedPositions.add(new Position(victim.getX()+i, victim.getY()));
				}
			}
		}
		
		if(xDist < 0 && yDist < 0){
			for(int i = xDist; i < 0; i++){
				concernedPositions.add(new Position(victim.getX()+i, victim.getY()+i));
			}
		}else if(xDist < 0 && yDist > 0){
			for(int i = xDist; i < 0; i++){
				concernedPositions.add(new Position(victim.getX()+i, victim.getY()-i));
			}
		}else if(xDist > 0 && yDist < 0){
			for(int i = yDist; i < 0; i++){
				concernedPositions.add(new Position(victim.getX()-i, victim.getY()+i));
			}
		}else if(xDist > 0 && yDist > 0){
			for(int i = 1; i <= yDist; i++){
				concernedPositions.add(new Position(victim.getX()+i, victim.getY()+i));
			}
		}
		
		
		Collections.sort(concernedPositions, new Comparator<Position>(){

			@Override
			public int compare(Position pos1, Position pos2) {
				if(Position.distance(pos1, victim) > Position.distance(pos2, victim)){
					return 1;
				}else{
					return -1;
				}
			}
			
		});

//		for(int i = 0; i < concernedPositions.size(); i++){
//			if(Game.map.isPositionWalkable(concernedPositions.get(i))){
//				target.setPosition(concernedPositions.get(i));
//			}else{
//				target.getModel().removeLP((concernedPositions.size()-i)*50);
//				//Game.println("Got squished against a wall with "+(concernedPositions.size()-i)+" remaining.");
//				break;
//			}
//		}
	}

	@Override
	public void applySpell(PlayingEntity pe, PlayingEntity target, boolean trueDamage, int intensity) {
		throw new Error("Apply spell was called for the pushback. It is not intended to be used this way. Please use the applySpell method containing the Position attribute 'center'");
	}

}
