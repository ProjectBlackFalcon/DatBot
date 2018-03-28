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
public class FriendsListMessage extends NetworkMessage {
	public static final int ProtocolId = 4002;

	private List<FriendInformations> friendsList;

	public List<FriendInformations> getFriendsList() { return this.friendsList; }
	public void setFriendsList(List<FriendInformations> friendsList) { this.friendsList = friendsList; };

	public FriendsListMessage(){
	}

	public FriendsListMessage(List<FriendInformations> friendsList){
		this.friendsList = friendsList;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.friendsList.size());
			int _loc2_ = 0;
			while( _loc2_ < this.friendsList.size()){
				writer.writeShort(FriendInformations.ProtocolId);
				this.friendsList.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.friendsList = new ArrayList<FriendInformations>();
			while( _loc3_ <  _loc2_){
				FriendInformations _loc15_ = (FriendInformations) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.friendsList.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
