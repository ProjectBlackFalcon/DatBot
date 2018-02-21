package ia.fight.structure.spells.spelltypes;

import java.util.ArrayList;
import java.util.Random;

import ia.fight.brain.Game;
import ia.fight.brain.PlayingEntity;
import ia.fight.brain.Position;
import ia.fight.map.Map;
import ia.fight.structure.spells.AreaOfEffect;
import ia.fight.structure.spells.Spell;
import ia.fight.structure.spells.SpellObject;


public class Damage extends Spell{

	private int lowDamage, highDamage;
	private int criticalLowDamage, criticalHighDamage;
	private int type;
	private int chosenDamage = -1;
	private SpellObject superSpell;

	public Damage(int type, int lowDamage, int highDamage, int criticalLowDamage, int criticalHighDamage, SpellObject superSpell) {
		this.lowDamage = lowDamage;
		this.highDamage = highDamage;
		this.criticalHighDamage = criticalHighDamage;
		this.criticalLowDamage = criticalLowDamage;
		this.superSpell = superSpell;
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
	
	public int chosenDamage(PlayingEntity pe, PlayingEntity caster){
		Random rand = new Random();

		int baseDamage;
		boolean critical = rand.nextInt(100) < caster.getModel().getCriticalChance()+superSpell.getCriticalChance();
		
		if(critical)
			baseDamage = this.getCriticalLowDamage()+rand.nextInt(this.getCriticalHighDamage()-this.getCriticalLowDamage());
		else
			baseDamage = this.getLowDamage()+rand.nextInt(this.getHighDamage()-this.getLowDamage());
		
		int addedDamage = baseDamage*((caster.getModel().getStats()[this.getType()] + caster.getModel().getPower())/100);
		int fixedDamage = caster.getModel().getFixedDamages() + caster.getModel().getElementaryDamage()[this.getType()];
		if(critical)
			fixedDamage += caster.getModel().getCriticalDamage();
		
		int outputDamage = baseDamage + addedDamage + fixedDamage;
		
		int fixedReduction = pe.getModel().getResFixed()[this.getType()];
		if(critical)
			fixedDamage += pe.getModel().getCriticalResistance();
		int prcntReduction = outputDamage / (100/pe.getModel().getResPrcnt()[this.getType()]);
		
		int finalDamage = outputDamage - fixedReduction - prcntReduction;
		/*
		Game.println("Casting spell : "+superSpell.getName());
		Game.println("Base damage : "+this.getLowDamage()+" - "+this.getCriticalHighDamage());
		Game.println("Chosen damage : "+baseDamage);
		Game.println("Added damage : "+addedDamage);
		Game.println("Fixed damage : "+fixedDamage);
		Game.println("Output damage : "+outputDamage);
		Game.println("Fixed reduction : "+fixedReduction);
		Game.println("Final damage : "+finalDamage);
		*/
		return finalDamage;
	}
	
	public int previz(PlayingEntity caster, PlayingEntity pe, boolean print) {

		double baseDamage = ((double)this.getLowDamage()+(double)this.getHighDamage())/2;
		
		double addedDamage = baseDamage*(((double)caster.getModel().getStats()[this.getType()] + (double)caster.getModel().getPower())/100);
		
		double fixedDamage = caster.getModel().getFixedDamages() + caster.getModel().getElementaryDamage()[this.getType()];
		
		double outputDamage = baseDamage + addedDamage + fixedDamage;
		
		double fixedReduction = pe.getModel().getResFixed()[this.getType()];
		
		double prcntReduction;
		
		if(pe.getModel().getResPrcnt()[this.getType()] == 0) {
			prcntReduction = 0;
		}else {
			prcntReduction = outputDamage / (100/pe.getModel().getResPrcnt()[this.getType()]);
		}
		
		double finalDamage = outputDamage - fixedReduction - prcntReduction;
		
		if(print) {
			Game.println(baseDamage+" * ( "+caster.getModel().getStats()[this.getType()]+" + "+caster.getModel().getPower()+" ) / 100");
			Game.println(caster.getModel().getFixedDamages()+" | "+caster.getModel().getElementaryDamage()[this.getType()]);
			Game.println("Casting spell : "+superSpell.getName());
			Game.println("Base damage : "+this.getLowDamage()+" - "+this.getCriticalHighDamage());
			Game.println("Chosen damage : "+baseDamage);
			Game.println("Added damage : "+addedDamage);
			Game.println("Fixed damage : "+fixedDamage);
			Game.println("Output damage : "+outputDamage);
			Game.println("Fixed reduction : "+fixedReduction);
			Game.println("Final damage : "+finalDamage);
		}
		
		
		
		return (int)finalDamage;
	}
	
	@Override
	public void applySpell(PlayingEntity caster, PlayingEntity target, boolean trueDamage, int intensity) {
		
		int finalDamage;
		
		if(trueDamage) {
			finalDamage = intensity;
		}else {
			finalDamage = this.chosenDamage(caster, target);
		}

		target.getModel().removeLP(finalDamage);
	}
	
}
