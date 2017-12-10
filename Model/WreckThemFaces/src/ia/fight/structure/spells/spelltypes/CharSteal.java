package ia.fight.structure.spells.spelltypes;

import ia.fight.brain.PlayingEntity;
import ia.fight.structure.spells.Spell;

public class CharSteal extends Spell{
	
	private String characteristic;
	private int value;

	public CharSteal(String characteristic, int value) {
		this.characteristic = characteristic;
		this.value = value;
	}
	

	@Override
	public void applySpell(PlayingEntity pe, PlayingEntity target, boolean trueDamage, int intensity) {
		//Game.log.println("Taking "+value+" "+characteristic+" off "+target.getModel().getBuffs());
		target.getModel().addBuff(new Buff("-"+characteristic, value, value, false, 1));
		//Game.log.println("Taking "+value+" "+characteristic+" off "+target.getModel().getBuffs());
		
		//Game.log.println("Adding "+value+" "+characteristic+" off "+pe.getModel().getBuffs());
		pe.getModel().addBuff(new Buff("+"+characteristic, value, value, false, 1));
		//Game.log.println("Adding "+value+" "+characteristic+" off "+pe.getModel().getBuffs());
	}
	
}
