package protocol.network.messages.game.inventory.preset;

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
public class IdolsPresetUseMessage extends NetworkMessage {
	public static final int ProtocolId = 6615;

	public int presetId;
	public boolean party;

	public IdolsPresetUseMessage(){
	}

	public IdolsPresetUseMessage(int presetId, boolean party){
		this.presetId = presetId;
		this.party = party;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.presetId);
			writer.writeBoolean(this.party);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.presetId = reader.readByte();
			this.party = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("presetId : " + this.presetId);
		//Network.appendDebug("party : " + this.party);
	//}
}
