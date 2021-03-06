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
import protocol.network.messages.game.chat.ChatAbstractClientMessage;

@SuppressWarnings("unused")
public class ChatClientPrivateMessage extends ChatAbstractClientMessage {
	public static final int ProtocolId = 851;

	private String receiver;

	public String getReceiver() { return this.receiver; }
	public void setReceiver(String receiver) { this.receiver = receiver; };

	public ChatClientPrivateMessage(){
	}

	public ChatClientPrivateMessage(String receiver){
		this.receiver = receiver;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeUTF(this.receiver);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.receiver = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
