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
	
	public static SpellObject[] getSpells(){
		initializeSpells();
		
		SpellObject[] spells = new SpellObject[6];
		spells[0] = magicArrow;
		spells[1] = retreatArrow;
		spells[2] = dispersingArrow;
		spells[3] = frozenArrow;
		spells[4] = burningArrow;
		spells[5] = distantShooting;
		
		CraModel.spells = spells;
		
		return spells;	
	}
	
	public static SpellObject getSpellFromName(String name) {
		if(spells == null) {
			getSpells();
		}
		
		for(int i = 0; i < spells.length; i++) {
			if(spells[i].getName().equals(name)) {
				return spells[i];
			}
		}
		
		return null;
	}
	
	public static final SpellObject magicArrow = new SpellObject("Magic arrow");
	public static final SpellObject concentrationArrow = new SpellObject("Concentration arrow");

	public static final SpellObject retreatArrow = new SpellObject("Retreat arrow");
	public static final SpellObject erosiveArrow = new SpellObject("Erosive arrow");
	
	public static final SpellObject dispersingArrow = new SpellObject("Dispersing arrow");
	public static final SpellObject reprisal = new SpellObject("Reprisal");
	
	public static final SpellObject frozenArrow = new SpellObject("Frozen arrow");
	public static final SpellObject paralyzingArrow = new SpellObject("Paralyzing arrow");
	
	public static final SpellObject burningArrow = new SpellObject("Burning arrow");
	public static final SpellObject repulsiveArrow = new SpellObject("Repulsive arrow");
	
	public static final SpellObject distantShooting = new SpellObject("Distant shooting");
	public static final SpellObject absoluteAcuteness = new SpellObject("Absolute acuteness");
	
	public static final SpellObject atonementArrow = new SpellObject("Atonement arrow");
	public static final SpellObject redemptionArrow = new SpellObject("Redemption arrow");
	
	public static final SpellObject batsEye = new SpellObject("Bat's eye");
	public static final SpellObject crushingArrow = new SpellObject("Crushing arrow");
	
	
	public static final SpellObject lashingArrow = new SpellObject("Lashing arrow");
	public static final SpellObject criticalShooting = new SpellObject("Critical shooting");
	public static final SpellObject paralysingArrow = new SpellObject("Paralysing arrow");
	public static final SpellObject punitiveArrow = new SpellObject("Punitive arrow");
	public static final SpellObject powerfulShooting = new SpellObject("Powerful shooting");
	public static final SpellObject plaguingArrow = new SpellObject("Plaguing arrow");
	public static final SpellObject poisonedArrow = new SpellObject("Poisoned arrow");
	public static final SpellObject tormentingArrow = new SpellObject("Tormenting arrow");
	public static final SpellObject destructiveArrow = new SpellObject("Destructive arrow");
	public static final SpellObject absorptiveArrow = new SpellObject("Absorptive arrow");
	public static final SpellObject slowDownArrow = new SpellObject("Slowdown arrow");
	public static final SpellObject explosiveArrow = new SpellObject("Explosive arrow");
	public static final SpellObject bowSkill = new SpellObject("Bow skill");
	public static final SpellObject craDopple = new SpellObject("Cra dopple");
	
}
