package protocol.network.types.game.context.fight;

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

@SuppressWarnings("unused")
public class GameFightMinimalStats extends NetworkMessage {
	public static final int ProtocolId = 31;

	private int lifePoints;
	private int maxLifePoints;
	private int baseMaxLifePoints;
	private int permanentDamagePercent;
	private int shieldPoints;
	private int actionPoints;
	private int maxActionPoints;
	private int movementPoints;
	private int maxMovementPoints;
	private double summoner;
	private boolean summoned;
	private int neutralElementResistPercent;
	private int earthElementResistPercent;
	private int waterElementResistPercent;
	private int airElementResistPercent;
	private int fireElementResistPercent;
	private int neutralElementReduction;
	private int earthElementReduction;
	private int waterElementReduction;
	private int airElementReduction;
	private int fireElementReduction;
	private int criticalDamageFixedResist;
	private int pushDamageFixedResist;
	private int pvpNeutralElementResistPercent;
	private int pvpEarthElementResistPercent;
	private int pvpWaterElementResistPercent;
	private int pvpAirElementResistPercent;
	private int pvpFireElementResistPercent;
	private int pvpNeutralElementReduction;
	private int pvpEarthElementReduction;
	private int pvpWaterElementReduction;
	private int pvpAirElementReduction;
	private int pvpFireElementReduction;
	private int dodgePALostProbability;
	private int dodgePMLostProbability;
	private int tackleBlock;
	private int tackleEvade;
	private int fixedDamageReflection;
	private int invisibilityState;
	private int meleeDamageReceivedPercent;
	private int rangedDamageReceivedPercent;
	private int weaponDamageReceivedPercent;
	private int spellDamageReceivedPercent;

	public int getLifePoints() { return this.lifePoints; }
	public void setLifePoints(int lifePoints) { this.lifePoints = lifePoints; };
	public int getMaxLifePoints() { return this.maxLifePoints; }
	public void setMaxLifePoints(int maxLifePoints) { this.maxLifePoints = maxLifePoints; };
	public int getBaseMaxLifePoints() { return this.baseMaxLifePoints; }
	public void setBaseMaxLifePoints(int baseMaxLifePoints) { this.baseMaxLifePoints = baseMaxLifePoints; };
	public int getPermanentDamagePercent() { return this.permanentDamagePercent; }
	public void setPermanentDamagePercent(int permanentDamagePercent) { this.permanentDamagePercent = permanentDamagePercent; };
	public int getShieldPoints() { return this.shieldPoints; }
	public void setShieldPoints(int shieldPoints) { this.shieldPoints = shieldPoints; };
	public int getActionPoints() { return this.actionPoints; }
	public void setActionPoints(int actionPoints) { this.actionPoints = actionPoints; };
	public int getMaxActionPoints() { return this.maxActionPoints; }
	public void setMaxActionPoints(int maxActionPoints) { this.maxActionPoints = maxActionPoints; };
	public int getMovementPoints() { return this.movementPoints; }
	public void setMovementPoints(int movementPoints) { this.movementPoints = movementPoints; };
	public int getMaxMovementPoints() { return this.maxMovementPoints; }
	public void setMaxMovementPoints(int maxMovementPoints) { this.maxMovementPoints = maxMovementPoints; };
	public double getSummoner() { return this.summoner; }
	public void setSummoner(double summoner) { this.summoner = summoner; };
	public boolean isSummoned() { return this.summoned; }
	public void setSummoned(boolean summoned) { this.summoned = summoned; };
	public int getNeutralElementResistPercent() { return this.neutralElementResistPercent; }
	public void setNeutralElementResistPercent(int neutralElementResistPercent) { this.neutralElementResistPercent = neutralElementResistPercent; };
	public int getEarthElementResistPercent() { return this.earthElementResistPercent; }
	public void setEarthElementResistPercent(int earthElementResistPercent) { this.earthElementResistPercent = earthElementResistPercent; };
	public int getWaterElementResistPercent() { return this.waterElementResistPercent; }
	public void setWaterElementResistPercent(int waterElementResistPercent) { this.waterElementResistPercent = waterElementResistPercent; };
	public int getAirElementResistPercent() { return this.airElementResistPercent; }
	public void setAirElementResistPercent(int airElementResistPercent) { this.airElementResistPercent = airElementResistPercent; };
	public int getFireElementResistPercent() { return this.fireElementResistPercent; }
	public void setFireElementResistPercent(int fireElementResistPercent) { this.fireElementResistPercent = fireElementResistPercent; };
	public int getNeutralElementReduction() { return this.neutralElementReduction; }
	public void setNeutralElementReduction(int neutralElementReduction) { this.neutralElementReduction = neutralElementReduction; };
	public int getEarthElementReduction() { return this.earthElementReduction; }
	public void setEarthElementReduction(int earthElementReduction) { this.earthElementReduction = earthElementReduction; };
	public int getWaterElementReduction() { return this.waterElementReduction; }
	public void setWaterElementReduction(int waterElementReduction) { this.waterElementReduction = waterElementReduction; };
	public int getAirElementReduction() { return this.airElementReduction; }
	public void setAirElementReduction(int airElementReduction) { this.airElementReduction = airElementReduction; };
	public int getFireElementReduction() { return this.fireElementReduction; }
	public void setFireElementReduction(int fireElementReduction) { this.fireElementReduction = fireElementReduction; };
	public int getCriticalDamageFixedResist() { return this.criticalDamageFixedResist; }
	public void setCriticalDamageFixedResist(int criticalDamageFixedResist) { this.criticalDamageFixedResist = criticalDamageFixedResist; };
	public int getPushDamageFixedResist() { return this.pushDamageFixedResist; }
	public void setPushDamageFixedResist(int pushDamageFixedResist) { this.pushDamageFixedResist = pushDamageFixedResist; };
	public int getPvpNeutralElementResistPercent() { return this.pvpNeutralElementResistPercent; }
	public void setPvpNeutralElementResistPercent(int pvpNeutralElementResistPercent) { this.pvpNeutralElementResistPercent = pvpNeutralElementResistPercent; };
	public int getPvpEarthElementResistPercent() { return this.pvpEarthElementResistPercent; }
	public void setPvpEarthElementResistPercent(int pvpEarthElementResistPercent) { this.pvpEarthElementResistPercent = pvpEarthElementResistPercent; };
	public int getPvpWaterElementResistPercent() { return this.pvpWaterElementResistPercent; }
	public void setPvpWaterElementResistPercent(int pvpWaterElementResistPercent) { this.pvpWaterElementResistPercent = pvpWaterElementResistPercent; };
	public int getPvpAirElementResistPercent() { return this.pvpAirElementResistPercent; }
	public void setPvpAirElementResistPercent(int pvpAirElementResistPercent) { this.pvpAirElementResistPercent = pvpAirElementResistPercent; };
	public int getPvpFireElementResistPercent() { return this.pvpFireElementResistPercent; }
	public void setPvpFireElementResistPercent(int pvpFireElementResistPercent) { this.pvpFireElementResistPercent = pvpFireElementResistPercent; };
	public int getPvpNeutralElementReduction() { return this.pvpNeutralElementReduction; }
	public void setPvpNeutralElementReduction(int pvpNeutralElementReduction) { this.pvpNeutralElementReduction = pvpNeutralElementReduction; };
	public int getPvpEarthElementReduction() { return this.pvpEarthElementReduction; }
	public void setPvpEarthElementReduction(int pvpEarthElementReduction) { this.pvpEarthElementReduction = pvpEarthElementReduction; };
	public int getPvpWaterElementReduction() { return this.pvpWaterElementReduction; }
	public void setPvpWaterElementReduction(int pvpWaterElementReduction) { this.pvpWaterElementReduction = pvpWaterElementReduction; };
	public int getPvpAirElementReduction() { return this.pvpAirElementReduction; }
	public void setPvpAirElementReduction(int pvpAirElementReduction) { this.pvpAirElementReduction = pvpAirElementReduction; };
	public int getPvpFireElementReduction() { return this.pvpFireElementReduction; }
	public void setPvpFireElementReduction(int pvpFireElementReduction) { this.pvpFireElementReduction = pvpFireElementReduction; };
	public int getDodgePALostProbability() { return this.dodgePALostProbability; }
	public void setDodgePALostProbability(int dodgePALostProbability) { this.dodgePALostProbability = dodgePALostProbability; };
	public int getDodgePMLostProbability() { return this.dodgePMLostProbability; }
	public void setDodgePMLostProbability(int dodgePMLostProbability) { this.dodgePMLostProbability = dodgePMLostProbability; };
	public int getTackleBlock() { return this.tackleBlock; }
	public void setTackleBlock(int tackleBlock) { this.tackleBlock = tackleBlock; };
	public int getTackleEvade() { return this.tackleEvade; }
	public void setTackleEvade(int tackleEvade) { this.tackleEvade = tackleEvade; };
	public int getFixedDamageReflection() { return this.fixedDamageReflection; }
	public void setFixedDamageReflection(int fixedDamageReflection) { this.fixedDamageReflection = fixedDamageReflection; };
	public int getInvisibilityState() { return this.invisibilityState; }
	public void setInvisibilityState(int invisibilityState) { this.invisibilityState = invisibilityState; };
	public int getMeleeDamageReceivedPercent() { return this.meleeDamageReceivedPercent; }
	public void setMeleeDamageReceivedPercent(int meleeDamageReceivedPercent) { this.meleeDamageReceivedPercent = meleeDamageReceivedPercent; };
	public int getRangedDamageReceivedPercent() { return this.rangedDamageReceivedPercent; }
	public void setRangedDamageReceivedPercent(int rangedDamageReceivedPercent) { this.rangedDamageReceivedPercent = rangedDamageReceivedPercent; };
	public int getWeaponDamageReceivedPercent() { return this.weaponDamageReceivedPercent; }
	public void setWeaponDamageReceivedPercent(int weaponDamageReceivedPercent) { this.weaponDamageReceivedPercent = weaponDamageReceivedPercent; };
	public int getSpellDamageReceivedPercent() { return this.spellDamageReceivedPercent; }
	public void setSpellDamageReceivedPercent(int spellDamageReceivedPercent) { this.spellDamageReceivedPercent = spellDamageReceivedPercent; };

	public GameFightMinimalStats(){
	}

	public GameFightMinimalStats(int lifePoints, int maxLifePoints, int baseMaxLifePoints, int permanentDamagePercent, int shieldPoints, int actionPoints, int maxActionPoints, int movementPoints, int maxMovementPoints, double summoner, boolean summoned, int neutralElementResistPercent, int earthElementResistPercent, int waterElementResistPercent, int airElementResistPercent, int fireElementResistPercent, int neutralElementReduction, int earthElementReduction, int waterElementReduction, int airElementReduction, int fireElementReduction, int criticalDamageFixedResist, int pushDamageFixedResist, int pvpNeutralElementResistPercent, int pvpEarthElementResistPercent, int pvpWaterElementResistPercent, int pvpAirElementResistPercent, int pvpFireElementResistPercent, int pvpNeutralElementReduction, int pvpEarthElementReduction, int pvpWaterElementReduction, int pvpAirElementReduction, int pvpFireElementReduction, int dodgePALostProbability, int dodgePMLostProbability, int tackleBlock, int tackleEvade, int fixedDamageReflection, int invisibilityState, int meleeDamageReceivedPercent, int rangedDamageReceivedPercent, int weaponDamageReceivedPercent, int spellDamageReceivedPercent){
		this.lifePoints = lifePoints;
		this.maxLifePoints = maxLifePoints;
		this.baseMaxLifePoints = baseMaxLifePoints;
		this.permanentDamagePercent = permanentDamagePercent;
		this.shieldPoints = shieldPoints;
		this.actionPoints = actionPoints;
		this.maxActionPoints = maxActionPoints;
		this.movementPoints = movementPoints;
		this.maxMovementPoints = maxMovementPoints;
		this.summoner = summoner;
		this.summoned = summoned;
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
		this.criticalDamageFixedResist = criticalDamageFixedResist;
		this.pushDamageFixedResist = pushDamageFixedResist;
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
		this.dodgePALostProbability = dodgePALostProbability;
		this.dodgePMLostProbability = dodgePMLostProbability;
		this.tackleBlock = tackleBlock;
		this.tackleEvade = tackleEvade;
		this.fixedDamageReflection = fixedDamageReflection;
		this.invisibilityState = invisibilityState;
		this.meleeDamageReceivedPercent = meleeDamageReceivedPercent;
		this.rangedDamageReceivedPercent = rangedDamageReceivedPercent;
		this.weaponDamageReceivedPercent = weaponDamageReceivedPercent;
		this.spellDamageReceivedPercent = spellDamageReceivedPercent;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.lifePoints);
			writer.writeVarInt(this.maxLifePoints);
			writer.writeVarInt(this.baseMaxLifePoints);
			writer.writeVarInt(this.permanentDamagePercent);
			writer.writeVarInt(this.shieldPoints);
			writer.writeVarShort(this.actionPoints);
			writer.writeVarShort(this.maxActionPoints);
			writer.writeVarShort(this.movementPoints);
			writer.writeVarShort(this.maxMovementPoints);
			writer.writeDouble(this.summoner);
			writer.writeBoolean(this.summoned);
			writer.writeVarShort(this.neutralElementResistPercent);
			writer.writeVarShort(this.earthElementResistPercent);
			writer.writeVarShort(this.waterElementResistPercent);
			writer.writeVarShort(this.airElementResistPercent);
			writer.writeVarShort(this.fireElementResistPercent);
			writer.writeVarShort(this.neutralElementReduction);
			writer.writeVarShort(this.earthElementReduction);
			writer.writeVarShort(this.waterElementReduction);
			writer.writeVarShort(this.airElementReduction);
			writer.writeVarShort(this.fireElementReduction);
			writer.writeVarShort(this.criticalDamageFixedResist);
			writer.writeVarShort(this.pushDamageFixedResist);
			writer.writeVarShort(this.pvpNeutralElementResistPercent);
			writer.writeVarShort(this.pvpEarthElementResistPercent);
			writer.writeVarShort(this.pvpWaterElementResistPercent);
			writer.writeVarShort(this.pvpAirElementResistPercent);
			writer.writeVarShort(this.pvpFireElementResistPercent);
			writer.writeVarShort(this.pvpNeutralElementReduction);
			writer.writeVarShort(this.pvpEarthElementReduction);
			writer.writeVarShort(this.pvpWaterElementReduction);
			writer.writeVarShort(this.pvpAirElementReduction);
			writer.writeVarShort(this.pvpFireElementReduction);
			writer.writeVarShort(this.dodgePALostProbability);
			writer.writeVarShort(this.dodgePMLostProbability);
			writer.writeVarShort(this.tackleBlock);
			writer.writeVarShort(this.tackleEvade);
			writer.writeVarShort(this.fixedDamageReflection);
			writer.writeByte(this.invisibilityState);
			writer.writeVarShort(this.meleeDamageReceivedPercent);
			writer.writeVarShort(this.rangedDamageReceivedPercent);
			writer.writeVarShort(this.weaponDamageReceivedPercent);
			writer.writeVarShort(this.spellDamageReceivedPercent);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.lifePoints = reader.readVarInt();
			this.maxLifePoints = reader.readVarInt();
			this.baseMaxLifePoints = reader.readVarInt();
			this.permanentDamagePercent = reader.readVarInt();
			this.shieldPoints = reader.readVarInt();
			this.actionPoints = reader.readVarShort();
			this.maxActionPoints = reader.readVarShort();
			this.movementPoints = reader.readVarShort();
			this.maxMovementPoints = reader.readVarShort();
			this.summoner = reader.readDouble();
			this.summoned = reader.readBoolean();
			this.neutralElementResistPercent = reader.readVarShort();
			this.earthElementResistPercent = reader.readVarShort();
			this.waterElementResistPercent = reader.readVarShort();
			this.airElementResistPercent = reader.readVarShort();
			this.fireElementResistPercent = reader.readVarShort();
			this.neutralElementReduction = reader.readVarShort();
			this.earthElementReduction = reader.readVarShort();
			this.waterElementReduction = reader.readVarShort();
			this.airElementReduction = reader.readVarShort();
			this.fireElementReduction = reader.readVarShort();
			this.criticalDamageFixedResist = reader.readVarShort();
			this.pushDamageFixedResist = reader.readVarShort();
			this.pvpNeutralElementResistPercent = reader.readVarShort();
			this.pvpEarthElementResistPercent = reader.readVarShort();
			this.pvpWaterElementResistPercent = reader.readVarShort();
			this.pvpAirElementResistPercent = reader.readVarShort();
			this.pvpFireElementResistPercent = reader.readVarShort();
			this.pvpNeutralElementReduction = reader.readVarShort();
			this.pvpEarthElementReduction = reader.readVarShort();
			this.pvpWaterElementReduction = reader.readVarShort();
			this.pvpAirElementReduction = reader.readVarShort();
			this.pvpFireElementReduction = reader.readVarShort();
			this.dodgePALostProbability = reader.readVarShort();
			this.dodgePMLostProbability = reader.readVarShort();
			this.tackleBlock = reader.readVarShort();
			this.tackleEvade = reader.readVarShort();
			this.fixedDamageReflection = reader.readVarShort();
			this.invisibilityState = reader.readByte();
			this.meleeDamageReceivedPercent = reader.readVarShort();
			this.rangedDamageReceivedPercent = reader.readVarShort();
			this.weaponDamageReceivedPercent = reader.readVarShort();
			this.spellDamageReceivedPercent = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
