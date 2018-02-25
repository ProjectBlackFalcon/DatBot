package ia.fight.brain;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import ia.fight.map.LineOfSight;
import ia.fight.map.Map;
import ia.fight.structure.Player;
import ia.fight.structure.spells.SpellObject;

public class PlayingEntity {

	String team;
	Player model;
	Position position;
	int ID;
	
	public PlayingEntity(int ID, Position position, String team, Player model) {
		this.ID = ID;
		this.position = position;
		this.team = team;
		this.model = model;
	}
	
	public int getID() {
		return ID;
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
		Game.println(optimalTurn);
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
		for(int i = 0; i < getModel().getAvailableSpells(false).size(); i++) {
			if(getModel().getAvailableSpells(false).get(i).isEntityTargetableBySpell(victim)) {
				spellsForEnnemy.add(getModel().getAvailableSpells(false).get(i));
			}
		}
		
		PlayingEntity caster = this;
		
		Collections.sort(spellsForEnnemy, new Comparator<SpellObject>() {
			@Override
			public int compare(SpellObject arg0, SpellObject arg1) {
				if(arg0.getDamagePreviz(caster, victim, false) < arg1.getDamagePreviz(caster, victim, false)) {
					return 1;
				}else {
					return -1;
				}
			}
		});
		
		ArrayList<SpellObject> optimalTurn = new ArrayList<>();
		int tempAP = this.getModel().getAP();
		Game.println("AP available : "+tempAP);
		
		for(int i = 0; i < spellsForEnnemy.size(); i++) {
			Game.println(spellsForEnnemy.get(i)+" "+spellsForEnnemy.get(i).getDamagePreviz(caster, victim, false));
			Game.println(spellsForEnnemy.get(i).remainingCastsForThisEntity(victim));
			
			for(int j = 0; j < spellsForEnnemy.get(i).remainingCastsForThisEntity(victim); j++) {
				if(tempAP >= spellsForEnnemy.get(i).getCost()) {
					optimalTurn.add(spellsForEnnemy.get(i));
					tempAP -= spellsForEnnemy.get(i).getCost();
				}
			}
		}
		
		long stop = System.currentTimeMillis();
		
		//Game.println(stop-start+" ms");
		return optimalTurn;
	}
	
	public ArrayList<SpellObject> getOptimalTurnFrom(Position position, PlayingEntity victim, boolean show, Map map){
		long start = System.currentTimeMillis();
		ArrayList<SpellObject> spellsForEnnemy = new ArrayList<>();
		ArrayList<SpellObject> allAvailableSpells = getModel().getAvailableSpells(true);
		for(int i = 0; i < allAvailableSpells.size(); i++) {
			Game.println(allAvailableSpells.get(i).getName()+" "+allAvailableSpells.get(i).remainingCastsForThisEntity(victim));
			boolean entityTargetable = allAvailableSpells.get(i).isEntityTargetableBySpell(victim);
			boolean hasVisibility = LineOfSight.visibility(position, victim.getPosition(), map.getBlocks());
			boolean requiresLineOfSight = allAvailableSpells.get(i).requiresLineOfSight();
			boolean overMinimumRange = allAvailableSpells.get(i).getMinimumRange() <= Position.distance(position, victim.getPosition());
			boolean hasModifiableRange = allAvailableSpells.get(i).isModifiableRange();
			boolean underMaximumRange = hasModifiableRange ? allAvailableSpells.get(i).getMaximumRange()+this.getModel().getRange() >= Position.distance(position, victim.getPosition()) : allAvailableSpells.get(i).getMaximumRange() >= Position.distance(position, victim.getPosition());
			boolean entityIsWithinDistance = overMinimumRange && underMaximumRange;
			boolean isStraightLineCastAndInLine = allAvailableSpells.get(i).isStraightLineCast() && (!(position.getX() != victim.getPosition().getX() && position.getY() != victim.getPosition().getY()));
			boolean isNotStraightLineCast = !allAvailableSpells.get(i).isStraightLineCast();
	
			if(entityTargetable && (hasVisibility || !requiresLineOfSight)) {
				Game.println("    LoV is okay.");
				if(entityIsWithinDistance) {
					Game.println("    Entity is within distance !");
					if(isStraightLineCastAndInLine || isNotStraightLineCast) {
						Game.println("    In line or doesn't require slc");
						spellsForEnnemy.add(allAvailableSpells.get(i));
					}
				}
			}
		}
		
		PlayingEntity caster = this;
		
		ArrayList<String> brainText = new ArrayList<>();
		
		if(spellsForEnnemy.size() > 0) {
			brainText.add("Spell selection for damage.");
		}else {
			brainText.add("No spell currently available for selection !");
		}

		Collections.sort(spellsForEnnemy, new Comparator<SpellObject>() {
			@Override
			public int compare(SpellObject arg0, SpellObject arg1) {
				if(arg0.getDamagePreviz(caster, victim, false) < arg1.getDamagePreviz(caster, victim, false)) {
					return 1;
				}else {
					return -1;
				}
			}
		});
		
		for(int i = spellsForEnnemy.size()-1; i >= 0; i--) {
			if(spellsForEnnemy.get(i).getDamagePreviz(caster, victim, false) <= 0) {
				Game.println("Removing "+spellsForEnnemy.get(i)+" because of lack of damage.");
				spellsForEnnemy.remove(i);
			}
		}
		
		for(int i = 0; i < spellsForEnnemy.size(); i++) {
			brainText.add(spellsForEnnemy.get(i).getName()+" : "+spellsForEnnemy.get(i).getDamagePreviz(caster, victim, false));
		}
		
		
		if(show) {
			Game.los.panel.updateBrainText(brainText);
		}
		
		
		ArrayList<SpellObject> optimalTurn = new ArrayList<>();
		int tempAP = this.getModel().getAP();
//		Game.println("AP available : "+tempAP);
		
		for(int i = 0; i < spellsForEnnemy.size(); i++) {
//			Game.println(spellsForEnnemy.get(i)+" "+spellsForEnnemy.get(i).getDamagePreviz(caster, victim));
//			Game.println(spellsForEnnemy.get(i).remainingCastsForThisEntity(victim));
			
			for(int j = 0; j < spellsForEnnemy.get(i).remainingCastsForThisEntity(victim); j++) {
				if(tempAP >= spellsForEnnemy.get(i).getCost()) {
					optimalTurn.add(spellsForEnnemy.get(i));
					tempAP -= spellsForEnnemy.get(i).getCost();
				}
			}
		}
		
		Game.println("Creating best turn ...");
		for(int i = 0; i < optimalTurn.size(); i++) {
			Game.println(optimalTurn.get(i).getName()+" "+optimalTurn.get(i).getDamagePreviz(caster, victim, false));
		}
		
		long stop = System.currentTimeMillis();
		
		//Game.println(stop-start+" ms");
		//Game.println(stop-start+" ms");
		return optimalTurn;
	}
	
}
