package protocol.network.types.game.context.roleplay.party;

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
public class DungeonPartyFinderPlayer extends NetworkMessage {
	public static final int ProtocolId = 373;

	public long playerId;
	public String playerName;
	public int breed;
	public boolean sex;
	public int level;

	public DungeonPartyFinderPlayer(){
	}

	public DungeonPartyFinderPlayer(long playerId, String playerName, int breed, boolean sex, int level){
		this.playerId = playerId;
		this.playerName = playerName;
		this.breed = breed;
		this.sex = sex;
		this.level = level;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.playerId);
			writer.writeUTF(this.playerName);
			writer.writeByte(this.breed);
			writer.writeBoolean(this.sex);
			writer.writeByte(this.level);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.playerId = reader.readVarLong();
			this.playerName = reader.readUTF();
			this.breed = reader.readByte();
			this.sex = reader.readBoolean();
			this.level = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("playerId : " + this.playerId);
		//Network.appendDebug("playerName : " + this.playerName);
		//Network.appendDebug("breed : " + this.breed);
		//Network.appendDebug("sex : " + this.sex);
		//Network.appendDebug("level : " + this.level);
	//}
}
