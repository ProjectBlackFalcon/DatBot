package ia.fight.structure.classes;

import java.util.ArrayList;

import ia.fight.structure.Player;
import ia.fight.structure.spells.AreaOfEffect;
import ia.fight.structure.spells.SpellObject;
import ia.fight.structure.spells.Type;
import ia.fight.structure.spells.spelltypes.Buff;
import ia.fight.structure.spells.spelltypes.CharSteal;
import ia.fight.structure.spells.spelltypes.Damage;
import ia.fight.structure.spells.spelltypes.LifeSteal;
import ia.fight.structure.spells.spelltypes.Pushback;
import ia.fight.structure.spells.spelltypes.Removal;
import ia.fight.structure.spells.spelltypes.StateApply;

public class CraModel{
	
	static SpellObject[] spells;
	
	public static void initializeSpells(){
		magicArrow.clearSpells();
		magicArrow.addSpell(new Damage(Type.AIR, 19, 21, 22, 24, magicArrow));
		magicArrow.addSpell(new CharSteal("range", 2));
		magicArrow.setAreaOfEffect(new AreaOfEffect("cell", 1));
		magicArrow.setMinimumRange(1);
		magicArrow.setMaximumRange(8);
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
		retreatArrow.setMaximumRange(4);
		retreatArrow.setCriticalChance(25);
		retreatArrow.setStraightLineCast(true);
		retreatArrow.setNumberOfCastsPerTurn(2);
		retreatArrow.setNumberOfCastsPerTarget(1);
		
		dispersingArrow.clearSpells();
		dispersingArrow.addSpell(new Pushback(2, dispersingArrow));
		dispersingArrow.setAreaOfEffect(new AreaOfEffect("centerless cross", 2));
		dispersingArrow.setCost(3);
		dispersingArrow.setMinimumRange(1);
		dispersingArrow.setMaximumRange(6);
		dispersingArrow.setRecastInterval(2);
		dispersingArrow.setModifiableRange(true);
		
		frozenArrow.clearSpells();
		frozenArrow.addSpell(new Damage(Type.FIRE, 12, 13, 15, 16, frozenArrow));
		frozenArrow.setAreaOfEffect(new AreaOfEffect("cell", 2));
		frozenArrow.addSpell(new Removal("AP", 2));
		frozenArrow.setCost(3);
		frozenArrow.setMinimumRange(3);
		frozenArrow.setMaximumRange(6);
		frozenArrow.setModifiableRange(true);
		frozenArrow.setNumberOfCastsPerTarget(2);
		frozenArrow.setCriticalChance(5);
		
		burningArrow.clearSpells();
		burningArrow.addSpell(new Damage(Type.FIRE, 33, 35, 39, 41, burningArrow));
		burningArrow.addSpell(new Pushback(1, burningArrow));
		burningArrow.setAreaOfEffect(new AreaOfEffect("line", 5));
		burningArrow.setCost(4);
		burningArrow.setMinimumRange(1);
		burningArrow.setMaximumRange(4);
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
		
		atonementArrow.clearSpells();
		atonementArrow.addSpell(new Damage(Type.WATER, 19, 21, 25, 27, atonementArrow));
		atonementArrow.addSpell(new StateApply("gravity", "target"));
		atonementArrow.addSpell(new Buff("atonement", 22, 28, true, 1));
		atonementArrow.setCost(4);
		atonementArrow.setMinimumRange(8);
		atonementArrow.setMaximumRange(10);
		atonementArrow.setModifiableRange(true);
		atonementArrow.setCriticalChance(25);
		atonementArrow.setRecastInterval(3);
		
		batsEye.clearSpells();
		batsEye.setCost(3);
		batsEye.setMinimumRange(5);
		batsEye.setMaximumRange(6);
		batsEye.setCriticalChance(25);
		batsEye.setAreaOfEffect(new AreaOfEffect("circle", 2));
		batsEye.setMaxEffectAccumulation(1);
		batsEye.setRecastInterval(4);
		batsEye.addSpell(new LifeSteal(Type.WATER, 8, 10, 11, 13));
		batsEye.addSpell(new Buff("-range", 2, 2, true, 3));
		
		criticalShooting.clearSpells();
		criticalShooting.addSpell(new Buff("critical", 10, 10, true, 4));
		criticalShooting.setCost(2);
		criticalShooting.setMinimumRange(0);
		criticalShooting.setMaximumRange(2);
		criticalShooting.setModifiableRange(true);
		criticalShooting.setCriticalChance(25);
		criticalShooting.setMaxEffectAccumulation(1);
		criticalShooting.setRecastInterval(5);
		
		paralysingArrow.clearSpells();
		paralysingArrow.setCost(2);
		paralysingArrow.setMinimumRange(1);
		paralysingArrow.setMaximumRange(6);
		paralysingArrow.setModifiableRange(true);
		paralysingArrow.setNumberOfCastsPerTarget(2);
		paralysingArrow.setNumberOfCastsPerTurn(4);
		paralysingArrow.addSpell(new Damage(Type.WATER, 6, 7, 7, 8, paralysingArrow));
		paralysingArrow.addSpell(new CharSteal("MP", 1));
		
		punitiveArrow.clearSpells();
		
		powerfulShooting.clearSpells();
		
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
		allSpells.add(paralysingArrow);
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
		
		int counter = 0;
		
		ArrayList<SpellObject> availableSpellsTemp = new ArrayList<>();
		
		for(int i = 0; i < allSpells.size(); i++) {
			if(allSpells.get(i).level <= level) {
				availableSpellsTemp.add(allSpells.get(i));
			}
		}
		
		SpellObject[] spells = new SpellObject[availableSpellsTemp.size()];
		for(int i = 0; i < availableSpellsTemp.size(); i++) {
			spells[i] = availableSpellsTemp.get(i);
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
	
	public static SpellObject getSpellFromID(int id) {
		getSpells(1001);
		
		if(spells == null) {
			getSpells(1001);
		}
		
		for(int i = 0; i < spells.length; i++) {
			if(spells[i].getID() == id) {
				return spells[i];
			}
		}
		
		return magicArrow;
	}
	
	public static final SpellObject magicArrow = new SpellObject("Magic arrow", 1, 161);
	public static final SpellObject concentrationArrow = new SpellObject("Concentration arrow", 101, 9301);

	public static final SpellObject retreatArrow = new SpellObject("Retreat arrow", 1, 169);
	public static final SpellObject erosiveArrow = new SpellObject("Erosive arrow", 105, 9302);
	
	public static final SpellObject dispersingArrow = new SpellObject("Dispersing arrow", 1, 418);
	public static final SpellObject reprisal = new SpellObject("Reprisal", 110, 9303);
	
	public static final SpellObject frozenArrow = new SpellObject("Frozen arrow", 3, 163);
	public static final SpellObject paralyzingArrow115 = new SpellObject("Paralyzing arrow115", 115, 9304);
	
	public static final SpellObject burningArrow = new SpellObject("Burning arrow", 6, 165);
	public static final SpellObject repulsiveArrow = new SpellObject("Repulsive arrow", 120, 9305);
	
	public static final SpellObject distantShooting = new SpellObject("Distant shooting", 9, 172);
	public static final SpellObject absoluteAcuteness = new SpellObject("Absolute acuteness", 125, 9306);
	
	public static final SpellObject atonementArrow = new SpellObject("Atonement arrow", 13, 167);
	public static final SpellObject redemptionArrow = new SpellObject("Redemption arrow", 130, 9307);
	
	public static final SpellObject batsEye = new SpellObject("Bat's eye", 17, 168);
	public static final SpellObject crushingArrow = new SpellObject("Crushing arrow", 135, 9308);
	
	public static final SpellObject criticalShooting = new SpellObject("Critical shooting", 22, 162);
	
	public static final SpellObject paralysingArrow = new SpellObject("Paralysing arrow", 27, 170);
	
	public static final SpellObject punitiveArrow = new SpellObject("Punitive arrow", 32, 171);
	
	public static final SpellObject powerfulShooting = new SpellObject("Powerful shooting", 38, 166);
	
	public static final SpellObject plaguingArrow = new SpellObject("Plaguing arrow", 44, 173);
	
	public static final SpellObject poisonedArrow = new SpellObject("Poisoned arrow", 50, 174);
	
	public static final SpellObject tormentingArrow = new SpellObject("Tormenting arrow", 56, 176);
	
	public static final SpellObject destructiveArrow = new SpellObject("Destructive arrow", 62, 175);

	public static final SpellObject absorptiveArrow = new SpellObject("Absorptive arrow", 69, 178);
	
	public static final SpellObject lashingArrow = new SpellObject("Lashing arrow", 77, 164);
	
	public static final SpellObject slowDownArrow = new SpellObject("Slowdown arrow", 84, 177);
	
	public static final SpellObject explosiveArrow = new SpellObject("Explosive arrow", 98, 179);
	
	public static final SpellObject bowSkill = new SpellObject("Bow skill", 100, 180);
	
}
