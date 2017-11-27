package ia.fight.brain.classes;

import ia.fight.structure.Player;
import ia.fight.structure.classes.CraModel;
import ia.fight.structure.spells.SpellObject;

public class Cra extends Player{
	
	SpellObject[] spells;
	
	public Cra(String name, int baseLP, int baseAP, int baseMP) {
		super(name, baseLP, baseAP, baseMP);
		spells = CraModel.getSpells();
	}
	
	public SpellObject[] getSpells(){
		return this.spells;
	}
	
	@Override
	public String toString() {
		return "Cra player with base LP/AP/MP : "+this.getBaseLP()+"/"+this.getBaseAP()+"/"+this.getBaseMP();
	}
}
