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
		Game.log.println("Casting spell : "+superSpell.getName());
		Game.log.println("Base damage : "+this.getLowDamage()+" - "+this.getCriticalHighDamage());
		Game.log.println("Chosen damage : "+baseDamage);
		Game.log.println("Added damage : "+addedDamage);
		Game.log.println("Fixed damage : "+fixedDamage);
		Game.log.println("Output damage : "+outputDamage);
		Game.log.println("Fixed reduction : "+fixedReduction);
		Game.log.println("Final damage : "+finalDamage);
		*/
		return finalDamage;
	}
	
	public int previz(PlayingEntity pe, PlayingEntity caster) {

		int baseDamage = (this.getLowDamage()+this.getHighDamage())/2;
		int baseDamageCrit = (this.getCriticalLowDamage()+this.getCriticalHighDamage())/2;

		baseDamageCrit = this.getCriticalHighDamage();
		baseDamage = this.getLowDamage();
		
		int addedDamage = baseDamage*((caster.getModel().getStats()[this.getType()] + caster.getModel().getPower())/100);
		int fixedDamage = caster.getModel().getFixedDamages() + caster.getModel().getElementaryDamage()[this.getType()];
		int	fixedDamageCrit = caster.getModel().getCriticalDamage();
		
		int outputDamage = baseDamage + addedDamage + fixedDamage;
		int outputDamageCrit = baseDamage + addedDamage + fixedDamage + fixedDamageCrit;
		
		int fixedReduction = pe.getModel().getResFixed()[this.getType()];
		int fixedReductionCrit = pe.getModel().getCriticalResistance();
		
		int prcntReduction =  pe.getModel().getResPrcnt()[this.getType()] != 0 ? outputDamage / (100/pe.getModel().getResPrcnt()[this.getType()]) : 0;
		int prcntReductionCrit = pe.getModel().getResPrcnt()[this.getType()] != 0 ? outputDamageCrit / (100/pe.getModel().getResPrcnt()[this.getType()]) : 0;
		
		int finalDamage = outputDamage - fixedReduction - prcntReduction;
		int finalDamageCrit = outputDamageCrit - fixedReduction - fixedReductionCrit - prcntReductionCrit;
		
		/*
		Game.log.println("Casting spell : "+superSpell.getName());
		Game.log.println("Base damage : "+this.getLowDamage()+" - "+this.getCriticalHighDamage());
		Game.log.println("Chosen damage : "+baseDamage);
		Game.log.println("Added damage : "+addedDamage);
		Game.log.println("Fixed damage : "+fixedDamage);
		Game.log.println("Output damage : "+outputDamage);
		Game.log.println("Fixed reduction : "+fixedReduction);
		Game.log.println("Final damage : "+finalDamage);
		*/
		return (finalDamage+finalDamageCrit)/2;
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
