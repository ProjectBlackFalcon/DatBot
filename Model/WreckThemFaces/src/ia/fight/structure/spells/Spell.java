package ia.fight.structure.spells;

import ia.fight.brain.PlayingEntity;
import ia.fight.brain.Position;

public abstract class Spell {
	
	String getSpellType(){
		return this.getClass().getName().toLowerCase();
	}

	public abstract void applySpell(PlayingEntity pe, PlayingEntity target, boolean trueDamage, int intensity);
	
}
