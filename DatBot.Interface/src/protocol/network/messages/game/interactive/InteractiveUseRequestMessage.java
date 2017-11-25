package protocol.network.messages.game.interactive;

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
public class InteractiveUseRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5001;

	public int elemId;
	public int skillInstanceUid;

	public InteractiveUseRequestMessage(){
	}

	public InteractiveUseRequestMessage(int elemId, int skillInstanceUid){
		this.elemId = elemId;
		this.skillInstanceUid = skillInstanceUid;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.elemId);
			writer.writeVarInt(this.skillInstanceUid);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.elemId = reader.readVarInt();
			this.skillInstanceUid = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("elemId : " + this.elemId);
		//Network.appendDebug("skillInstanceUid : " + this.skillInstanceUid);
	//}
}