package protocol.network.messages.game.social;

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
public class SocialNoticeMessage extends NetworkMessage {
	public static final int ProtocolId = 6688;

	public String content;
	public int timestamp;
	public long memberId;
	public String memberName;

	public SocialNoticeMessage(){
	}

	public SocialNoticeMessage(String content, int timestamp, long memberId, String memberName){
		this.content = content;
		this.timestamp = timestamp;
		this.memberId = memberId;
		this.memberName = memberName;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.content);
			writer.writeInt(this.timestamp);
			writer.writeVarLong(this.memberId);
			writer.writeUTF(this.memberName);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.content = reader.readUTF();
			this.timestamp = reader.readInt();
			this.memberId = reader.readVarLong();
			this.memberName = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("content : " + this.content);
		//Network.appendDebug("timestamp : " + this.timestamp);
		//Network.appendDebug("memberId : " + this.memberId);
		//Network.appendDebug("memberName : " + this.memberName);
	//}
}