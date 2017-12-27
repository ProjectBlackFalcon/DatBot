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

	private int presetId;
	private int position;
	private int objUid;

	public int getPresetId() { return this.presetId; };
	public void setPresetId(int presetId) { this.presetId = presetId; };
	public int getPosition() { return this.position; };
	public void setPosition(int position) { this.position = position; };
	public int getObjUid() { return this.objUid; };
	public void setObjUid(int objUid) { this.objUid = objUid; };

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
	}

}
