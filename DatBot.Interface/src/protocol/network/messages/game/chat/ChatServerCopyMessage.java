package protocol.network.messages.game.chat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.chat.ChatAbstractServerMessage;

@SuppressWarnings("unused")
public class ChatServerCopyMessage extends ChatAbstractServerMessage {
	public static final int ProtocolId = 882;

	public long receiverId;
	public String receiverName;

	public ChatServerCopyMessage(){
	}

	public ChatServerCopyMessage(long receiverId, String receiverName){
		this.receiverId = receiverId;
		this.receiverName = receiverName;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.receiverId);
			writer.writeUTF(this.receiverName);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.receiverId = reader.readVarLong();
			this.receiverName = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("receiverId : " + this.receiverId);
		//Network.appendDebug("receiverName : " + this.receiverName);
	//}
}