package protocol.network.types.game.interactive;

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
public class InteractiveElementSkill extends NetworkMessage {
	public static final int ProtocolId = 219;

	public int skillId;
	public int skillInstanceUid;

	public InteractiveElementSkill(){
	}

	public InteractiveElementSkill(int skillId, int skillInstanceUid){
		this.skillId = skillId;
		this.skillInstanceUid = skillInstanceUid;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.skillId);
			writer.writeInt(this.skillInstanceUid);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.skillId = reader.readVarInt();
			this.skillInstanceUid = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("skillId : " + this.skillId);
		//Network.appendDebug("skillInstanceUid : " + this.skillInstanceUid);
	//}
}
