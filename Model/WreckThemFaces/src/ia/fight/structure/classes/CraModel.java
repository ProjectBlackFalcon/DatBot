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
		dispersingArrow.clearSpells();
		dispersingArrow.addSpell(new Pushback(2, dispersingArrow));
		dispersingArrow.setAreaOfEffect(new AreaOfEffect("centerless cross", 2));
		dispersingArrow.setCost(3);
		dispersingArrow.setMinimumRange(1);
		dispersingArrow.setMaximumRange(12);
		dispersingArrow.setRecastInterval(2);
		
		magicArrow.clearSpells();
		magicArrow.addSpell(new Damage(Type.FIRE, 15, 17, 17, 19, magicArrow));
		magicArrow.addSpell(new CharSteal("range", 2));
		magicArrow.setAreaOfEffect(new AreaOfEffect("cell", 1));
		magicArrow.setMinimumRange(1);
		magicArrow.setMaximumRange(7);
		magicArrow.setModifiableRange(true);
		magicArrow.setCriticalChance(15);
		magicArrow.setNumberOfCastsPerTarget(1);
		magicArrow.setMaxEffectAccumulation(2);
		
		lashingArrow.clearSpells();
		lashingArrow.addSpell(new Buff("erosion", 10, true, 2));
		lashingArrow.setAreaOfEffect(new AreaOfEffect("cell", 2));
		lashingArrow.setCost(2);
		lashingArrow.setMinimumRange(1);
		lashingArrow.setMaximumRange(4);
		lashingArrow.setModifiableRange(true);
		lashingArrow.setStraightLineCast(true);
		lashingArrow.setNumberOfCastsPerTarget(2);
		lashingArrow.setNumberOfCastsPerTurn(4);
		lashingArrow.setMaxEffectAccumulation(4);
		
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
		
		frozenArrow.clearSpells();
		frozenArrow.addSpell(new Damage(Type.FIRE, 12, 13, 15, 16, frozenArrow));
		frozenArrow.setAreaOfEffect(new AreaOfEffect("cell", 2));
		frozenArrow.addSpell(new Removal("AP", 2));
		frozenArrow.setCost(3);
		frozenArrow.setMinimumRange(3);
		frozenArrow.setMaximumRange(10);
		frozenArrow.setModifiableRange(true);
		frozenArrow.setNumberOfCastsPerTarget(2);
		frozenArrow.setCriticalChance(9);;
		
		burningArrow.clearSpells();
		burningArrow.addSpell(new Damage(Type.FIRE, 33, 35, 39, 41, burningArrow));
		burningArrow.addSpell(new Pushback(1, burningArrow));
		burningArrow.setAreaOfEffect(new AreaOfEffect("line", 5));
		burningArrow.setCost(4);
		burningArrow.setMinimumRange(1);
		burningArrow.setMaximumRange(8);
		burningArrow.setCriticalChance(29);
		burningArrow.setStraightLineCast(true);
		burningArrow.setModifiableRange(true);
		burningArrow.setNumberOfCastsPerTurn(2);
		
		distantShooting.clearSpells();
		distantShooting.addSpell(new Buff("range", 6, false, 5));
		distantShooting.setCost(3);
		distantShooting.setMinimumRange(0);
		distantShooting.setMaximumRange(0);
		distantShooting.setCriticalChance(29);
		distantShooting.setAreaOfEffect(new AreaOfEffect("circle", 3));
		distantShooting.setMaxEffectAccumulation(1);
		distantShooting.setRecastInterval(5);
		
	}
	
	public static SpellObject[] getSpells(){
		initializeSpells();
		
		SpellObject[] spells = new SpellObject[7];
		spells[0] = dispersingArrow;
		spells[1] = magicArrow;
		spells[2] = lashingArrow;
		spells[3] = retreatArrow;
		spells[4] = frozenArrow;
		spells[5] = burningArrow;
		spells[6] = distantShooting;
		
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

	public static final SpellObject dispersingArrow = new SpellObject("Dispersing arrow");
	public static final SpellObject magicArrow = new SpellObject("Magic arrow");
	public static final SpellObject lashingArrow = new SpellObject("Lashing arrow");
	public static final SpellObject retreatArrow = new SpellObject("Retreat arrow");
	public static final SpellObject frozenArrow = new SpellObject("Frozen arrow");
	public static final SpellObject burningArrow = new SpellObject("Burning arrow");
	public static final SpellObject distantShooting = new SpellObject("Distant shooting");
	public static final SpellObject atonementArrow = new SpellObject("Atonement arrow");
	public static final SpellObject batsEye = new SpellObject("Bat's eye");
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
