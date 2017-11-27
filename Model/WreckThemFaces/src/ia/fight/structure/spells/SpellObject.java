package ia.fight.structure.spells;

import java.util.ArrayList;

import org.json.simple.JSONObject;

import ia.fight.brain.Game;
import ia.fight.brain.PlayingEntity;
import ia.fight.brain.Position;
import ia.fight.map.Map;
import ia.fight.structure.spells.spelltypes.Pushback;

public class SpellObject {

	private ArrayList<Spell> spells;
	
	public SpellObject(String name) {
		spells = new ArrayList<>();
		this.name = name;
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
		return this.getName();
	}
	

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
		
		System.out.println("Touched entities : "+touchedEntities);
		
		for(int j = 0; j < touchedEntities.size(); j++) {
			for(int i = 0; i < this.getSpells().size(); i++){
				if(this.getSpells().get(i).getClass().getSimpleName().equals("Pushback")) {
					((Pushback)this.getSpells().get(i)).applySpell(caster, touchedEntities.get(j), trueDamage, intensity, pos);
				}else {
					this.getSpells().get(i).applySpell(caster, touchedEntities.get(j), trueDamage, intensity);
				}
				
			}
		}
		
		System.out.println(caster);
		caster.getModel().removeAP(this.getCost());
		System.out.println("Removed AP from caster "+caster);
	}	
}
