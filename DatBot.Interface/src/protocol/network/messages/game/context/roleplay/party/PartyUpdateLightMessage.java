package protocol.network.messages.game.context.roleplay.party;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.party.AbstractPartyEventMessage;

@SuppressWarnings("unused")
public class PartyUpdateLightMessage extends AbstractPartyEventMessage {
	public static final int ProtocolId = 6054;

	public long id;
	public int lifePoints;
	public int maxLifePoints;
	public int prospecting;
	public int regenRate;

	public PartyUpdateLightMessage(){
	}

	public PartyUpdateLightMessage(long id, int lifePoints, int maxLifePoints, int prospecting, int regenRate){
		this.id = id;
		this.lifePoints = lifePoints;
		this.maxLifePoints = maxLifePoints;
		this.prospecting = prospecting;
		this.regenRate = regenRate;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.id);
			writer.writeVarInt(this.lifePoints);
			writer.writeVarInt(this.maxLifePoints);
			writer.writeVarShort(this.prospecting);
			writer.writeByte(this.regenRate);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.id = reader.readVarLong();
			this.lifePoints = reader.readVarInt();
			this.maxLifePoints = reader.readVarInt();
			this.prospecting = reader.readVarShort();
			this.regenRate = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("id : " + this.id);
		//Network.appendDebug("lifePoints : " + this.lifePoints);
		//Network.appendDebug("maxLifePoints : " + this.maxLifePoints);
		//Network.appendDebug("prospecting : " + this.prospecting);
		//Network.appendDebug("regenRate : " + this.regenRate);
	//}
}
