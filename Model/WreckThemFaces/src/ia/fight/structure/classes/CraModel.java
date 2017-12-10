package ia.fight.structure.classes;

import java.util.ArrayList;

import ia.fight.structure.Player;
import ia.fight.structure.spells.AreaOfEffect;
import ia.fight.structure.spells.SpellObject;
import ia.fight.structure.spells.Type;
import ia.fight.structure.spells.spelltypes.Buff;
import ia.fight.structure.spells.spelltypes.CharSteal;
import ia.fight.structure.spells.spelltypes.Damage;
import ia.fight.structure.spells.spelltypes.Pushback;
import ia.fight.structure.spells.spelltypes.Removal;

public class CraModel{
	
	static SpellObject[] spells;
	
	public static void initializeSpells(){
		magicArrow.clearSpells();
		magicArrow.addSpell(new Damage(Type.AIR, 19, 21, 22, 24, magicArrow));
		magicArrow.addSpell(new CharSteal("range", 2));
		magicArrow.setAreaOfEffect(new AreaOfEffect("cell", 1));
		magicArrow.setMinimumRange(1);
		magicArrow.setMaximumRange(12);
		magicArrow.setModifiableRange(true);
		magicArrow.setCriticalChance(15);
		magicArrow.setNumberOfCastsPerTarget(2);
		magicArrow.setMaxEffectAccumulation(2);
		magicArrow.setNumberOfCastsPerTurn(3);
		magicArrow.setCost(4);
		
		retreatArrow.clearSpells();
		retreatArrow.addSpell(new Pushback(4, retreatArrow));
		retreatArrow.addSpell(new Damage(Type.AIR, 25, 28, 29, 32, retreatArrow));
		retreatArrow.setAreaOfEffect(new AreaOfEffect("cell", 2));
		retreatArrow.setCost(3);
		retreatArrow.setMinimumRange(1);
		retreatArrow.setMaximumRange(8);
		retreatArrow.setCriticalChance(25);
		retreatArrow.setStraightLineCast(true);
		retreatArrow.setNumberOfCastsPerTurn(2);
		retreatArrow.setNumberOfCastsPerTarget(1);
		
		dispersingArrow.clearSpells();
		dispersingArrow.addSpell(new Pushback(2, dispersingArrow));
		dispersingArrow.setAreaOfEffect(new AreaOfEffect("centerless cross", 2));
		dispersingArrow.setCost(3);
		dispersingArrow.setMinimumRange(1);
		dispersingArrow.setMaximumRange(12);
		dispersingArrow.setRecastInterval(2);
		dispersingArrow.setModifiableRange(true);
		
		frozenArrow.clearSpells();
		frozenArrow.addSpell(new Damage(Type.FIRE, 12, 13, 15, 16, frozenArrow));
		frozenArrow.setAreaOfEffect(new AreaOfEffect("cell", 2));
		frozenArrow.addSpell(new Removal("AP", 2));
		frozenArrow.setCost(3);
		frozenArrow.setMinimumRange(3);
		frozenArrow.setMaximumRange(10);
		frozenArrow.setModifiableRange(true);
		frozenArrow.setNumberOfCastsPerTarget(2);
		frozenArrow.setCriticalChance(5);
		
		burningArrow.clearSpells();
		burningArrow.addSpell(new Damage(Type.FIRE, 33, 35, 39, 41, burningArrow));
		burningArrow.addSpell(new Pushback(1, burningArrow));
		burningArrow.setAreaOfEffect(new AreaOfEffect("line", 5));
		burningArrow.setCost(4);
		burningArrow.setMinimumRange(1);
		burningArrow.setMaximumRange(8);
		burningArrow.setCriticalChance(25);
		burningArrow.setStraightLineCast(true);
		burningArrow.setModifiableRange(true);
		burningArrow.setNumberOfCastsPerTurn(2);
		
		distantShooting.clearSpells();
		distantShooting.addSpell(new Buff("range", 6, 7, false, 5));
		distantShooting.setCost(3);
		distantShooting.setMinimumRange(0);
		distantShooting.setMaximumRange(0);
		distantShooting.setCriticalChance(29);
		distantShooting.setAreaOfEffect(new AreaOfEffect("circle", 3));
		distantShooting.setMaxEffectAccumulation(1);
		distantShooting.setRecastInterval(5);
		
		
		lashingArrow.clearSpells();
		lashingArrow.addSpell(new Buff("erosion", 10, 10, true, 2));
		lashingArrow.setAreaOfEffect(new AreaOfEffect("cell", 2));
		lashingArrow.setCost(2);
		lashingArrow.setMinimumRange(1);
		lashingArrow.setMaximumRange(4);
		lashingArrow.setModifiableRange(true);
		lashingArrow.setStraightLineCast(true);
		lashingArrow.setNumberOfCastsPerTarget(2);
		lashingArrow.setNumberOfCastsPerTurn(4);
		lashingArrow.setCriticalChance(-100);
		lashingArrow.setMaxEffectAccumulation(4);
	}
	
	public static SpellObject[] getSpells(int level){
		initializeSpells();
		
		ArrayList<SpellObject> allSpells = new ArrayList<>();
		
		allSpells.add(magicArrow);
		allSpells.add(concentrationArrow);
		allSpells.add(retreatArrow);
		allSpells.add(erosiveArrow);
		allSpells.add(dispersingArrow);
		allSpells.add(reprisal);
		allSpells.add(frozenArrow);
		allSpells.add(paralyzingArrow);
		allSpells.add(burningArrow);
		allSpells.add(repulsiveArrow);
		allSpells.add(distantShooting);
		allSpells.add(absoluteAcuteness);
		allSpells.add(atonementArrow);
		allSpells.add(redemptionArrow);
		allSpells.add(batsEye);
		allSpells.add(crushingArrow);
		allSpells.add(lashingArrow);
		allSpells.add(criticalShooting);
		allSpells.add(paralysingArrow);
		allSpells.add(punitiveArrow);
		allSpells.add(powerfulShooting);
		allSpells.add(plaguingArrow);
		allSpells.add(poisonedArrow);
		allSpells.add(tormentingArrow);
		allSpells.add(destructiveArrow);
		allSpells.add(absorptiveArrow);
		allSpells.add(slowDownArrow);
		allSpells.add(explosiveArrow);
		allSpells.add(bowSkill);
		allSpells.add(craDopple);
		
		int counter = 0;
		
		for(int i = 0; i < allSpells.size(); i++) {
			if(allSpells.get(i).level <= level) {
				counter ++;
			}
		}
		
		SpellObject[] spells = new SpellObject[counter];
		for(int i = 0; i < counter; i++) {
			spells[i] = allSpells.get(i);
		}
		
		CraModel.spells = spells;
		return spells;	
	}
	
	public static SpellObject getSpellFromName(String name) {
		if(spells == null) {
			getSpells(1001);
		}
		
		for(int i = 0; i < spells.length; i++) {
			if(spells[i].getName().equals(name)) {
				return spells[i];
			}
		}
		
		return null;
	}
	
	public static final SpellObject magicArrow = new SpellObject("Magic arrow", 1);
	public static final SpellObject concentrationArrow = new SpellObject("Concentration arrow", 101);

	public static final SpellObject retreatArrow = new SpellObject("Retreat arrow", 1);
	public static final SpellObject erosiveArrow = new SpellObject("Erosive arrow", 105);
	
	public static final SpellObject dispersingArrow = new SpellObject("Dispersing arrow", 1);
	public static final SpellObject reprisal = new SpellObject("Reprisal", 110);
	
	public static final SpellObject frozenArrow = new SpellObject("Frozen arrow", 3);
	public static final SpellObject paralyzingArrow = new SpellObject("Paralyzing arrow", 115);
	
	public static final SpellObject burningArrow = new SpellObject("Burning arrow", 6);
	public static final SpellObject repulsiveArrow = new SpellObject("Repulsive arrow", 120);
	
	public static final SpellObject distantShooting = new SpellObject("Distant shooting", 9);
	public static final SpellObject absoluteAcuteness = new SpellObject("Absolute acuteness", 125);
	
	public static final SpellObject atonementArrow = new SpellObject("Atonement arrow", 13);
	public static final SpellObject redemptionArrow = new SpellObject("Redemption arrow", 130);
	
	public static final SpellObject batsEye = new SpellObject("Bat's eye", 17);
	public static final SpellObject crushingArrow = new SpellObject("Crushing arrow", 135);
	
	public static final SpellObject lashingArrow = new SpellObject("Lashing arrow", 1000);
	public static final SpellObject criticalShooting = new SpellObject("Critical shooting", 1000);
	public static final SpellObject paralysingArrow = new SpellObject("Paralysing arrow", 1000);
	public static final SpellObject punitiveArrow = new SpellObject("Punitive arrow", 1000);
	public static final SpellObject powerfulShooting = new SpellObject("Powerful shooting", 1000);
	public static final SpellObject plaguingArrow = new SpellObject("Plaguing arrow", 1000);
	public static final SpellObject poisonedArrow = new SpellObject("Poisoned arrow", 1000);
	public static final SpellObject tormentingArrow = new SpellObject("Tormenting arrow", 1000);
	public static final SpellObject destructiveArrow = new SpellObject("Destructive arrow", 1000);
	public static final SpellObject absorptiveArrow = new SpellObject("Absorptive arrow", 1000);
	public static final SpellObject slowDownArrow = new SpellObject("Slowdown arrow", 1000);
	public static final SpellObject explosiveArrow = new SpellObject("Explosive arrow", 1000);
	public static final SpellObject bowSkill = new SpellObject("Bow skill", 1000);
	public static final SpellObject craDopple = new SpellObject("Cra dopple", 1000);
	
}
