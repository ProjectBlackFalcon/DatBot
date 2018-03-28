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
public class FriendUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 5924;

	private FriendInformations friendUpdated;

	public FriendInformations getFriendUpdated() { return this.friendUpdated; }
	public void setFriendUpdated(FriendInformations friendUpdated) { this.friendUpdated = friendUpdated; };

	public FriendUpdateMessage(){
	}

	public FriendUpdateMessage(FriendInformations friendUpdated){
		this.friendUpdated = friendUpdated;
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
			this.friendUpdated = (FriendInformations) ProtocolTypeManager.getInstance(reader.readShort());
			this.friendUpdated.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
