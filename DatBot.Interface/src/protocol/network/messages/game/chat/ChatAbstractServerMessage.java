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
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class ChatAbstractServerMessage extends NetworkMessage {
	public static final int ProtocolId = 880;

	public int channel;
	public String content;
	public int timestamp;
	public String fingerprint;

	public ChatAbstractServerMessage(){
	}

	public ChatAbstractServerMessage(int channel, String content, int timestamp, String fingerprint){
		this.channel = channel;
		this.content = content;
		this.timestamp = timestamp;
		this.fingerprint = fingerprint;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.channel);
			writer.writeUTF(this.content);
			writer.writeInt(this.timestamp);
			writer.writeUTF(this.fingerprint);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.channel = reader.readByte();
			this.content = reader.readUTF();
			this.timestamp = reader.readInt();
			this.fingerprint = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("channel : " + this.channel);
		//Network.appendDebug("content : " + this.content);
		//Network.appendDebug("timestamp : " + this.timestamp);
		//Network.appendDebug("fingerprint : " + this.fingerprint);
	//}
}
