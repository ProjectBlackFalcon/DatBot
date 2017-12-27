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

	private long playerId;
	private String playerName;
	private int breed;
	private boolean sex;
	private int level;

	public long getPlayerId() { return this.playerId; };
	public void setPlayerId(long playerId) { this.playerId = playerId; };
	public String getPlayerName() { return this.playerName; };
	public void setPlayerName(String playerName) { this.playerName = playerName; };
	public int getBreed() { return this.breed; };
	public void setBreed(int breed) { this.breed = breed; };
	public boolean isSex() { return this.sex; };
	public void setSex(boolean sex) { this.sex = sex; };
	public int getLevel() { return this.level; };
	public void setLevel(int level) { this.level = level; };

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
			writer.writeVarShort(this.level);
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
			this.level = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
