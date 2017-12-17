package ia.fight.structure.spells;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import ia.fight.brain.Game;
import ia.fight.brain.PlayingEntity;
import ia.fight.brain.Position;
import ia.fight.map.Map;
import ia.fight.structure.spells.spelltypes.*;

public class SpellObject {

	
	private int ID;
	private ArrayList<Spell> spells;
	private int cooldown = 0;
	private int spellCounter = 0;
	private ArrayList<int[]> spellPerEntityCounter;

	private int recastInterval = 0;
	private int criticalChance = 0;
	
	private int minimumRange = 0;
	private int maximumRange = 0;
	private boolean modifiableRange = false;
	
	private int numberOfCastsPerTarget = 10;
	private int numberOfCastsPerTurn = 10;
	private int maxEffectAccumulation = 10;
	
	private boolean straightLineCast = false;
	private AreaOfEffect aoe = new AreaOfEffect("cell", 0);
	
	private int cost = 4;
	private boolean requireLineOfSight = true;
	private String name;
	
	public int level;
	
	
	public SpellObject(String name, int level, int ID) {
		spells = new ArrayList<>();
		this.name = name;
		spellPerEntityCounter = new ArrayList<>();
		this.level = level;
		this.ID = ID;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int iD) {
		ID = iD;
	}
	
	public boolean isAvailable() {
		boolean cd = cooldown >= 0;
		boolean sc = spellCounter < numberOfCastsPerTurn;

		return cd && sc;
	}
	
	public int getDamagePreviz(PlayingEntity caster, PlayingEntity target) {
		int damagePreviz = 0;
		
		for(int i = 0; i < this.spells.size(); i++) {
			if(this.spells.get(i).getClass().getSimpleName().equals("Damage")) {
				damagePreviz += ((Damage)spells.get(i)).previz(caster, target);
			}
		}
		
		return damagePreviz;
	}
	
	public boolean isEntityTargetableBySpell(PlayingEntity entity) {
		for(int i = 0; i < this.spellPerEntityCounter.size(); i++) {
			if(this.spellPerEntityCounter.get(i)[0] == entity.getID()) {
				if(this.spellPerEntityCounter.get(i)[1] < this.numberOfCastsPerTarget) {
					return true;
				}else {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public int remainingCastsForThisEntity(PlayingEntity entity) {
		if(!isAvailable()) {
			return 0;
		}
		
		for(int i = 0; i < this.spellPerEntityCounter.size(); i++) {
			if(this.spellPerEntityCounter.get(i)[0] == entity.getID()) {
				return this.numberOfCastsPerTarget - this.spellPerEntityCounter.get(i)[1];
			}
		}
		
		if(this.getRecastInterval() > 0 && spellCounter < 1) {
			return 1;
		}
		
		return Math.min(this.numberOfCastsPerTurn - spellCounter, this.numberOfCastsPerTarget); 
	}
	
	public void passTurn() {
		spellPerEntityCounter.clear();
		spellCounter = 0;
		if(cooldown > 0) {
			cooldown--;
		}
	}
	
	public void addSpell(Spell spell){
		this.spells.add(spell);
	}
	
	public void removeSpell(Spell spell){
		this.spells.remove(spell);
	}
	
	public void setSpells(ArrayList<Spell> spells){
		this.spells = spells;
	}
	
	public ArrayList<Spell> getSpells(){
		return this.spells;
	}
	
	public void clearSpells(){
		this.spells.clear();
	}
	
	@Override
	public String toString() {
		return this.getName()+" ("+this.getMinimumRange()+" - "+this.getMaximumRange()+")";
	}

	public String getName(){
		return this.name;
	}
	
	public JSONObject toJSON(){
		JSONObject obj = new JSONObject();
		
		obj.put("spellname", this.getName());
		
		return obj;
	}
	
	public int getRecastInterval() {
		return recastInterval;
	}
	public void setRecastInterval(int recastInterval) {
		this.recastInterval = recastInterval;
	}
	public int getCriticalChance() {
		return criticalChance;
	}
	public void setCriticalChance(int criticalChance) {
		this.criticalChance = criticalChance;
	}
	public int getMinimumRange() {
		return minimumRange;
	}
	public void setMinimumRange(int minimumRange) {
		this.minimumRange = minimumRange;
	}
	public int getMaximumRange() {
		return maximumRange;
	}
	public void setMaximumRange(int maximumRange) {
		this.maximumRange = maximumRange;
	}
	public boolean isModifiableRange() {
		return modifiableRange;
	}
	public void setModifiableRange(boolean modifiableRange) {
		this.modifiableRange = modifiableRange;
	}
	public int getNumberOfCastsPerTarget() {
		return numberOfCastsPerTarget;
	}
	public void setNumberOfCastsPerTarget(int numberOfCastsPerTarget) {
		this.numberOfCastsPerTarget = numberOfCastsPerTarget;
	}
	public int getNumberOfCastsPerTurn() {
		return numberOfCastsPerTurn;
	}
	public void setNumberOfCastsPerTurn(int numberOfCastsPerTurn) {
		this.numberOfCastsPerTurn = numberOfCastsPerTurn;
	}
	public int getMaxEffectAccumulation() {
		return maxEffectAccumulation;
	}
	public void setMaxEffectAccumulation(int maxEffectAccumulation) {
		this.maxEffectAccumulation = maxEffectAccumulation;
	}
	public boolean isStraightLineCast() {
		return straightLineCast;
	}
	public void setStraightLineCast(boolean straightLineCast) {
		this.straightLineCast = straightLineCast;
	}
	public AreaOfEffect getAreaOfEffect() {
		return aoe;
	}
	public void setAreaOfEffect(AreaOfEffect spellZone) {
		this.aoe = spellZone;
	}
	public int getCost() {
		return cost;
	}
	public void setCost(int cost) {
		this.cost = cost;
	}
	public boolean requiresLineOfSight() {
		return requireLineOfSight;
	}
	public void setRequireLineOfSight(boolean lineOfSight) {
		this.requireLineOfSight = lineOfSight;
	}

	/**
	 * Apply a spell on a certain entity
	 * @param pe Entity on which the action is cast
	 * @param action action cast
	 */
	public void applySpells(PlayingEntity caster, Position pos, boolean trueDamage, int intensity) {
		ArrayList<PlayingEntity> touchedEntities = new ArrayList<>();
		ArrayList<Position> positions = this.getAreaOfEffect().getPositions(caster.getPosition(), pos);
		
		Map map = Game.map;
		ArrayList<int[]> obstacles = map.getObstacles();
		
		for(int j = 0; j < obstacles.size(); j++) {
			int[] obstacle = obstacles.get(j);
			if(obstacle[2] > 2) {
				for(int i = 0; i < positions.size(); i++) {
					if(obstacle[0] == positions.get(i).getX() && obstacle[1] == positions.get(i).getY()) {
						ArrayList<PlayingEntity> playingEntities = Game.playingEntities;
						for(int k = 0; k < playingEntities.size(); k++) {
							if(playingEntities.get(k).getPosition().deepEquals(positions.get(i))) {
								touchedEntities.add(playingEntities.get(k));
							}
						}
					}
				}
			}
		}
		
		Game.log.println("Touched entities : "+touchedEntities);
		
		for(int j = 0; j < touchedEntities.size(); j++) {
			for(int i = 0; i < this.getSpells().size(); i++){
				if(this.getSpells().get(i).getClass().getSimpleName().equals("Pushback")) {
					((Pushback)this.getSpells().get(i)).applySpell(caster, touchedEntities.get(j), trueDamage, intensity, pos);
				}else {
					this.getSpells().get(i).applySpell(caster, touchedEntities.get(j), trueDamage, intensity);
				}
				
				for(int k = 0; k < Game.playingEntities.size(); k++) {
					if(Game.playingEntities.get(k).getModel().getLP() <= 0) {
						Game.log.println(Game.playingEntities.get(k)+" died !");
						Game.playingEntities.remove(k);
					}
				}
				
			}
		}

		this.spellCounter++;
		
		for(int i = 0; i < Game.playingEntities.size(); i++) {
			if(Game.playingEntities.get(i).getPosition().deepEquals(pos)) {
				
				Game.log.println("Casting spell directly onto : "+Game.playingEntities.get(i));
				
				boolean found = false;
				for(int j = 0; j < this.spellPerEntityCounter.size(); j++) {
					if(this.spellPerEntityCounter.get(j)[0] == Game.playingEntities.get(i).getID()) {
						found = true;
						this.spellPerEntityCounter.get(j)[1]++;
					}
				}
				
				if(!found) {
					this.spellPerEntityCounter.add(new int[] {Game.playingEntities.get(i).getID(), 1});
				}
			}
		}
		
		for(int i = 0; i < this.spellPerEntityCounter.size(); i++) {
			Game.log.println("    "+this.spellPerEntityCounter.get(i)[0]+" "+this.spellPerEntityCounter.get(i)[1]);
		}
		this.cooldown = this.getRecastInterval();
		caster.getModel().removeAP(this.getCost());
	}	
}
