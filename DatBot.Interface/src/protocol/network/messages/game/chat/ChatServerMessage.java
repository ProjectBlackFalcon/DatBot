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
public class ChatServerMessage extends ChatAbstractServerMessage {
	public static final int ProtocolId = 881;

	private double senderId;
	private String senderName;
	private int senderAccountId;

	public double getSenderId() { return this.senderId; }
	public void setSenderId(double senderId) { this.senderId = senderId; };
	public String getSenderName() { return this.senderName; }
	public void setSenderName(String senderName) { this.senderName = senderName; };
	public int getSenderAccountId() { return this.senderAccountId; }
	public void setSenderAccountId(int senderAccountId) { this.senderAccountId = senderAccountId; };

	public ChatServerMessage(){
	}

	public ChatServerMessage(double senderId, String senderName, int senderAccountId){
		this.senderId = senderId;
		this.senderName = senderName;
		this.senderAccountId = senderAccountId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeDouble(this.senderId);
			writer.writeUTF(this.senderName);
			writer.writeInt(this.senderAccountId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.senderId = reader.readDouble();
			this.senderName = reader.readUTF();
			this.senderAccountId = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
