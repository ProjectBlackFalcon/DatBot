package protocol.network.types.game.character.characteristic;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.NetworkMessage;
import protocol.network.types.game.character.alignment.ActorExtendedAlignmentInformations;
import protocol.network.types.game.character.characteristic.CharacterBaseCharacteristic;
import protocol.network.types.game.character.characteristic.CharacterSpellModification;

@SuppressWarnings("unused")
public class CharacterCharacteristicsInformations extends NetworkMessage {
	public static final int ProtocolId = 8;

	private long experience;
	private long experienceLevelFloor;
	private long experienceNextLevelFloor;
	private long experienceBonusLimit;
	private long kamas;
	private int statsPoints;
	private int additionnalPoints;
	private int spellsPoints;
	private ActorExtendedAlignmentInformations alignmentInfos;
	private int lifePoints;
	private int maxLifePoints;
	private int energyPoints;
	private int maxEnergyPoints;
	private int actionPointsCurrent;
	private int movementPointsCurrent;
	private CharacterBaseCharacteristic initiative;
	private CharacterBaseCharacteristic prospecting;
	private CharacterBaseCharacteristic actionPoints;
	private CharacterBaseCharacteristic movementPoints;
	private CharacterBaseCharacteristic strength;
	private CharacterBaseCharacteristic vitality;
	private CharacterBaseCharacteristic wisdom;
	private CharacterBaseCharacteristic chance;
	private CharacterBaseCharacteristic agility;
	private CharacterBaseCharacteristic intelligence;
	private CharacterBaseCharacteristic range;
	private CharacterBaseCharacteristic summonableCreaturesBoost;
	private CharacterBaseCharacteristic reflect;
	private CharacterBaseCharacteristic criticalHit;
	private int criticalHitWeapon;
	private CharacterBaseCharacteristic criticalMiss;
	private CharacterBaseCharacteristic healBonus;
	private CharacterBaseCharacteristic allDamagesBonus;
	private CharacterBaseCharacteristic weaponDamagesBonusPercent;
	private CharacterBaseCharacteristic damagesBonusPercent;
	private CharacterBaseCharacteristic trapBonus;
	private CharacterBaseCharacteristic trapBonusPercent;
	private CharacterBaseCharacteristic glyphBonusPercent;
	private CharacterBaseCharacteristic runeBonusPercent;
	private CharacterBaseCharacteristic permanentDamagePercent;
	private CharacterBaseCharacteristic tackleBlock;
	private CharacterBaseCharacteristic tackleEvade;
	private CharacterBaseCharacteristic PAAttack;
	private CharacterBaseCharacteristic PMAttack;
	private CharacterBaseCharacteristic pushDamageBonus;
	private CharacterBaseCharacteristic criticalDamageBonus;
	private CharacterBaseCharacteristic neutralDamageBonus;
	private CharacterBaseCharacteristic earthDamageBonus;
	private CharacterBaseCharacteristic waterDamageBonus;
	private CharacterBaseCharacteristic airDamageBonus;
	private CharacterBaseCharacteristic fireDamageBonus;
	private CharacterBaseCharacteristic dodgePALostProbability;
	private CharacterBaseCharacteristic dodgePMLostProbability;
	private CharacterBaseCharacteristic neutralElementResistPercent;
	private CharacterBaseCharacteristic earthElementResistPercent;
	private CharacterBaseCharacteristic waterElementResistPercent;
	private CharacterBaseCharacteristic airElementResistPercent;
	private CharacterBaseCharacteristic fireElementResistPercent;
	private CharacterBaseCharacteristic neutralElementReduction;
	private CharacterBaseCharacteristic earthElementReduction;
	private CharacterBaseCharacteristic waterElementReduction;
	private CharacterBaseCharacteristic airElementReduction;
	private CharacterBaseCharacteristic fireElementReduction;
	private CharacterBaseCharacteristic pushDamageReduction;
	private CharacterBaseCharacteristic criticalDamageReduction;
	private CharacterBaseCharacteristic pvpNeutralElementResistPercent;
	private CharacterBaseCharacteristic pvpEarthElementResistPercent;
	private CharacterBaseCharacteristic pvpWaterElementResistPercent;
	private CharacterBaseCharacteristic pvpAirElementResistPercent;
	private CharacterBaseCharacteristic pvpFireElementResistPercent;
	private CharacterBaseCharacteristic pvpNeutralElementReduction;
	private CharacterBaseCharacteristic pvpEarthElementReduction;
	private CharacterBaseCharacteristic pvpWaterElementReduction;
	private CharacterBaseCharacteristic pvpAirElementReduction;
	private CharacterBaseCharacteristic pvpFireElementReduction;
	private CharacterBaseCharacteristic meleeDamageDonePercent;
	private CharacterBaseCharacteristic meleeDamageReceivedPercent;
	private CharacterBaseCharacteristic rangedDamageDonePercent;
	private CharacterBaseCharacteristic rangedDamageReceivedPercent;
	private CharacterBaseCharacteristic weaponDamageDonePercent;
	private CharacterBaseCharacteristic weaponDamageReceivedPercent;
	private CharacterBaseCharacteristic spellDamageDonePercent;
	private CharacterBaseCharacteristic spellDamageReceivedPercent;
	private List<CharacterSpellModification> spellModifications;
	private int probationTime;

	public long getExperience() { return this.experience; }
	public void setExperience(long experience) { this.experience = experience; };
	public long getExperienceLevelFloor() { return this.experienceLevelFloor; }
	public void setExperienceLevelFloor(long experienceLevelFloor) { this.experienceLevelFloor = experienceLevelFloor; };
	public long getExperienceNextLevelFloor() { return this.experienceNextLevelFloor; }
	public void setExperienceNextLevelFloor(long experienceNextLevelFloor) { this.experienceNextLevelFloor = experienceNextLevelFloor; };
	public long getExperienceBonusLimit() { return this.experienceBonusLimit; }
	public void setExperienceBonusLimit(long experienceBonusLimit) { this.experienceBonusLimit = experienceBonusLimit; };
	public long getKamas() { return this.kamas; }
	public void setKamas(long kamas) { this.kamas = kamas; };
	public int getStatsPoints() { return this.statsPoints; }
	public void setStatsPoints(int statsPoints) { this.statsPoints = statsPoints; };
	public int getAdditionnalPoints() { return this.additionnalPoints; }
	public void setAdditionnalPoints(int additionnalPoints) { this.additionnalPoints = additionnalPoints; };
	public int getSpellsPoints() { return this.spellsPoints; }
	public void setSpellsPoints(int spellsPoints) { this.spellsPoints = spellsPoints; };
	public ActorExtendedAlignmentInformations getAlignmentInfos() { return this.alignmentInfos; }
	public void setAlignmentInfos(ActorExtendedAlignmentInformations alignmentInfos) { this.alignmentInfos = alignmentInfos; };
	public int getLifePoints() { return this.lifePoints; }
	public void setLifePoints(int lifePoints) { this.lifePoints = lifePoints; };
	public int getMaxLifePoints() { return this.maxLifePoints; }
	public void setMaxLifePoints(int maxLifePoints) { this.maxLifePoints = maxLifePoints; };
	public int getEnergyPoints() { return this.energyPoints; }
	public void setEnergyPoints(int energyPoints) { this.energyPoints = energyPoints; };
	public int getMaxEnergyPoints() { return this.maxEnergyPoints; }
	public void setMaxEnergyPoints(int maxEnergyPoints) { this.maxEnergyPoints = maxEnergyPoints; };
	public int getActionPointsCurrent() { return this.actionPointsCurrent; }
	public void setActionPointsCurrent(int actionPointsCurrent) { this.actionPointsCurrent = actionPointsCurrent; };
	public int getMovementPointsCurrent() { return this.movementPointsCurrent; }
	public void setMovementPointsCurrent(int movementPointsCurrent) { this.movementPointsCurrent = movementPointsCurrent; };
	public CharacterBaseCharacteristic getInitiative() { return this.initiative; }
	public void setInitiative(CharacterBaseCharacteristic initiative) { this.initiative = initiative; };
	public CharacterBaseCharacteristic getProspecting() { return this.prospecting; }
	public void setProspecting(CharacterBaseCharacteristic prospecting) { this.prospecting = prospecting; };
	public CharacterBaseCharacteristic getActionPoints() { return this.actionPoints; }
	public void setActionPoints(CharacterBaseCharacteristic actionPoints) { this.actionPoints = actionPoints; };
	public CharacterBaseCharacteristic getMovementPoints() { return this.movementPoints; }
	public void setMovementPoints(CharacterBaseCharacteristic movementPoints) { this.movementPoints = movementPoints; };
	public CharacterBaseCharacteristic getStrength() { return this.strength; }
	public void setStrength(CharacterBaseCharacteristic strength) { this.strength = strength; };
	public CharacterBaseCharacteristic getVitality() { return this.vitality; }
	public void setVitality(CharacterBaseCharacteristic vitality) { this.vitality = vitality; };
	public CharacterBaseCharacteristic getWisdom() { return this.wisdom; }
	public void setWisdom(CharacterBaseCharacteristic wisdom) { this.wisdom = wisdom; };
	public CharacterBaseCharacteristic getChance() { return this.chance; }
	public void setChance(CharacterBaseCharacteristic chance) { this.chance = chance; };
	public CharacterBaseCharacteristic getAgility() { return this.agility; }
	public void setAgility(CharacterBaseCharacteristic agility) { this.agility = agility; };
	public CharacterBaseCharacteristic getIntelligence() { return this.intelligence; }
	public void setIntelligence(CharacterBaseCharacteristic intelligence) { this.intelligence = intelligence; };
	public CharacterBaseCharacteristic getRange() { return this.range; }
	public void setRange(CharacterBaseCharacteristic range) { this.range = range; };
	public CharacterBaseCharacteristic getSummonableCreaturesBoost() { return this.summonableCreaturesBoost; }
	public void setSummonableCreaturesBoost(CharacterBaseCharacteristic summonableCreaturesBoost) { this.summonableCreaturesBoost = summonableCreaturesBoost; };
	public CharacterBaseCharacteristic getReflect() { return this.reflect; }
	public void setReflect(CharacterBaseCharacteristic reflect) { this.reflect = reflect; };
	public CharacterBaseCharacteristic getCriticalHit() { return this.criticalHit; }
	public void setCriticalHit(CharacterBaseCharacteristic criticalHit) { this.criticalHit = criticalHit; };
	public int getCriticalHitWeapon() { return this.criticalHitWeapon; }
	public void setCriticalHitWeapon(int criticalHitWeapon) { this.criticalHitWeapon = criticalHitWeapon; };
	public CharacterBaseCharacteristic getCriticalMiss() { return this.criticalMiss; }
	public void setCriticalMiss(CharacterBaseCharacteristic criticalMiss) { this.criticalMiss = criticalMiss; };
	public CharacterBaseCharacteristic getHealBonus() { return this.healBonus; }
	public void setHealBonus(CharacterBaseCharacteristic healBonus) { this.healBonus = healBonus; };
	public CharacterBaseCharacteristic getAllDamagesBonus() { return this.allDamagesBonus; }
	public void setAllDamagesBonus(CharacterBaseCharacteristic allDamagesBonus) { this.allDamagesBonus = allDamagesBonus; };
	public CharacterBaseCharacteristic getWeaponDamagesBonusPercent() { return this.weaponDamagesBonusPercent; }
	public void setWeaponDamagesBonusPercent(CharacterBaseCharacteristic weaponDamagesBonusPercent) { this.weaponDamagesBonusPercent = weaponDamagesBonusPercent; };
	public CharacterBaseCharacteristic getDamagesBonusPercent() { return this.damagesBonusPercent; }
	public void setDamagesBonusPercent(CharacterBaseCharacteristic damagesBonusPercent) { this.damagesBonusPercent = damagesBonusPercent; };
	public CharacterBaseCharacteristic getTrapBonus() { return this.trapBonus; }
	public void setTrapBonus(CharacterBaseCharacteristic trapBonus) { this.trapBonus = trapBonus; };
	public CharacterBaseCharacteristic getTrapBonusPercent() { return this.trapBonusPercent; }
	public void setTrapBonusPercent(CharacterBaseCharacteristic trapBonusPercent) { this.trapBonusPercent = trapBonusPercent; };
	public CharacterBaseCharacteristic getGlyphBonusPercent() { return this.glyphBonusPercent; }
	public void setGlyphBonusPercent(CharacterBaseCharacteristic glyphBonusPercent) { this.glyphBonusPercent = glyphBonusPercent; };
	public CharacterBaseCharacteristic getRuneBonusPercent() { return this.runeBonusPercent; }
	public void setRuneBonusPercent(CharacterBaseCharacteristic runeBonusPercent) { this.runeBonusPercent = runeBonusPercent; };
	public CharacterBaseCharacteristic getPermanentDamagePercent() { return this.permanentDamagePercent; }
	public void setPermanentDamagePercent(CharacterBaseCharacteristic permanentDamagePercent) { this.permanentDamagePercent = permanentDamagePercent; };
	public CharacterBaseCharacteristic getTackleBlock() { return this.tackleBlock; }
	public void setTackleBlock(CharacterBaseCharacteristic tackleBlock) { this.tackleBlock = tackleBlock; };
	public CharacterBaseCharacteristic getTackleEvade() { return this.tackleEvade; }
	public void setTackleEvade(CharacterBaseCharacteristic tackleEvade) { this.tackleEvade = tackleEvade; };
	public CharacterBaseCharacteristic getPAAttack() { return this.PAAttack; }
	public void setPAAttack(CharacterBaseCharacteristic PAAttack) { this.PAAttack = PAAttack; };
	public CharacterBaseCharacteristic getPMAttack() { return this.PMAttack; }
	public void setPMAttack(CharacterBaseCharacteristic PMAttack) { this.PMAttack = PMAttack; };
	public CharacterBaseCharacteristic getPushDamageBonus() { return this.pushDamageBonus; }
	public void setPushDamageBonus(CharacterBaseCharacteristic pushDamageBonus) { this.pushDamageBonus = pushDamageBonus; };
	public CharacterBaseCharacteristic getCriticalDamageBonus() { return this.criticalDamageBonus; }
	public void setCriticalDamageBonus(CharacterBaseCharacteristic criticalDamageBonus) { this.criticalDamageBonus = criticalDamageBonus; };
	public CharacterBaseCharacteristic getNeutralDamageBonus() { return this.neutralDamageBonus; }
	public void setNeutralDamageBonus(CharacterBaseCharacteristic neutralDamageBonus) { this.neutralDamageBonus = neutralDamageBonus; };
	public CharacterBaseCharacteristic getEarthDamageBonus() { return this.earthDamageBonus; }
	public void setEarthDamageBonus(CharacterBaseCharacteristic earthDamageBonus) { this.earthDamageBonus = earthDamageBonus; };
	public CharacterBaseCharacteristic getWaterDamageBonus() { return this.waterDamageBonus; }
	public void setWaterDamageBonus(CharacterBaseCharacteristic waterDamageBonus) { this.waterDamageBonus = waterDamageBonus; };
	public CharacterBaseCharacteristic getAirDamageBonus() { return this.airDamageBonus; }
	public void setAirDamageBonus(CharacterBaseCharacteristic airDamageBonus) { this.airDamageBonus = airDamageBonus; };
	public CharacterBaseCharacteristic getFireDamageBonus() { return this.fireDamageBonus; }
	public void setFireDamageBonus(CharacterBaseCharacteristic fireDamageBonus) { this.fireDamageBonus = fireDamageBonus; };
	public CharacterBaseCharacteristic getDodgePALostProbability() { return this.dodgePALostProbability; }
	public void setDodgePALostProbability(CharacterBaseCharacteristic dodgePALostProbability) { this.dodgePALostProbability = dodgePALostProbability; };
	public CharacterBaseCharacteristic getDodgePMLostProbability() { return this.dodgePMLostProbability; }
	public void setDodgePMLostProbability(CharacterBaseCharacteristic dodgePMLostProbability) { this.dodgePMLostProbability = dodgePMLostProbability; };
	public CharacterBaseCharacteristic getNeutralElementResistPercent() { return this.neutralElementResistPercent; }
	public void setNeutralElementResistPercent(CharacterBaseCharacteristic neutralElementResistPercent) { this.neutralElementResistPercent = neutralElementResistPercent; };
	public CharacterBaseCharacteristic getEarthElementResistPercent() { return this.earthElementResistPercent; }
	public void setEarthElementResistPercent(CharacterBaseCharacteristic earthElementResistPercent) { this.earthElementResistPercent = earthElementResistPercent; };
	public CharacterBaseCharacteristic getWaterElementResistPercent() { return this.waterElementResistPercent; }
	public void setWaterElementResistPercent(CharacterBaseCharacteristic waterElementResistPercent) { this.waterElementResistPercent = waterElementResistPercent; };
	public CharacterBaseCharacteristic getAirElementResistPercent() { return this.airElementResistPercent; }
	public void setAirElementResistPercent(CharacterBaseCharacteristic airElementResistPercent) { this.airElementResistPercent = airElementResistPercent; };
	public CharacterBaseCharacteristic getFireElementResistPercent() { return this.fireElementResistPercent; }
	public void setFireElementResistPercent(CharacterBaseCharacteristic fireElementResistPercent) { this.fireElementResistPercent = fireElementResistPercent; };
	public CharacterBaseCharacteristic getNeutralElementReduction() { return this.neutralElementReduction; }
	public void setNeutralElementReduction(CharacterBaseCharacteristic neutralElementReduction) { this.neutralElementReduction = neutralElementReduction; };
	public CharacterBaseCharacteristic getEarthElementReduction() { return this.earthElementReduction; }
	public void setEarthElementReduction(CharacterBaseCharacteristic earthElementReduction) { this.earthElementReduction = earthElementReduction; };
	public CharacterBaseCharacteristic getWaterElementReduction() { return this.waterElementReduction; }
	public void setWaterElementReduction(CharacterBaseCharacteristic waterElementReduction) { this.waterElementReduction = waterElementReduction; };
	public CharacterBaseCharacteristic getAirElementReduction() { return this.airElementReduction; }
	public void setAirElementReduction(CharacterBaseCharacteristic airElementReduction) { this.airElementReduction = airElementReduction; };
	public CharacterBaseCharacteristic getFireElementReduction() { return this.fireElementReduction; }
	public void setFireElementReduction(CharacterBaseCharacteristic fireElementReduction) { this.fireElementReduction = fireElementReduction; };
	public CharacterBaseCharacteristic getPushDamageReduction() { return this.pushDamageReduction; }
	public void setPushDamageReduction(CharacterBaseCharacteristic pushDamageReduction) { this.pushDamageReduction = pushDamageReduction; };
	public CharacterBaseCharacteristic getCriticalDamageReduction() { return this.criticalDamageReduction; }
	public void setCriticalDamageReduction(CharacterBaseCharacteristic criticalDamageReduction) { this.criticalDamageReduction = criticalDamageReduction; };
	public CharacterBaseCharacteristic getPvpNeutralElementResistPercent() { return this.pvpNeutralElementResistPercent; }
	public void setPvpNeutralElementResistPercent(CharacterBaseCharacteristic pvpNeutralElementResistPercent) { this.pvpNeutralElementResistPercent = pvpNeutralElementResistPercent; };
	public CharacterBaseCharacteristic getPvpEarthElementResistPercent() { return this.pvpEarthElementResistPercent; }
	public void setPvpEarthElementResistPercent(CharacterBaseCharacteristic pvpEarthElementResistPercent) { this.pvpEarthElementResistPercent = pvpEarthElementResistPercent; };
	public CharacterBaseCharacteristic getPvpWaterElementResistPercent() { return this.pvpWaterElementResistPercent; }
	public void setPvpWaterElementResistPercent(CharacterBaseCharacteristic pvpWaterElementResistPercent) { this.pvpWaterElementResistPercent = pvpWaterElementResistPercent; };
	public CharacterBaseCharacteristic getPvpAirElementResistPercent() { return this.pvpAirElementResistPercent; }
	public void setPvpAirElementResistPercent(CharacterBaseCharacteristic pvpAirElementResistPercent) { this.pvpAirElementResistPercent = pvpAirElementResistPercent; };
	public CharacterBaseCharacteristic getPvpFireElementResistPercent() { return this.pvpFireElementResistPercent; }
	public void setPvpFireElementResistPercent(CharacterBaseCharacteristic pvpFireElementResistPercent) { this.pvpFireElementResistPercent = pvpFireElementResistPercent; };
	public CharacterBaseCharacteristic getPvpNeutralElementReduction() { return this.pvpNeutralElementReduction; }
	public void setPvpNeutralElementReduction(CharacterBaseCharacteristic pvpNeutralElementReduction) { this.pvpNeutralElementReduction = pvpNeutralElementReduction; };
	public CharacterBaseCharacteristic getPvpEarthElementReduction() { return this.pvpEarthElementReduction; }
	public void setPvpEarthElementReduction(CharacterBaseCharacteristic pvpEarthElementReduction) { this.pvpEarthElementReduction = pvpEarthElementReduction; };
	public CharacterBaseCharacteristic getPvpWaterElementReduction() { return this.pvpWaterElementReduction; }
	public void setPvpWaterElementReduction(CharacterBaseCharacteristic pvpWaterElementReduction) { this.pvpWaterElementReduction = pvpWaterElementReduction; };
	public CharacterBaseCharacteristic getPvpAirElementReduction() { return this.pvpAirElementReduction; }
	public void setPvpAirElementReduction(CharacterBaseCharacteristic pvpAirElementReduction) { this.pvpAirElementReduction = pvpAirElementReduction; };
	public CharacterBaseCharacteristic getPvpFireElementReduction() { return this.pvpFireElementReduction; }
	public void setPvpFireElementReduction(CharacterBaseCharacteristic pvpFireElementReduction) { this.pvpFireElementReduction = pvpFireElementReduction; };
	public CharacterBaseCharacteristic getMeleeDamageDonePercent() { return this.meleeDamageDonePercent; }
	public void setMeleeDamageDonePercent(CharacterBaseCharacteristic meleeDamageDonePercent) { this.meleeDamageDonePercent = meleeDamageDonePercent; };
	public CharacterBaseCharacteristic getMeleeDamageReceivedPercent() { return this.meleeDamageReceivedPercent; }
	public void setMeleeDamageReceivedPercent(CharacterBaseCharacteristic meleeDamageReceivedPercent) { this.meleeDamageReceivedPercent = meleeDamageReceivedPercent; };
	public CharacterBaseCharacteristic getRangedDamageDonePercent() { return this.rangedDamageDonePercent; }
	public void setRangedDamageDonePercent(CharacterBaseCharacteristic rangedDamageDonePercent) { this.rangedDamageDonePercent = rangedDamageDonePercent; };
	public CharacterBaseCharacteristic getRangedDamageReceivedPercent() { return this.rangedDamageReceivedPercent; }
	public void setRangedDamageReceivedPercent(CharacterBaseCharacteristic rangedDamageReceivedPercent) { this.rangedDamageReceivedPercent = rangedDamageReceivedPercent; };
	public CharacterBaseCharacteristic getWeaponDamageDonePercent() { return this.weaponDamageDonePercent; }
	public void setWeaponDamageDonePercent(CharacterBaseCharacteristic weaponDamageDonePercent) { this.weaponDamageDonePercent = weaponDamageDonePercent; };
	public CharacterBaseCharacteristic getWeaponDamageReceivedPercent() { return this.weaponDamageReceivedPercent; }
	public void setWeaponDamageReceivedPercent(CharacterBaseCharacteristic weaponDamageReceivedPercent) { this.weaponDamageReceivedPercent = weaponDamageReceivedPercent; };
	public CharacterBaseCharacteristic getSpellDamageDonePercent() { return this.spellDamageDonePercent; }
	public void setSpellDamageDonePercent(CharacterBaseCharacteristic spellDamageDonePercent) { this.spellDamageDonePercent = spellDamageDonePercent; };
	public CharacterBaseCharacteristic getSpellDamageReceivedPercent() { return this.spellDamageReceivedPercent; }
	public void setSpellDamageReceivedPercent(CharacterBaseCharacteristic spellDamageReceivedPercent) { this.spellDamageReceivedPercent = spellDamageReceivedPercent; };
	public List<CharacterSpellModification> getSpellModifications() { return this.spellModifications; }
	public void setSpellModifications(List<CharacterSpellModification> spellModifications) { this.spellModifications = spellModifications; };
	public int getProbationTime() { return this.probationTime; }
	public void setProbationTime(int probationTime) { this.probationTime = probationTime; };

	public CharacterCharacteristicsInformations(){
	}

	public CharacterCharacteristicsInformations(long experience, long experienceLevelFloor, long experienceNextLevelFloor, long experienceBonusLimit, long kamas, int statsPoints, int additionnalPoints, int spellsPoints, ActorExtendedAlignmentInformations alignmentInfos, int lifePoints, int maxLifePoints, int energyPoints, int maxEnergyPoints, int actionPointsCurrent, int movementPointsCurrent, CharacterBaseCharacteristic initiative, CharacterBaseCharacteristic prospecting, CharacterBaseCharacteristic actionPoints, CharacterBaseCharacteristic movementPoints, CharacterBaseCharacteristic strength, CharacterBaseCharacteristic vitality, CharacterBaseCharacteristic wisdom, CharacterBaseCharacteristic chance, CharacterBaseCharacteristic agility, CharacterBaseCharacteristic intelligence, CharacterBaseCharacteristic range, CharacterBaseCharacteristic summonableCreaturesBoost, CharacterBaseCharacteristic reflect, CharacterBaseCharacteristic criticalHit, int criticalHitWeapon, CharacterBaseCharacteristic criticalMiss, CharacterBaseCharacteristic healBonus, CharacterBaseCharacteristic allDamagesBonus, CharacterBaseCharacteristic weaponDamagesBonusPercent, CharacterBaseCharacteristic damagesBonusPercent, CharacterBaseCharacteristic trapBonus, CharacterBaseCharacteristic trapBonusPercent, CharacterBaseCharacteristic glyphBonusPercent, CharacterBaseCharacteristic runeBonusPercent, CharacterBaseCharacteristic permanentDamagePercent, CharacterBaseCharacteristic tackleBlock, CharacterBaseCharacteristic tackleEvade, CharacterBaseCharacteristic PAAttack, CharacterBaseCharacteristic PMAttack, CharacterBaseCharacteristic pushDamageBonus, CharacterBaseCharacteristic criticalDamageBonus, CharacterBaseCharacteristic neutralDamageBonus, CharacterBaseCharacteristic earthDamageBonus, CharacterBaseCharacteristic waterDamageBonus, CharacterBaseCharacteristic airDamageBonus, CharacterBaseCharacteristic fireDamageBonus, CharacterBaseCharacteristic dodgePALostProbability, CharacterBaseCharacteristic dodgePMLostProbability, CharacterBaseCharacteristic neutralElementResistPercent, CharacterBaseCharacteristic earthElementResistPercent, CharacterBaseCharacteristic waterElementResistPercent, CharacterBaseCharacteristic airElementResistPercent, CharacterBaseCharacteristic fireElementResistPercent, CharacterBaseCharacteristic neutralElementReduction, CharacterBaseCharacteristic earthElementReduction, CharacterBaseCharacteristic waterElementReduction, CharacterBaseCharacteristic airElementReduction, CharacterBaseCharacteristic fireElementReduction, CharacterBaseCharacteristic pushDamageReduction, CharacterBaseCharacteristic criticalDamageReduction, CharacterBaseCharacteristic pvpNeutralElementResistPercent, CharacterBaseCharacteristic pvpEarthElementResistPercent, CharacterBaseCharacteristic pvpWaterElementResistPercent, CharacterBaseCharacteristic pvpAirElementResistPercent, CharacterBaseCharacteristic pvpFireElementResistPercent, CharacterBaseCharacteristic pvpNeutralElementReduction, CharacterBaseCharacteristic pvpEarthElementReduction, CharacterBaseCharacteristic pvpWaterElementReduction, CharacterBaseCharacteristic pvpAirElementReduction, CharacterBaseCharacteristic pvpFireElementReduction, CharacterBaseCharacteristic meleeDamageDonePercent, CharacterBaseCharacteristic meleeDamageReceivedPercent, CharacterBaseCharacteristic rangedDamageDonePercent, CharacterBaseCharacteristic rangedDamageReceivedPercent, CharacterBaseCharacteristic weaponDamageDonePercent, CharacterBaseCharacteristic weaponDamageReceivedPercent, CharacterBaseCharacteristic spellDamageDonePercent, CharacterBaseCharacteristic spellDamageReceivedPercent, List<CharacterSpellModification> spellModifications, int probationTime){
		this.experience = experience;
		this.experienceLevelFloor = experienceLevelFloor;
		this.experienceNextLevelFloor = experienceNextLevelFloor;
		this.experienceBonusLimit = experienceBonusLimit;
		this.kamas = kamas;
		this.statsPoints = statsPoints;
		this.additionnalPoints = additionnalPoints;
		this.spellsPoints = spellsPoints;
		this.alignmentInfos = alignmentInfos;
		this.lifePoints = lifePoints;
		this.maxLifePoints = maxLifePoints;
		this.energyPoints = energyPoints;
		this.maxEnergyPoints = maxEnergyPoints;
		this.actionPointsCurrent = actionPointsCurrent;
		this.movementPointsCurrent = movementPointsCurrent;
		this.initiative = initiative;
		this.prospecting = prospecting;
		this.actionPoints = actionPoints;
		this.movementPoints = movementPoints;
		this.strength = strength;
		this.vitality = vitality;
		this.wisdom = wisdom;
		this.chance = chance;
		this.agility = agility;
		this.intelligence = intelligence;
		this.range = range;
		this.summonableCreaturesBoost = summonableCreaturesBoost;
		this.reflect = reflect;
		this.criticalHit = criticalHit;
		this.criticalHitWeapon = criticalHitWeapon;
		this.criticalMiss = criticalMiss;
		this.healBonus = healBonus;
		this.allDamagesBonus = allDamagesBonus;
		this.weaponDamagesBonusPercent = weaponDamagesBonusPercent;
		this.damagesBonusPercent = damagesBonusPercent;
		this.trapBonus = trapBonus;
		this.trapBonusPercent = trapBonusPercent;
		this.glyphBonusPercent = glyphBonusPercent;
		this.runeBonusPercent = runeBonusPercent;
		this.permanentDamagePercent = permanentDamagePercent;
		this.tackleBlock = tackleBlock;
		this.tackleEvade = tackleEvade;
		this.PAAttack = PAAttack;
		this.PMAttack = PMAttack;
		this.pushDamageBonus = pushDamageBonus;
		this.criticalDamageBonus = criticalDamageBonus;
		this.neutralDamageBonus = neutralDamageBonus;
		this.earthDamageBonus = earthDamageBonus;
		this.waterDamageBonus = waterDamageBonus;
		this.airDamageBonus = airDamageBonus;
		this.fireDamageBonus = fireDamageBonus;
		this.dodgePALostProbability = dodgePALostProbability;
		this.dodgePMLostProbability = dodgePMLostProbability;
		this.neutralElementResistPercent = neutralElementResistPercent;
		this.earthElementResistPercent = earthElementResistPercent;
		this.waterElementResistPercent = waterElementResistPercent;
		this.airElementResistPercent = airElementResistPercent;
		this.fireElementResistPercent = fireElementResistPercent;
		this.neutralElementReduction = neutralElementReduction;
		this.earthElementReduction = earthElementReduction;
		this.waterElementReduction = waterElementReduction;
		this.airElementReduction = airElementReduction;
		this.fireElementReduction = fireElementReduction;
		this.pushDamageReduction = pushDamageReduction;
		this.criticalDamageReduction = criticalDamageReduction;
		this.pvpNeutralElementResistPercent = pvpNeutralElementResistPercent;
		this.pvpEarthElementResistPercent = pvpEarthElementResistPercent;
		this.pvpWaterElementResistPercent = pvpWaterElementResistPercent;
		this.pvpAirElementResistPercent = pvpAirElementResistPercent;
		this.pvpFireElementResistPercent = pvpFireElementResistPercent;
		this.pvpNeutralElementReduction = pvpNeutralElementReduction;
		this.pvpEarthElementReduction = pvpEarthElementReduction;
		this.pvpWaterElementReduction = pvpWaterElementReduction;
		this.pvpAirElementReduction = pvpAirElementReduction;
		this.pvpFireElementReduction = pvpFireElementReduction;
		this.meleeDamageDonePercent = meleeDamageDonePercent;
		this.meleeDamageReceivedPercent = meleeDamageReceivedPercent;
		this.rangedDamageDonePercent = rangedDamageDonePercent;
		this.rangedDamageReceivedPercent = rangedDamageReceivedPercent;
		this.weaponDamageDonePercent = weaponDamageDonePercent;
		this.weaponDamageReceivedPercent = weaponDamageReceivedPercent;
		this.spellDamageDonePercent = spellDamageDonePercent;
		this.spellDamageReceivedPercent = spellDamageReceivedPercent;
		this.spellModifications = spellModifications;
		this.probationTime = probationTime;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.experience);
			writer.writeVarLong(this.experienceLevelFloor);
			writer.writeVarLong(this.experienceNextLevelFloor);
			writer.writeVarLong(this.experienceBonusLimit);
			writer.writeVarLong(this.kamas);
			writer.writeVarShort(this.statsPoints);
			writer.writeVarShort(this.additionnalPoints);
			writer.writeVarShort(this.spellsPoints);
			alignmentInfos.Serialize(writer);
			writer.writeVarInt(this.lifePoints);
			writer.writeVarInt(this.maxLifePoints);
			writer.writeVarShort(this.energyPoints);
			writer.writeVarShort(this.maxEnergyPoints);
			writer.writeVarShort(this.actionPointsCurrent);
			writer.writeVarShort(this.movementPointsCurrent);
			initiative.Serialize(writer);
			prospecting.Serialize(writer);
			actionPoints.Serialize(writer);
			movementPoints.Serialize(writer);
			strength.Serialize(writer);
			vitality.Serialize(writer);
			wisdom.Serialize(writer);
			chance.Serialize(writer);
			agility.Serialize(writer);
			intelligence.Serialize(writer);
			range.Serialize(writer);
			summonableCreaturesBoost.Serialize(writer);
			reflect.Serialize(writer);
			criticalHit.Serialize(writer);
			writer.writeVarShort(this.criticalHitWeapon);
			criticalMiss.Serialize(writer);
			healBonus.Serialize(writer);
			allDamagesBonus.Serialize(writer);
			weaponDamagesBonusPercent.Serialize(writer);
			damagesBonusPercent.Serialize(writer);
			trapBonus.Serialize(writer);
			trapBonusPercent.Serialize(writer);
			glyphBonusPercent.Serialize(writer);
			runeBonusPercent.Serialize(writer);
			permanentDamagePercent.Serialize(writer);
			tackleBlock.Serialize(writer);
			tackleEvade.Serialize(writer);
			PAAttack.Serialize(writer);
			PMAttack.Serialize(writer);
			pushDamageBonus.Serialize(writer);
			criticalDamageBonus.Serialize(writer);
			neutralDamageBonus.Serialize(writer);
			earthDamageBonus.Serialize(writer);
			waterDamageBonus.Serialize(writer);
			airDamageBonus.Serialize(writer);
			fireDamageBonus.Serialize(writer);
			dodgePALostProbability.Serialize(writer);
			dodgePMLostProbability.Serialize(writer);
			neutralElementResistPercent.Serialize(writer);
			earthElementResistPercent.Serialize(writer);
			waterElementResistPercent.Serialize(writer);
			airElementResistPercent.Serialize(writer);
			fireElementResistPercent.Serialize(writer);
			neutralElementReduction.Serialize(writer);
			earthElementReduction.Serialize(writer);
			waterElementReduction.Serialize(writer);
			airElementReduction.Serialize(writer);
			fireElementReduction.Serialize(writer);
			pushDamageReduction.Serialize(writer);
			criticalDamageReduction.Serialize(writer);
			pvpNeutralElementResistPercent.Serialize(writer);
			pvpEarthElementResistPercent.Serialize(writer);
			pvpWaterElementResistPercent.Serialize(writer);
			pvpAirElementResistPercent.Serialize(writer);
			pvpFireElementResistPercent.Serialize(writer);
			pvpNeutralElementReduction.Serialize(writer);
			pvpEarthElementReduction.Serialize(writer);
			pvpWaterElementReduction.Serialize(writer);
			pvpAirElementReduction.Serialize(writer);
			pvpFireElementReduction.Serialize(writer);
			meleeDamageDonePercent.Serialize(writer);
			meleeDamageReceivedPercent.Serialize(writer);
			rangedDamageDonePercent.Serialize(writer);
			rangedDamageReceivedPercent.Serialize(writer);
			weaponDamageDonePercent.Serialize(writer);
			weaponDamageReceivedPercent.Serialize(writer);
			spellDamageDonePercent.Serialize(writer);
			spellDamageReceivedPercent.Serialize(writer);
			writer.writeShort(this.spellModifications.size());
			int _loc2_ = 0;
			while( _loc2_ < this.spellModifications.size()){
				this.spellModifications.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeInt(this.probationTime);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.experience = reader.readVarLong();
			this.experienceLevelFloor = reader.readVarLong();
			this.experienceNextLevelFloor = reader.readVarLong();
			this.experienceBonusLimit = reader.readVarLong();
			this.kamas = reader.readVarLong();
			this.statsPoints = reader.readVarShort();
			this.additionnalPoints = reader.readVarShort();
			this.spellsPoints = reader.readVarShort();
			this.alignmentInfos = new ActorExtendedAlignmentInformations();
			this.alignmentInfos.Deserialize(reader);
			this.lifePoints = reader.readVarInt();
			this.maxLifePoints = reader.readVarInt();
			this.energyPoints = reader.readVarShort();
			this.maxEnergyPoints = reader.readVarShort();
			this.actionPointsCurrent = reader.readVarShort();
			this.movementPointsCurrent = reader.readVarShort();
			this.initiative = new CharacterBaseCharacteristic();
			this.initiative.Deserialize(reader);
			this.prospecting = new CharacterBaseCharacteristic();
			this.prospecting.Deserialize(reader);
			this.actionPoints = new CharacterBaseCharacteristic();
			this.actionPoints.Deserialize(reader);
			this.movementPoints = new CharacterBaseCharacteristic();
			this.movementPoints.Deserialize(reader);
			this.strength = new CharacterBaseCharacteristic();
			this.strength.Deserialize(reader);
			this.vitality = new CharacterBaseCharacteristic();
			this.vitality.Deserialize(reader);
			this.wisdom = new CharacterBaseCharacteristic();
			this.wisdom.Deserialize(reader);
			this.chance = new CharacterBaseCharacteristic();
			this.chance.Deserialize(reader);
			this.agility = new CharacterBaseCharacteristic();
			this.agility.Deserialize(reader);
			this.intelligence = new CharacterBaseCharacteristic();
			this.intelligence.Deserialize(reader);
			this.range = new CharacterBaseCharacteristic();
			this.range.Deserialize(reader);
			this.summonableCreaturesBoost = new CharacterBaseCharacteristic();
			this.summonableCreaturesBoost.Deserialize(reader);
			this.reflect = new CharacterBaseCharacteristic();
			this.reflect.Deserialize(reader);
			this.criticalHit = new CharacterBaseCharacteristic();
			this.criticalHit.Deserialize(reader);
			this.criticalHitWeapon = reader.readVarShort();
			this.criticalMiss = new CharacterBaseCharacteristic();
			this.criticalMiss.Deserialize(reader);
			this.healBonus = new CharacterBaseCharacteristic();
			this.healBonus.Deserialize(reader);
			this.allDamagesBonus = new CharacterBaseCharacteristic();
			this.allDamagesBonus.Deserialize(reader);
			this.weaponDamagesBonusPercent = new CharacterBaseCharacteristic();
			this.weaponDamagesBonusPercent.Deserialize(reader);
			this.damagesBonusPercent = new CharacterBaseCharacteristic();
			this.damagesBonusPercent.Deserialize(reader);
			this.trapBonus = new CharacterBaseCharacteristic();
			this.trapBonus.Deserialize(reader);
			this.trapBonusPercent = new CharacterBaseCharacteristic();
			this.trapBonusPercent.Deserialize(reader);
			this.glyphBonusPercent = new CharacterBaseCharacteristic();
			this.glyphBonusPercent.Deserialize(reader);
			this.runeBonusPercent = new CharacterBaseCharacteristic();
			this.runeBonusPercent.Deserialize(reader);
			this.permanentDamagePercent = new CharacterBaseCharacteristic();
			this.permanentDamagePercent.Deserialize(reader);
			this.tackleBlock = new CharacterBaseCharacteristic();
			this.tackleBlock.Deserialize(reader);
			this.tackleEvade = new CharacterBaseCharacteristic();
			this.tackleEvade.Deserialize(reader);
			this.PAAttack = new CharacterBaseCharacteristic();
			this.PAAttack.Deserialize(reader);
			this.PMAttack = new CharacterBaseCharacteristic();
			this.PMAttack.Deserialize(reader);
			this.pushDamageBonus = new CharacterBaseCharacteristic();
			this.pushDamageBonus.Deserialize(reader);
			this.criticalDamageBonus = new CharacterBaseCharacteristic();
			this.criticalDamageBonus.Deserialize(reader);
			this.neutralDamageBonus = new CharacterBaseCharacteristic();
			this.neutralDamageBonus.Deserialize(reader);
			this.earthDamageBonus = new CharacterBaseCharacteristic();
			this.earthDamageBonus.Deserialize(reader);
			this.waterDamageBonus = new CharacterBaseCharacteristic();
			this.waterDamageBonus.Deserialize(reader);
			this.airDamageBonus = new CharacterBaseCharacteristic();
			this.airDamageBonus.Deserialize(reader);
			this.fireDamageBonus = new CharacterBaseCharacteristic();
			this.fireDamageBonus.Deserialize(reader);
			this.dodgePALostProbability = new CharacterBaseCharacteristic();
			this.dodgePALostProbability.Deserialize(reader);
			this.dodgePMLostProbability = new CharacterBaseCharacteristic();
			this.dodgePMLostProbability.Deserialize(reader);
			this.neutralElementResistPercent = new CharacterBaseCharacteristic();
			this.neutralElementResistPercent.Deserialize(reader);
			this.earthElementResistPercent = new CharacterBaseCharacteristic();
			this.earthElementResistPercent.Deserialize(reader);
			this.waterElementResistPercent = new CharacterBaseCharacteristic();
			this.waterElementResistPercent.Deserialize(reader);
			this.airElementResistPercent = new CharacterBaseCharacteristic();
			this.airElementResistPercent.Deserialize(reader);
			this.fireElementResistPercent = new CharacterBaseCharacteristic();
			this.fireElementResistPercent.Deserialize(reader);
			this.neutralElementReduction = new CharacterBaseCharacteristic();
			this.neutralElementReduction.Deserialize(reader);
			this.earthElementReduction = new CharacterBaseCharacteristic();
			this.earthElementReduction.Deserialize(reader);
			this.waterElementReduction = new CharacterBaseCharacteristic();
			this.waterElementReduction.Deserialize(reader);
			this.airElementReduction = new CharacterBaseCharacteristic();
			this.airElementReduction.Deserialize(reader);
			this.fireElementReduction = new CharacterBaseCharacteristic();
			this.fireElementReduction.Deserialize(reader);
			this.pushDamageReduction = new CharacterBaseCharacteristic();
			this.pushDamageReduction.Deserialize(reader);
			this.criticalDamageReduction = new CharacterBaseCharacteristic();
			this.criticalDamageReduction.Deserialize(reader);
			this.pvpNeutralElementResistPercent = new CharacterBaseCharacteristic();
			this.pvpNeutralElementResistPercent.Deserialize(reader);
			this.pvpEarthElementResistPercent = new CharacterBaseCharacteristic();
			this.pvpEarthElementResistPercent.Deserialize(reader);
			this.pvpWaterElementResistPercent = new CharacterBaseCharacteristic();
			this.pvpWaterElementResistPercent.Deserialize(reader);
			this.pvpAirElementResistPercent = new CharacterBaseCharacteristic();
			this.pvpAirElementResistPercent.Deserialize(reader);
			this.pvpFireElementResistPercent = new CharacterBaseCharacteristic();
			this.pvpFireElementResistPercent.Deserialize(reader);
			this.pvpNeutralElementReduction = new CharacterBaseCharacteristic();
			this.pvpNeutralElementReduction.Deserialize(reader);
			this.pvpEarthElementReduction = new CharacterBaseCharacteristic();
			this.pvpEarthElementReduction.Deserialize(reader);
			this.pvpWaterElementReduction = new CharacterBaseCharacteristic();
			this.pvpWaterElementReduction.Deserialize(reader);
			this.pvpAirElementReduction = new CharacterBaseCharacteristic();
			this.pvpAirElementReduction.Deserialize(reader);
			this.pvpFireElementReduction = new CharacterBaseCharacteristic();
			this.pvpFireElementReduction.Deserialize(reader);
			this.meleeDamageDonePercent = new CharacterBaseCharacteristic();
			this.meleeDamageDonePercent.Deserialize(reader);
			this.meleeDamageReceivedPercent = new CharacterBaseCharacteristic();
			this.meleeDamageReceivedPercent.Deserialize(reader);
			this.rangedDamageDonePercent = new CharacterBaseCharacteristic();
			this.rangedDamageDonePercent.Deserialize(reader);
			this.rangedDamageReceivedPercent = new CharacterBaseCharacteristic();
			this.rangedDamageReceivedPercent.Deserialize(reader);
			this.weaponDamageDonePercent = new CharacterBaseCharacteristic();
			this.weaponDamageDonePercent.Deserialize(reader);
			this.weaponDamageReceivedPercent = new CharacterBaseCharacteristic();
			this.weaponDamageReceivedPercent.Deserialize(reader);
			this.spellDamageDonePercent = new CharacterBaseCharacteristic();
			this.spellDamageDonePercent.Deserialize(reader);
			this.spellDamageReceivedPercent = new CharacterBaseCharacteristic();
			this.spellDamageReceivedPercent.Deserialize(reader);
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.spellModifications = new ArrayList<CharacterSpellModification>();
			while( _loc3_ <  _loc2_){
				CharacterSpellModification _loc15_ = new CharacterSpellModification();
				_loc15_.Deserialize(reader);
				this.spellModifications.add(_loc15_);
				_loc3_++;
			}
			this.probationTime = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
