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

	private String content;
	private int timestamp;
	private long memberId;
	private String memberName;

	public String getContent() { return this.content; }
	public void setContent(String content) { this.content = content; };
	public int getTimestamp() { return this.timestamp; }
	public void setTimestamp(int timestamp) { this.timestamp = timestamp; };
	public long getMemberId() { return this.memberId; }
	public void setMemberId(long memberId) { this.memberId = memberId; };
	public String getMemberName() { return this.memberName; }
	public void setMemberName(String memberName) { this.memberName = memberName; };

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
	}

}
