package protocol.network.types.game.friend;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.friend.AbstractContactInformations;

@SuppressWarnings("unused")
public class FriendInformations extends AbstractContactInformations {
	public static final int ProtocolId = 78;

	public int playerState;
	public int lastConnection;
	public int achievementPoints;

	public FriendInformations(){
	}

	public FriendInformations(int playerState, int lastConnection, int achievementPoints){
		this.playerState = playerState;
		this.lastConnection = lastConnection;
		this.achievementPoints = achievementPoints;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.playerState);
			writer.writeVarShort(this.lastConnection);
			writer.writeInt(this.achievementPoints);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.playerState = reader.readByte();
			this.lastConnection = reader.readVarShort();
			this.achievementPoints = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("playerState : " + this.playerState);
		//Network.appendDebug("lastConnection : " + this.lastConnection);
		//Network.appendDebug("achievementPoints : " + this.achievementPoints);
	//}
}