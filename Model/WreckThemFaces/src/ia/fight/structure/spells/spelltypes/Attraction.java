package ia.fight.structure.spells.spelltypes;

import ia.fight.brain.PlayingEntity;
import ia.fight.structure.spells.Spell;

public class Attraction extends Spell{

	public Attraction() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void applySpell(PlayingEntity pe, PlayingEntity target, boolean trueDamage, int intensity) {
		throw new Error("Unsupported spell type : "+this.getClass().getSimpleName());
	}
	
}
