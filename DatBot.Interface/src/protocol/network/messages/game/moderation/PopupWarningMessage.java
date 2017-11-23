package protocol.network.messages.game.moderation;

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
public class PopupWarningMessage extends NetworkMessage {
	public static final int ProtocolId = 6134;

	public int lockDuration;
	public String author;
	public String content;

	public PopupWarningMessage(){
	}

	public PopupWarningMessage(int lockDuration, String author, String content){
		this.lockDuration = lockDuration;
		this.author = author;
		this.content = content;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.lockDuration);
			writer.writeUTF(this.author);
			writer.writeUTF(this.content);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.lockDuration = reader.readByte();
			this.author = reader.readUTF();
			this.content = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("lockDuration : " + this.lockDuration);
		//Network.appendDebug("author : " + this.author);
		//Network.appendDebug("content : " + this.content);
	//}
}
