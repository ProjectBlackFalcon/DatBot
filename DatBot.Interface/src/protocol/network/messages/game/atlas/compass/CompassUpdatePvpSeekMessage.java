package protocol.network.messages.game.atlas.compass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.atlas.compass.CompassUpdateMessage;

@SuppressWarnings("unused")
public class CompassUpdatePvpSeekMessage extends CompassUpdateMessage {
	public static final int ProtocolId = 6013;

	private long memberId;
	private String memberName;

	public long getMemberId() { return this.memberId; }
	public void setMemberId(long memberId) { this.memberId = memberId; };
	public String getMemberName() { return this.memberName; }
	public void setMemberName(String memberName) { this.memberName = memberName; };

	public CompassUpdatePvpSeekMessage(){
	}

	public CompassUpdatePvpSeekMessage(long memberId, String memberName){
		this.memberId = memberId;
		this.memberName = memberName;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.memberId);
			writer.writeUTF(this.memberName);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.memberId = reader.readVarLong();
			this.memberName = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
