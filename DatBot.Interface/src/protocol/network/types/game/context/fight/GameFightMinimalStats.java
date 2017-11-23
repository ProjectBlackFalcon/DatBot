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

	public int lifePoints;
	public int maxLifePoints;
	public int baseMaxLifePoints;
	public int permanentDamagePercent;
	public int shieldPoints;
	public int actionPoints;
	public int maxActionPoints;
	public int movementPoints;
	public int maxMovementPoints;
	public double summoner;
	public boolean summoned;
	public int neutralElementResistPercent;
	public int earthElementResistPercent;
	public int waterElementResistPercent;
	public int airElementResistPercent;
	public int fireElementResistPercent;
	public int neutralElementReduction;
	public int earthElementReduction;
	public int waterElementReduction;
	public int airElementReduction;
	public int fireElementReduction;
	public int criticalDamageFixedResist;
	public int pushDamageFixedResist;
	public int pvpNeutralElementResistPercent;
	public int pvpEarthElementResistPercent;
	public int pvpWaterElementResistPercent;
	public int pvpAirElementResistPercent;
	public int pvpFireElementResistPercent;
	public int pvpNeutralElementReduction;
	public int pvpEarthElementReduction;
	public int pvpWaterElementReduction;
	public int pvpAirElementReduction;
	public int pvpFireElementReduction;
	public int dodgePALostProbability;
	public int dodgePMLostProbability;
	public int tackleBlock;
	public int tackleEvade;
	public int fixedDamageReflection;
	public int invisibilityState;
	public int meleeDamageReceivedPercent;
	public int rangedDamageReceivedPercent;
	public int weaponDamageReceivedPercent;
	public int spellDamageReceivedPercent;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("lifePoints : " + this.lifePoints);
		//Network.appendDebug("maxLifePoints : " + this.maxLifePoints);
		//Network.appendDebug("baseMaxLifePoints : " + this.baseMaxLifePoints);
		//Network.appendDebug("permanentDamagePercent : " + this.permanentDamagePercent);
		//Network.appendDebug("shieldPoints : " + this.shieldPoints);
		//Network.appendDebug("actionPoints : " + this.actionPoints);
		//Network.appendDebug("maxActionPoints : " + this.maxActionPoints);
		//Network.appendDebug("movementPoints : " + this.movementPoints);
		//Network.appendDebug("maxMovementPoints : " + this.maxMovementPoints);
		//Network.appendDebug("summoner : " + this.summoner);
		//Network.appendDebug("summoned : " + this.summoned);
		//Network.appendDebug("neutralElementResistPercent : " + this.neutralElementResistPercent);
		//Network.appendDebug("earthElementResistPercent : " + this.earthElementResistPercent);
		//Network.appendDebug("waterElementResistPercent : " + this.waterElementResistPercent);
		//Network.appendDebug("airElementResistPercent : " + this.airElementResistPercent);
		//Network.appendDebug("fireElementResistPercent : " + this.fireElementResistPercent);
		//Network.appendDebug("neutralElementReduction : " + this.neutralElementReduction);
		//Network.appendDebug("earthElementReduction : " + this.earthElementReduction);
		//Network.appendDebug("waterElementReduction : " + this.waterElementReduction);
		//Network.appendDebug("airElementReduction : " + this.airElementReduction);
		//Network.appendDebug("fireElementReduction : " + this.fireElementReduction);
		//Network.appendDebug("criticalDamageFixedResist : " + this.criticalDamageFixedResist);
		//Network.appendDebug("pushDamageFixedResist : " + this.pushDamageFixedResist);
		//Network.appendDebug("pvpNeutralElementResistPercent : " + this.pvpNeutralElementResistPercent);
		//Network.appendDebug("pvpEarthElementResistPercent : " + this.pvpEarthElementResistPercent);
		//Network.appendDebug("pvpWaterElementResistPercent : " + this.pvpWaterElementResistPercent);
		//Network.appendDebug("pvpAirElementResistPercent : " + this.pvpAirElementResistPercent);
		//Network.appendDebug("pvpFireElementResistPercent : " + this.pvpFireElementResistPercent);
		//Network.appendDebug("pvpNeutralElementReduction : " + this.pvpNeutralElementReduction);
		//Network.appendDebug("pvpEarthElementReduction : " + this.pvpEarthElementReduction);
		//Network.appendDebug("pvpWaterElementReduction : " + this.pvpWaterElementReduction);
		//Network.appendDebug("pvpAirElementReduction : " + this.pvpAirElementReduction);
		//Network.appendDebug("pvpFireElementReduction : " + this.pvpFireElementReduction);
		//Network.appendDebug("dodgePALostProbability : " + this.dodgePALostProbability);
		//Network.appendDebug("dodgePMLostProbability : " + this.dodgePMLostProbability);
		//Network.appendDebug("tackleBlock : " + this.tackleBlock);
		//Network.appendDebug("tackleEvade : " + this.tackleEvade);
		//Network.appendDebug("fixedDamageReflection : " + this.fixedDamageReflection);
		//Network.appendDebug("invisibilityState : " + this.invisibilityState);
		//Network.appendDebug("meleeDamageReceivedPercent : " + this.meleeDamageReceivedPercent);
		//Network.appendDebug("rangedDamageReceivedPercent : " + this.rangedDamageReceivedPercent);
		//Network.appendDebug("weaponDamageReceivedPercent : " + this.weaponDamageReceivedPercent);
		//Network.appendDebug("spellDamageReceivedPercent : " + this.spellDamageReceivedPercent);
	//}
}
