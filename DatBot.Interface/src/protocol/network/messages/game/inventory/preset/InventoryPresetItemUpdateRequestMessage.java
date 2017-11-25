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
public class InventoryPresetItemUpdateRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6210;

	public int presetId;
	public int position;
	public int objUid;

	public InventoryPresetItemUpdateRequestMessage(){
	}

	public InventoryPresetItemUpdateRequestMessage(int presetId, int position, int objUid){
		this.presetId = presetId;
		this.position = position;
		this.objUid = objUid;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.presetId);
			writer.writeByte(this.position);
			writer.writeVarInt(this.objUid);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.presetId = reader.readByte();
			this.position = reader.readByte();
			this.objUid = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("presetId : " + this.presetId);
		//Network.appendDebug("position : " + this.position);
		//Network.appendDebug("objUid : " + this.objUid);
	//}
}