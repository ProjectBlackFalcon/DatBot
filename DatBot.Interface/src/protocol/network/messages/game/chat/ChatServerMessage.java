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
	private String prefix;
	private int senderAccountId;

	public double getSenderId() { return this.senderId; }
	public void setSenderId(double senderId) { this.senderId = senderId; };
	public String getSenderName() { return this.senderName; }
	public void setSenderName(String senderName) { this.senderName = senderName; };
	public String getPrefix() { return this.prefix; }
	public void setPrefix(String prefix) { this.prefix = prefix; };
	public int getSenderAccountId() { return this.senderAccountId; }
	public void setSenderAccountId(int senderAccountId) { this.senderAccountId = senderAccountId; };

	public ChatServerMessage(){
	}

	public ChatServerMessage(double senderId, String senderName, String prefix, int senderAccountId){
		this.senderId = senderId;
		this.senderName = senderName;
		this.prefix = prefix;
		this.senderAccountId = senderAccountId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeDouble(this.senderId);
			writer.writeUTF(this.senderName);
			writer.writeUTF(this.prefix);
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
			this.prefix = reader.readUTF();
			this.senderAccountId = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
