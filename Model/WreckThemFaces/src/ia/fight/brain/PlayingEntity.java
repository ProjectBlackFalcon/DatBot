package ia.fight.brain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import ia.fight.map.LineOfSight;
import ia.fight.structure.Player;
import ia.fight.structure.spells.SpellObject;

public class PlayingEntity {

	String team;
	Player model;
	Position position;
	int ID;
	boolean npc;
	
	public PlayingEntity(int ID, boolean npc, Position position, String team, Player model) {
		this.ID = ID;
		this.position = position;
		this.team = team;
		this.model = model;
		this.npc = npc;
	}
	
	public int getID() {
		return ID;
	}
	
	public boolean isNpc() {
		return npc;
	}

	public void setNpc(boolean npc) {
		this.npc = npc;
	}

	public Position getPosition() {
		return position;
	}
	
	public Player getModel() {
		return model;
	}
	
	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public void setModel(Player model) {
		this.model = model;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public Object toJSON() {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String toString() {
		return model.getName()+" "+model.getLP()+"/"+model.getAP()+"/"+model.getMP();
	}

	public int[] getOptimalRangeForMaximumDamageOutput(PlayingEntity victim) {
		ArrayList<SpellObject> optimalTurn = getOptimalTurn(victim);
		Game.log.println(optimalTurn);
		int min = optimalTurn.get(0).getMinimumRange();
		int max = optimalTurn.get(0).getMaximumRange();
		
		if(optimalTurn.get(0).isModifiableRange()) {
			max += this.getModel().getRange();
		}
		
		for(int i = 1; i < optimalTurn.size(); i++) {
			if(optimalTurn.get(i).getMinimumRange() > min) {
				min = optimalTurn.get(i).getMinimumRange();
			}
			
			if(optimalTurn.get(0).isModifiableRange()) {
				if(optimalTurn.get(i).getMaximumRange()+this.getModel().getRange() < max) {
					max = optimalTurn.get(i).getMaximumRange()+this.getModel().getRange();
				}
			}else {
				if(optimalTurn.get(i).getMaximumRange() < max) {
					max = optimalTurn.get(i).getMaximumRange();
				}
			}
			
		}

		return new int[] {min, max};
	}
	
	public ArrayList<SpellObject> getOptimalTurn(PlayingEntity victim){
		long start = System.currentTimeMillis();
		ArrayList<SpellObject> spellsForEnnemy = new ArrayList<>();
		for(int i = 0; i < getModel().getAvailableSpells().size(); i++) {
			if(getModel().getAvailableSpells().get(i).isEntityTargetableBySpell(victim)) {
				spellsForEnnemy.add(getModel().getAvailableSpells().get(i));
			}
		}
		
		PlayingEntity caster = this;
		
		Collections.sort(spellsForEnnemy, new Comparator<SpellObject>() {
			@Override
			public int compare(SpellObject arg0, SpellObject arg1) {
				if(arg0.getDamagePreviz(caster, victim) < arg1.getDamagePreviz(caster, victim)) {
					return 1;
				}else {
					return -1;
				}
			}
		});
		
		ArrayList<SpellObject> optimalTurn = new ArrayList<>();
		int tempAP = this.getModel().getAP();
		Game.log.println("AP available : "+tempAP);
		
		for(int i = 0; i < spellsForEnnemy.size(); i++) {
			Game.log.println(spellsForEnnemy.get(i)+" "+spellsForEnnemy.get(i).getDamagePreviz(caster, victim));
			Game.log.println(spellsForEnnemy.get(i).remainingCastsForThisEntity(victim));
			
			for(int j = 0; j < spellsForEnnemy.get(i).remainingCastsForThisEntity(victim); j++) {
				if(tempAP >= spellsForEnnemy.get(i).getCost()) {
					optimalTurn.add(spellsForEnnemy.get(i));
					tempAP -= spellsForEnnemy.get(i).getCost();
				}
			}
		}
		
		long stop = System.currentTimeMillis();
		
		Game.log.println(stop-start+" ms");
		return optimalTurn;
	}
	
	public ArrayList<SpellObject> getOptimalTurnFrom(Position position, PlayingEntity victim){
		long start = System.currentTimeMillis();
		ArrayList<SpellObject> spellsForEnnemy = new ArrayList<>();
		for(int i = 0; i < getModel().getAvailableSpells().size(); i++) {
			
			boolean entityTargetable = getModel().getAvailableSpells().get(i).isEntityTargetableBySpell(victim);
			boolean hasVisibility = LineOfSight.visibility(position, victim.getPosition(), Game.map.getBlocks());
			boolean requiresLineOfSight = getModel().getAvailableSpells().get(i).requiresLineOfSight();
			boolean overMinimumRange = getModel().getAvailableSpells().get(i).getMinimumRange() <= Position.distance(position, victim.getPosition());
			boolean hasModifiableRange = getModel().getAvailableSpells().get(i).isModifiableRange();
			boolean underMaximumRange = hasModifiableRange ? getModel().getAvailableSpells().get(i).getMaximumRange()+this.getModel().getRange() >= Position.distance(position, victim.getPosition()) : getModel().getAvailableSpells().get(i).getMaximumRange() <= Position.distance(position, victim.getPosition());
			boolean entityIsWithinDistance = overMinimumRange && underMaximumRange;
			
			if(entityTargetable && (hasVisibility || !requiresLineOfSight)) {
				if(getModel().getAvailableSpells().get(i).getMinimumRange() <= Position.distance(position, victim.getPosition())) {
					if(getModel().getAvailableSpells().get(i).isModifiableRange()) {
						if(getModel().getAvailableSpells().get(i).getMaximumRange()+this.getModel().getRange() >= Position.distance(position, victim.getPosition())) {
							if(getModel().getAvailableSpells().get(i).isStraightLineCast()) {
								if(!(position.getX() != victim.getPosition().getX() && position.getY() != victim.getPosition().getY())) {
									spellsForEnnemy.add(getModel().getAvailableSpells().get(i));
								}
							}else {
								spellsForEnnemy.add(getModel().getAvailableSpells().get(i));
							}
							
						}
					}else {
						if(getModel().getAvailableSpells().get(i).getMaximumRange() <= Position.distance(position, victim.getPosition())) {
							if(getModel().getAvailableSpells().get(i).isStraightLineCast()) {
								if(!(position.getX() != victim.getPosition().getX() && position.getY() != victim.getPosition().getY())) {
									spellsForEnnemy.add(getModel().getAvailableSpells().get(i));
								}
							}else {
								spellsForEnnemy.add(getModel().getAvailableSpells().get(i));
							}
						}
					}
				}
			}
		}
		
		PlayingEntity caster = this;
		
		Collections.sort(spellsForEnnemy, new Comparator<SpellObject>() {
			@Override
			public int compare(SpellObject arg0, SpellObject arg1) {
				if(arg0.getDamagePreviz(caster, victim) < arg1.getDamagePreviz(caster, victim)) {
					return 1;
				}else {
					return -1;
				}
			}
		});
		
		ArrayList<SpellObject> optimalTurn = new ArrayList<>();
		int tempAP = this.getModel().getAP();
		Game.log.println("AP available : "+tempAP);
		
		for(int i = 0; i < spellsForEnnemy.size(); i++) {
			Game.log.println(spellsForEnnemy.get(i)+" "+spellsForEnnemy.get(i).getDamagePreviz(caster, victim));
			Game.log.println(spellsForEnnemy.get(i).remainingCastsForThisEntity(victim));
			
			for(int j = 0; j < spellsForEnnemy.get(i).remainingCastsForThisEntity(victim); j++) {
				if(tempAP >= spellsForEnnemy.get(i).getCost()) {
					optimalTurn.add(spellsForEnnemy.get(i));
					tempAP -= spellsForEnnemy.get(i).getCost();
				}
			}
		}
		
		long stop = System.currentTimeMillis();
		
		Game.log.println(stop-start+" ms");
		System.out.println(stop-start+" ms");
		return optimalTurn;
	}
	
}
