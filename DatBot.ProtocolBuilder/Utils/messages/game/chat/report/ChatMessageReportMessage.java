package protocol.network.messages.game.chat.report;

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
public class ChatMessageReportMessage extends NetworkMessage {
	public static final int ProtocolId = 821;

	private String senderName;
	private String content;
	private int timestamp;
	private int channel;
	private String fingerprint;
	private int reason;

	public String getSenderName() { return this.senderName; };
	public void setSenderName(String senderName) { this.senderName = senderName; };
	public String getContent() { return this.content; };
	public void setContent(String content) { this.content = content; };
	public int getTimestamp() { return this.timestamp; };
	public void setTimestamp(int timestamp) { this.timestamp = timestamp; };
	public int getChannel() { return this.channel; };
	public void setChannel(int channel) { this.channel = channel; };
	public String getFingerprint() { return this.fingerprint; };
	public void setFingerprint(String fingerprint) { this.fingerprint = fingerprint; };
	public int getReason() { return this.reason; };
	public void setReason(int reason) { this.reason = reason; };

	public ChatMessageReportMessage(){
	}

	public ChatMessageReportMessage(String senderName, String content, int timestamp, int channel, String fingerprint, int reason){
		this.senderName = senderName;
		this.content = content;
		this.timestamp = timestamp;
		this.channel = channel;
		this.fingerprint = fingerprint;
		this.reason = reason;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.senderName);
			writer.writeUTF(this.content);
			writer.writeInt(this.timestamp);
			writer.writeByte(this.channel);
			writer.writeUTF(this.fingerprint);
			writer.writeByte(this.reason);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.senderName = reader.readUTF();
			this.content = reader.readUTF();
			this.timestamp = reader.readInt();
			this.channel = reader.readByte();
			this.fingerprint = reader.readUTF();
			this.reason = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
