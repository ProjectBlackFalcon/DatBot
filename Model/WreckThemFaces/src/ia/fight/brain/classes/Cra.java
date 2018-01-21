package ia.fight.brain.classes;

import ia.fight.structure.Player;
import ia.fight.structure.classes.CraModel;
import ia.fight.structure.spells.SpellObject;

public class Cra extends Player{
	
	SpellObject[] spells;
	
	public Cra(String name, int baseLP, int baseAP, int baseMP, int level) {
		super(name, baseLP, baseAP, baseMP, level);
		spells = CraModel.getSpells(level);
	}
	
	public SpellObject[] getSpells(){
		return this.spells;
	}
	
	public String getType(){
		return "cra";
	}
}