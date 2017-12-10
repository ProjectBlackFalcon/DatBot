package ia.fight.structure.spells.spelltypes;

import ia.fight.brain.PlayingEntity;
import ia.fight.structure.spells.Spell;

public class Buff extends Spell{
	
	private String type;
	private int value;
	private boolean percentage;
	private int turnDuration;
	
	public Buff(String type, int value, int critValue,  boolean percentage, int turnDuration) {
		this.type = type;
		this.value = value;
		this.percentage = percentage;
		this.turnDuration = turnDuration;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public boolean isPercentage() {
		return percentage;
	}

	public void setPercentage(boolean percentage) {
		this.percentage = percentage;
	}

	public int getTurnDuration() {
		return turnDuration;
	}
	
	public void removeTurnDurationQuantity(int turnDuration){
		this.turnDuration -= turnDuration;
	}

	public void setTurnDuration(int turnDuration) {
		this.turnDuration = turnDuration;
	}

	@Override
	public String toString() {
		String s = this.type+" "+this.value+", "+this.turnDuration+" turn(s) left";
		return s;
	}

	@Override
	public void applySpell(PlayingEntity pe, PlayingEntity target, boolean trueDamage, int intensity) {
		target.getModel().addBuff(this);
	}
	
}
