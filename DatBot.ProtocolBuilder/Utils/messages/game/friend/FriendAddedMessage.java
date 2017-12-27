package protocol.network.messages.game.friend;

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
import protocol.network.types.game.friend.FriendInformations;

@SuppressWarnings("unused")
public class FriendAddedMessage extends NetworkMessage {
	public static final int ProtocolId = 5599;

	private FriendInformations friendAdded;

	public FriendInformations getFriendAdded() { return this.friendAdded; };
	public void setFriendAdded(FriendInformations friendAdded) { this.friendAdded = friendAdded; };

	public FriendAddedMessage(){
	}

	public FriendAddedMessage(FriendInformations friendAdded){
		this.friendAdded = friendAdded;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(FriendInformations.ProtocolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.friendAdded = (FriendInformations) ProtocolTypeManager.getInstance(reader.readShort());
			this.friendAdded.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
