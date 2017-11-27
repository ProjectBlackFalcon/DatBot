package ia.fight.structure.spells.spelltypes;

import ia.fight.brain.PlayingEntity;
import ia.fight.structure.spells.Spell;

public class Removal extends Spell{
	
	private String characteristic;
	private int amount;
	
	public Removal(String characteristic, int amount) {
		this.characteristic = characteristic;
		this.amount = amount;
	}

	@Override
	public void applySpell(PlayingEntity pe, PlayingEntity target, boolean trueDamage, int intensity) {
		pe.getModel().addBuff(new Buff("-"+characteristic, amount, false, 1));
	}

}
