package ia.fight.structure;

import java.util.ArrayList;
import java.util.Collections;

import ia.fight.structure.spells.SpellObject;
import ia.fight.structure.spells.spelltypes.Buff;


public abstract class Player {

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
	
	private ArrayList<Buff> buffs;
	
	private static SpellObject[] spells;
	
	public Player(String name, int baseLP, int baseAP, int baseMP, int level) {
		buffs = new ArrayList<>();
		this.name = name;
		this.baseLP = baseLP;
		this.baseAP = baseAP;
		this.baseMP = baseMP;
		this.maxLP = baseLP;
		this.LP = baseLP;
		this.AP = baseAP;
		this.MP = baseMP;
		this.level = level;
	}
	
	public SpellObject[] getSpells(){
		return spells;
	}
	
	public int getLevel()
	{
		return level;
	}

	public ArrayList<SpellObject> getAvailableSpells(){
		ArrayList<SpellObject> spells = new ArrayList<>();
		
		for(int i = 0; i < this.getSpells().length; i++){
			if(this.getSpells()[i].getCost() <= this.AP && this.getSpells()[i].isAvailable()){
				spells.add(this.getSpells()[i]);
			}
		}
		
		return spells;
	}
	
	public void addBuff(Buff buff){
		this.buffs.add(buff);
	}
	
	public void removeOneBuffTurn(){
		//Game.log.println("REMOVING OBT");
		ArrayList<Integer> buffsToRemove = new ArrayList<>();
		for(int i = 0; i < this.buffs.size(); i++){
			this.buffs.get(i).removeTurnDurationQuantity(1);
			if(this.buffs.get(i).getTurnDuration() <= 0){
				buffsToRemove.add(i);
			}
		}
		
		Collections.sort(buffsToRemove);
		
		for(int i = buffsToRemove.size()-1; i >= 0; i--){
			this.buffs.remove(buffsToRemove.get(i).intValue());
		}
	}
	
	public void updateSpellsStatus() {
		for(int i = 0; i < this.getSpells().length; i++) {
			this.getSpells()[i].passTurn();
		}
	}
	
	public ArrayList<Buff> getBuffs() {
		return buffs;
		
	}

	public void setBuffs(ArrayList<Buff> buffs) {
		this.buffs = buffs;
	}

	public void removeAP(int AP){
		this.AP -= AP;
	}
	
	public void removeMP(int MP){
		this.MP -= MP;
	}
	
	public void removeLP(int LP){
		this.LP -= LP;
	}
	
	public void addAP(int AP){
		this.AP += AP;
	}
	
	public void addMP(int MP){
		this.MP += MP;
	}
	
	public void addLP(int LP){
		this.LP += LP;
	}
	
	public void resetAP(){
		this.AP = this.baseAP;
	}
	
	public void resetMP(){
		this.MP = this.baseMP;
	}
	
	public int getBaseAP() {
		return baseAP;
	}

	public void setBaseAP(int baseAP) {
		this.baseAP = baseAP;
	}

	public int getBaseMP() {
		return baseMP;
	}

	public void setBaseMP(int baseMP) {
		this.baseMP = baseMP;
	}

	public int getBaseLP() {
		return baseLP;
	}

	public void setBaseLP(int baseLP) {
		this.baseLP = baseLP;
	}

	public int getLP() {
		return LP;
	}

	public void setLP(int lP) {
		LP = lP;
	}

	public void setSpells(SpellObject[] spells) {
		this.spells = spells;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int[] getStats() {
		return stats;
	}
	public void setStats(int[] stats) {
		this.stats = stats;
	}
	public int[] getResPrcnt() {
		return resPrcnt;
	}
	public void setResPrcnt(int[] resPrcnt) {
		this.resPrcnt = resPrcnt;
	}
	public int[] getResFixed() {
		return resFixed;
	}
	public void setResFixed(int[] resFixed) {
		this.resFixed = resFixed;
	}
	public int getAP() {
		return AP;
	}
	public void setAP(int aP) {
		AP = aP;
	}
	public int getMP() {
		return MP;
	}
	public void setMP(int mP) {
		MP = mP;
	}
	public int getInitiative() {
		return initiative;
	}
	public void setInitiative(int initiative) {
		this.initiative = initiative;
	}
	public int getProspection() {
		return prospection;
	}
	public void setProspection(int prospection) {
		this.prospection = prospection;
	}
	public int getRange() {
		return range;
	}
	public void setRange(int range) {
		this.range = range;
	}
	public int getSummons() {
		return summons;
	}
	public void setSummons(int summons) {
		this.summons = summons;
	}
	public int getAPReduction() {
		return APReduction;
	}
	public void setAPReduction(int aPReduction) {
		APReduction = aPReduction;
	}
	public int getMPReduction() {
		return MPReduction;
	}
	public void setMPReduction(int mPReduction) {
		MPReduction = mPReduction;
	}
	public int getAPLossReduction() {
		return APLossReduction;
	}
	public void setAPLossReduction(int aPLossReduction) {
		APLossReduction = aPLossReduction;
	}
	public int getMPLossReduction() {
		return MPLossReduction;
	}
	public void setMPLossReduction(int mPLossReduction) {
		MPLossReduction = mPLossReduction;
	}
	public int getCriticalChance() {
		return criticalChance;
	}
	public void setCriticalChance(int criticalChance) {
		this.criticalChance = criticalChance;
	}
	public int getHeals() {
		return heals;
	}
	public void setHeals(int heals) {
		this.heals = heals;
	}
	public int getDodge() {
		return dodge;
	}
	public void setDodge(int dodge) {
		this.dodge = dodge;
	}
	public int getLock() {
		return lock;
	}
	public void setLock(int lock) {
		this.lock = lock;
	}
	public int getFixedDamages() {
		return fixedDamages;
	}
	public void setFixedDamages(int fixedDamages) {
		this.fixedDamages = fixedDamages;
	}
	public int getPower() {
		return power;
	}
	public void setPower(int power) {
		this.power = power;
	}
	public int getCriticalDamage() {
		return criticalDamage;
	}
	public void setCriticalDamage(int criticalDamage) {
		this.criticalDamage = criticalDamage;
	}
	public int[] getElementaryDamage() {
		return elementaryDamage;
	}
	public void setElementaryDamage(int[] elementaryDamage) {
		this.elementaryDamage = elementaryDamage;
	}
	public int getDamageReturn() {
		return damageReturn;
	}
	public void setDamageReturn(int damageReturn) {
		this.damageReturn = damageReturn;
	}
	public int getWeaponPower() {
		return weaponPower;
	}
	public void setWeaponPower(int weaponPower) {
		this.weaponPower = weaponPower;
	}
	public int getTrapDamage() {
		return trapDamage;
	}
	public void setTrapDamage(int trapDamage) {
		this.trapDamage = trapDamage;
	}
	public int getTrapPower() {
		return trapPower;
	}
	public void setTrapPower(int trapPower) {
		this.trapPower = trapPower;
	}
	public int getPushbackDamage() {
		return pushbackDamage;
	}
	public void setPushbackDamage(int pushbackDamage) {
		this.pushbackDamage = pushbackDamage;
	}
	public int getSpellPowerPrcnt() {
		return spellPowerPrcnt;
	}
	public void setSpellPowerPrcnt(int spellPowerPrcnt) {
		this.spellPowerPrcnt = spellPowerPrcnt;
	}
	public int getWeaponPowerPrcnt() {
		return weaponPowerPrcnt;
	}
	public void setWeaponPowerPrcnt(int weaponPowerPrcnt) {
		this.weaponPowerPrcnt = weaponPowerPrcnt;
	}
	public int getDistancePowerPrcnt() {
		return distancePowerPrcnt;
	}
	public void setDistancePowerPrcnt(int distancePowerPrcnt) {
		this.distancePowerPrcnt = distancePowerPrcnt;
	}
	public int getCloseCombatPowerPrcnt() {
		return closeCombatPowerPrcnt;
	}
	public void setCloseCombatPowerPrcnt(int closeCombatPowerPrcnt) {
		this.closeCombatPowerPrcnt = closeCombatPowerPrcnt;
	}
	public int getCriticalResistance() {
		return criticalResistance;
	}
	public void setCriticalResistance(int criticalResistance) {
		this.criticalResistance = criticalResistance;
	}
	public int getPushbackResistance() {
		return pushbackResistance;
	}
	public void setPushbackResistance(int pushbackResistance) {
		this.pushbackResistance = pushbackResistance;
	}
	public int getDistanceResistancePrcnt() {
		return distanceResistancePrcnt;
	}
	public void setDistanceResistancePrcnt(int distanceResistancePrcnt) {
		this.distanceResistancePrcnt = distanceResistancePrcnt;
	}
	public int getCloseCombatResistancePrcnt() {
		return closeCombatResistancePrcnt;
	}
	public void setCloseCombatResistancePrcnt(int closeCombatResistancePrcnt) {
		this.closeCombatResistancePrcnt = closeCombatResistancePrcnt;
	}
	public int getMaxLP(){
		return maxLP;
	}
	public void setMaxLP(int maxLP){
		this.maxLP = maxLP;
	}
	public int getShield()	{
		return shield;
	}
	public void setShield(int shield)	{
		this.shield = shield;
	}
	public boolean isSummoned()	{
		return summoned;
	}
	public void setSummoned(boolean summoned)	{
		this.summoned = summoned;
	}
	
	public String toString() {
		String s = this.getClass().getSimpleName()+" entity, "+this.getMaxLP()+"/"+this.getBaseAP()+"/"+this.getBaseMP() +"\n";
		s += "Fixed Resistances : [";
		for(int i = 0; i < this.getResFixed().length; i++) {
			s+= this.getResFixed()[i];
			if(i != this.getResFixed().length-1) {
				s+=",";
			}
		}
		s+="]\n";

		s += "% Resistances : [";
		for(int i = 0; i < this.getResPrcnt().length; i++) {
			s+= this.getResPrcnt()[i];
			if(i != this.getResPrcnt().length-1) {
				s+=",";
			}
		}
		s+="]\n";
		
		s += "Stats : [";
		for(int i = 0; i < this.getStats().length; i++) {
			s+= this.getStats()[i];
			if(i != this.getStats().length-1) {
				s+=",";
			}
		}
		s+="]\n";
		
		return s;
	}

	public abstract String getType();




}
	