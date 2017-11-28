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
//		append();
	}

	private void append(){
		Network.append("contextualId : " + this.informations.contextualId);
		Network.append("teamId : " + this.informations.teamId);
		Network.append("wave : " + this.informations.wave);
		Network.append("alive : " + this.informations.alive);
		Network.append("cellId : " + this.informations.disposition.cellId);
//		for (Integer i : this.informations.previousPositions) {
//			Network.appendDebug("previousPositions : " + i);
//		}
		Network.append("lifePoints : " + this.informations.stats.lifePoints);
				Network.append("maxLifePoints : " + this.informations.stats.maxLifePoints);
				Network.append("baseMaxLifePoints : " + this.informations.stats.baseMaxLifePoints);
				Network.append("permanentDamagePercent : " + this.informations.stats.permanentDamagePercent);
				Network.append("shieldPoints : " + this.informations.stats.shieldPoints);
				Network.append("actionPoints : " + this.informations.stats.actionPoints);
				Network.append("maxActionPoints : " + this.informations.stats.maxActionPoints);
				Network.append("movementPoints : " + this.informations.stats.movementPoints);
				Network.append("maxMovementPoints : " + this.informations.stats.maxMovementPoints);
				Network.append("summoner : " + this.informations.stats.summoner);
				Network.append("summoned : " + this.informations.stats.summoned);
				Network.append("neutralElementResistPercent : " + this.informations.stats.neutralElementResistPercent);
				Network.append("earthElementResistPercent : " + this.informations.stats.earthElementResistPercent);
				Network.append("waterElementResistPercent : " + this.informations.stats.waterElementResistPercent);
				Network.append("airElementResistPercent : " + this.informations.stats.airElementResistPercent);
				Network.append("fireElementResistPercent : " + this.informations.stats.fireElementResistPercent);
				Network.append("neutralElementReduction : " + this.informations.stats.neutralElementReduction);
				Network.append("earthElementReduction : " + this.informations.stats.earthElementReduction);
				Network.append("waterElementReduction : " + this.informations.stats.waterElementReduction);
				Network.append("airElementReduction : " + this.informations.stats.airElementReduction);
				Network.append("fireElementReduction : " + this.informations.stats.fireElementReduction);
				Network.append("criticalDamageFixedResist : " + this.informations.stats.criticalDamageFixedResist);
				Network.append("pushDamageFixedResist : " + this.informations.stats.pushDamageFixedResist);
				Network.append("pvpNeutralElementResistPercent : " + this.informations.stats.pvpNeutralElementResistPercent);
				Network.append("pvpEarthElementResistPercent : " + this.informations.stats.pvpEarthElementResistPercent);
				Network.append("pvpWaterElementResistPercent : " + this.informations.stats.pvpWaterElementResistPercent);
				Network.append("pvpAirElementResistPercent : " + this.informations.stats.pvpAirElementResistPercent);
				Network.append("pvpFireElementResistPercent : " + this.informations.stats.pvpFireElementResistPercent);
				Network.append("pvpNeutralElementReduction : " + this.informations.stats.pvpNeutralElementReduction);
				Network.append("pvpEarthElementReduction : " + this.informations.stats.pvpEarthElementReduction);
				Network.append("pvpWaterElementReduction : " + this.informations.stats.pvpWaterElementReduction);
				Network.append("pvpAirElementReduction : " + this.informations.stats.pvpAirElementReduction);
				Network.append("pvpFireElementReduction : " + this.informations.stats.pvpFireElementReduction);
				Network.append("dodgePALostProbability : " + this.informations.stats.dodgePALostProbability);
				Network.append("dodgePMLostProbability : " + this.informations.stats.dodgePMLostProbability);
				Network.append("tackleBlock : " + this.informations.stats.tackleBlock);
				Network.append("tackleEvade : " + this.informations.stats.tackleEvade);
				Network.append("fixedDamageReflection : " + this.informations.stats.fixedDamageReflection);
				Network.append("invisibilityState : " + this.informations.stats.invisibilityState);
				Network.append("meleeDamageReceivedPercent : " + this.informations.stats.meleeDamageReceivedPercent);
				Network.append("rangedDamageReceivedPercent : " + this.informations.stats.rangedDamageReceivedPercent);
				Network.append("weaponDamageReceivedPercent : " + this.informations.stats.weaponDamageReceivedPercent);
				Network.append("spellDamageReceivedPercent : " + this.informations.stats.spellDamageReceivedPercent);
	}
}
