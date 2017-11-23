package protocol.network.types.game.inventory.preset;

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
public class PresetItem extends NetworkMessage {
	public static final int ProtocolId = 354;

	public int position;
	public int objGid;
	public int objUid;

	public PresetItem(){
	}

	public PresetItem(int position, int objGid, int objUid){
		this.position = position;
		this.objGid = objGid;
		this.objUid = objUid;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.position);
			writer.writeVarShort(this.objGid);
			writer.writeVarInt(this.objUid);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.position = reader.readByte();
			this.objGid = reader.readVarShort();
			this.objUid = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("position : " + this.position);
		//Network.appendDebug("objGid : " + this.objGid);
		//Network.appendDebug("objUid : " + this.objUid);
	//}
}
