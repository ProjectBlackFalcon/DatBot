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
public class InteractiveUseEndedMessage extends NetworkMessage {
	public static final int ProtocolId = 6112;

	public int elemId;
	public int skillId;

	public InteractiveUseEndedMessage(){
	}

	public InteractiveUseEndedMessage(int elemId, int skillId){
		this.elemId = elemId;
		this.skillId = skillId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.elemId);
			writer.writeVarShort(this.skillId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.elemId = reader.readVarInt();
			this.skillId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("elemId : " + this.elemId);
		//Network.appendDebug("skillId : " + this.skillId);
	//}
}