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
import protocol.network.types.game.friend.IgnoredInformations;

@SuppressWarnings("unused")
public class IgnoredOnlineInformations extends IgnoredInformations {
	public static final int ProtocolId = 105;

	public long playerId;
	public String playerName;
	public int breed;
	public boolean sex;

	public IgnoredOnlineInformations(){
	}

	public IgnoredOnlineInformations(long playerId, String playerName, int breed, boolean sex){
		this.playerId = playerId;
		this.playerName = playerName;
		this.breed = breed;
		this.sex = sex;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.playerId);
			writer.writeUTF(this.playerName);
			writer.writeByte(this.breed);
			writer.writeBoolean(this.sex);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.playerId = reader.readVarLong();
			this.playerName = reader.readUTF();
			this.breed = reader.readByte();
			this.sex = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("playerId : " + this.playerId);
		//Network.appendDebug("playerName : " + this.playerName);
		//Network.appendDebug("breed : " + this.breed);
		//Network.appendDebug("sex : " + this.sex);
	//}
}
