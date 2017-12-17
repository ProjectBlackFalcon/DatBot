package ia.fight.structure.spells.spelltypes;

import ia.fight.brain.PlayingEntity;
import ia.fight.structure.spells.Spell;

public class LifeSteal extends Spell{

	private int lowDamage, highDamage;
	private int criticalLowDamage, criticalHighDamage;
	private int type;
	
	public LifeSteal(int type, int lowDamage, int highDamage, int criticalLowDamage, int criticalHighDamage) {
		this.lowDamage = lowDamage;
		this.highDamage = highDamage;
		this.criticalHighDamage = criticalHighDamage;
		this.criticalLowDamage = criticalLowDamage;
	}
	
	public int getType(){
		return type;
	}

	public void setType(int type){
		this.type = type;
	}
	
	
	public int getLowDamage() {
		return lowDamage;
	}

	public void setLowDamage(int lowDamage) {
		this.lowDamage = lowDamage;
	}

	public int getHighDamage() {
		return highDamage;
	}

	public void setHighDamage(int highDamage) {
		this.highDamage = highDamage;
	}

	public int getCriticalLowDamage() {
		return criticalLowDamage;
	}

	public void setCriticalLowDamage(int criticalLowDamage) {
		this.criticalLowDamage = criticalLowDamage;
	}

	public int getCriticalHighDamage() {
		return criticalHighDamage;
	}

	public void setCriticalHighDamage(int criticalHighDamage) {
		this.criticalHighDamage = criticalHighDamage;
	}

	@Override
	public void applySpell(PlayingEntity pe, PlayingEntity target, boolean trueDamage, int intensity) {
		pe.getModel().removeLP(this.getHighDamage());
		target.getModel().addLP(this.getHighDamage()/2);
	}
	
	
}
