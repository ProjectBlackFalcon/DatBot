package protocol.network.messages.game.context.roleplay.party;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.party.AbstractPartyMessage;

@SuppressWarnings("unused")
public class PartyFollowStatusUpdateMessage extends AbstractPartyMessage {
	public static final int ProtocolId = 5581;

	private boolean success;
	private boolean isFollowed;
	private long followedId;

	public boolean isSuccess() { return this.success; };
	public void setSuccess(boolean success) { this.success = success; };
	public boolean isIsFollowed() { return this.isFollowed; };
	public void setIsFollowed(boolean isFollowed) { this.isFollowed = isFollowed; };
	public long getFollowedId() { return this.followedId; };
	public void setFollowedId(long followedId) { this.followedId = followedId; };

	public PartyFollowStatusUpdateMessage(){
	}

	public PartyFollowStatusUpdateMessage(boolean success, boolean isFollowed, long followedId){
		this.success = success;
		this.isFollowed = isFollowed;
		this.followedId = followedId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, success);
			flag = BooleanByteWrapper.SetFlag(1, flag, isFollowed);
			writer.writeByte(flag);
			writer.writeVarLong(this.followedId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			byte flag;
			flag = (byte) reader.readUnsignedByte();
			this.success = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.isFollowed = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.followedId = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
