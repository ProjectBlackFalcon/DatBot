package protocol.network.messages.game.context.roleplay.party.entity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.party.PartyUpdateLightMessage;

@SuppressWarnings("unused")
public class PartyEntityUpdateLightMessage extends PartyUpdateLightMessage {
	public static final int ProtocolId = 6781;

	private int indexId;

	public int getIndexId() { return this.indexId; }
	public void setIndexId(int indexId) { this.indexId = indexId; };

	public PartyEntityUpdateLightMessage(){
	}

	public PartyEntityUpdateLightMessage(int indexId){
		this.indexId = indexId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.indexId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.indexId = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
