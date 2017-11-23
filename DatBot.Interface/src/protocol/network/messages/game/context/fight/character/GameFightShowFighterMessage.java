package protocol.network.messages.game.context.fight.character;

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
import protocol.network.types.game.context.fight.GameFightFighterInformations;

@SuppressWarnings("unused")
public class GameFightShowFighterMessage extends NetworkMessage {
	public static final int ProtocolId = 5864;

	public GameFightFighterInformations informations;

	public GameFightShowFighterMessage(){
	}

	public GameFightShowFighterMessage(GameFightFighterInformations informations){
		this.informations = informations;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(GameFightFighterInformations.ProtocolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.informations = (GameFightFighterInformations) ProtocolTypeManager.getInstance(reader.readShort());
			this.informations.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		append();
	}

	private void append(){
		Network.appendDebug("contextualId : " + this.informations.contextualId);
		Network.appendDebug("teamId : " + this.informations.teamId);
		Network.appendDebug("wave : " + this.informations.wave);
		Network.appendDebug("alive : " + this.informations.alive);
		Network.appendDebug("cellId : " + this.informations.disposition.cellId);
//		for (Integer i : this.informations.previousPositions) {
//			Network.appendDebug("previousPositions : " + i);
//		}
		Network.appendDebug("lifePoints : " + this.informations.stats.lifePoints);
				Network.appendDebug("maxLifePoints : " + this.informations.stats.maxLifePoints);
				Network.appendDebug("baseMaxLifePoints : " + this.informations.stats.baseMaxLifePoints);
				Network.appendDebug("permanentDamagePercent : " + this.informations.stats.permanentDamagePercent);
				Network.appendDebug("shieldPoints : " + this.informations.stats.shieldPoints);
				Network.appendDebug("actionPoints : " + this.informations.stats.actionPoints);
				Network.appendDebug("maxActionPoints : " + this.informations.stats.maxActionPoints);
				Network.appendDebug("movementPoints : " + this.informations.stats.movementPoints);
				Network.appendDebug("maxMovementPoints : " + this.informations.stats.maxMovementPoints);
				Network.appendDebug("summoner : " + this.informations.stats.summoner);
				Network.appendDebug("summoned : " + this.informations.stats.summoned);
				Network.appendDebug("neutralElementResistPercent : " + this.informations.stats.neutralElementResistPercent);
				Network.appendDebug("earthElementResistPercent : " + this.informations.stats.earthElementResistPercent);
				Network.appendDebug("waterElementResistPercent : " + this.informations.stats.waterElementResistPercent);
				Network.appendDebug("airElementResistPercent : " + this.informations.stats.airElementResistPercent);
				Network.appendDebug("fireElementResistPercent : " + this.informations.stats.fireElementResistPercent);
				Network.appendDebug("neutralElementReduction : " + this.informations.stats.neutralElementReduction);
				Network.appendDebug("earthElementReduction : " + this.informations.stats.earthElementReduction);
				Network.appendDebug("waterElementReduction : " + this.informations.stats.waterElementReduction);
				Network.appendDebug("airElementReduction : " + this.informations.stats.airElementReduction);
				Network.appendDebug("fireElementReduction : " + this.informations.stats.fireElementReduction);
				Network.appendDebug("criticalDamageFixedResist : " + this.informations.stats.criticalDamageFixedResist);
				Network.appendDebug("pushDamageFixedResist : " + this.informations.stats.pushDamageFixedResist);
				Network.appendDebug("pvpNeutralElementResistPercent : " + this.informations.stats.pvpNeutralElementResistPercent);
				Network.appendDebug("pvpEarthElementResistPercent : " + this.informations.stats.pvpEarthElementResistPercent);
				Network.appendDebug("pvpWaterElementResistPercent : " + this.informations.stats.pvpWaterElementResistPercent);
				Network.appendDebug("pvpAirElementResistPercent : " + this.informations.stats.pvpAirElementResistPercent);
				Network.appendDebug("pvpFireElementResistPercent : " + this.informations.stats.pvpFireElementResistPercent);
				Network.appendDebug("pvpNeutralElementReduction : " + this.informations.stats.pvpNeutralElementReduction);
				Network.appendDebug("pvpEarthElementReduction : " + this.informations.stats.pvpEarthElementReduction);
				Network.appendDebug("pvpWaterElementReduction : " + this.informations.stats.pvpWaterElementReduction);
				Network.appendDebug("pvpAirElementReduction : " + this.informations.stats.pvpAirElementReduction);
				Network.appendDebug("pvpFireElementReduction : " + this.informations.stats.pvpFireElementReduction);
				Network.appendDebug("dodgePALostProbability : " + this.informations.stats.dodgePALostProbability);
				Network.appendDebug("dodgePMLostProbability : " + this.informations.stats.dodgePMLostProbability);
				Network.appendDebug("tackleBlock : " + this.informations.stats.tackleBlock);
				Network.appendDebug("tackleEvade : " + this.informations.stats.tackleEvade);
				Network.appendDebug("fixedDamageReflection : " + this.informations.stats.fixedDamageReflection);
				Network.appendDebug("invisibilityState : " + this.informations.stats.invisibilityState);
				Network.appendDebug("meleeDamageReceivedPercent : " + this.informations.stats.meleeDamageReceivedPercent);
				Network.appendDebug("rangedDamageReceivedPercent : " + this.informations.stats.rangedDamageReceivedPercent);
				Network.appendDebug("weaponDamageReceivedPercent : " + this.informations.stats.weaponDamageReceivedPercent);
				Network.appendDebug("spellDamageReceivedPercent : " + this.informations.stats.spellDamageReceivedPercent);
	}
}
