package ia.entities;

public class Stats {
	private String name;
	private int[] stats =                    {100, 100, 100, 100, 100};
	private int[] resPrcnt =                 {21, 22, 15, 12, 20}; // AIR, EARTH, WATER, FIRE, NEUTRAL
	private int[] resFixed =                 {5, 5, 5, 5, 5}; // AIR, EARTH, WATER, FIRE, NEUTRAL
	private int AP =                         12;
	private int baseAP =                     12;
	private int MP =                         6;
	private int baseMP =                     6;
	private int LP =                         200;
	private int maxLP =						 200;	
	private int baseLP =                     200;
	private int shield =					 200;
	
	private int initiative =                 2000;
	private int prospection =                120;
	private int range =                      0;
	private int summons =                    2;
	private boolean summoned = 				 false;
	
	private int APReduction =                20;
	private int MPReduction =                20;
	private int APLossReduction =            20;
	private int MPLossReduction =            20;
	private int criticalChance =             20;
	private int heals =                      8;
	private int dodge =                      20;
	private int lock =                       20;
	
	private int fixedDamages =               10;
	private int power =                      100;
	private int criticalDamage =             50;
	private int[] elementaryDamage =         {10, 10, 10, 10, 10};
	private int damageReturn =               0;
	private int weaponPower =                0;
	private int trapDamage =                 0;
	private int trapPower =                  0;
	private int pushbackDamage =             10;
	
	private int spellPowerPrcnt =            0;
	private int weaponPowerPrcnt =           0;
	private int distancePowerPrcnt =         0; 
	private int closeCombatPowerPrcnt =      0;
	
	private int criticalResistance =         10;
	private int pushbackResistance =         0;
	private int distanceResistancePrcnt =    0;
	private int closeCombatResistancePrcnt = 0;
	private int level = 0;
}
