package ia.fight.structure.spells.spelltypes;

import ia.fight.brain.PlayingEntity;
import ia.fight.structure.spells.Spell;

public class Trap extends Spell{

	@Override
	public void applySpell(PlayingEntity pe, PlayingEntity target, boolean trueDamage, int intensity) {
		throw new Error("Unsupported spell type : "+this.getClass().getSimpleName());
	}

}
