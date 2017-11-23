package protocol.network.messages.game.chat.smiley;

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
public class ChatSmileyMessage extends NetworkMessage {
	public static final int ProtocolId = 801;

	public double entityId;
	public int smileyId;
	public int accountId;

	public ChatSmileyMessage(){
	}

	public ChatSmileyMessage(double entityId, int smileyId, int accountId){
		this.entityId = entityId;
		this.smileyId = smileyId;
		this.accountId = accountId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.entityId);
			writer.writeVarShort(this.smileyId);
			writer.writeInt(this.accountId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.entityId = reader.readDouble();
			this.smileyId = reader.readVarShort();
			this.accountId = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("entityId : " + this.entityId);
		//Network.appendDebug("smileyId : " + this.smileyId);
		//Network.appendDebug("accountId : " + this.accountId);
	//}
}
