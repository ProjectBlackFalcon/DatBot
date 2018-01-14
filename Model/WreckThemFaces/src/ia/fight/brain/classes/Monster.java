package ia.fight.brain.classes;

import ia.fight.structure.Player;
import ia.fight.structure.classes.CraModel;
import ia.fight.structure.spells.SpellObject;

public class Monster extends Player{
	
	SpellObject[] spells;
	
	public Monster(String name, int baseLP, int baseAP, int baseMP, int level) {
		super(name, baseLP, baseAP, baseMP, level);
		spells = CraModel.getSpells(level);
	}
	
	public SpellObject[] getSpells(){
		return this.spells;
	}
	
	@Override
	public String toString() {
		return "Monster "+super.getName()+" with base LP/AP/MP : "+this.getLP()+"/"+this.getAP()+"/"+this.getMP();
	}
	
	public String getType(){
		return "monster";
	}
}