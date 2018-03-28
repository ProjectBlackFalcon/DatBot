package protocol.network.types.game.presets;

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
public class ItemForPreset extends NetworkMessage {
	public static final int ProtocolId = 540;

	private int position;
	private int objGid;
	private int objUid;

	public int getPosition() { return this.position; }
	public void setPosition(int position) { this.position = position; };
	public int getObjGid() { return this.objGid; }
	public void setObjGid(int objGid) { this.objGid = objGid; };
	public int getObjUid() { return this.objUid; }
	public void setObjUid(int objUid) { this.objUid = objUid; };

	public ItemForPreset(){
	}

	public ItemForPreset(int position, int objGid, int objUid){
		this.position = position;
		this.objGid = objGid;
		this.objUid = objUid;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.position);
			writer.writeVarShort(this.objGid);
			writer.writeVarInt(this.objUid);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.position = reader.readShort();
			this.objGid = reader.readVarShort();
			this.objUid = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
