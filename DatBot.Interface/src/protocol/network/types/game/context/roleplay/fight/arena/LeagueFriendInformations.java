package protocol.network.types.game.context.roleplay.fight.arena;

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
public class LeagueFriendInformations extends AbstractContactInformations {
	public static final int ProtocolId = 555;

	private long playerId;
	private String playerName;
	private int breed;
	private boolean sex;
	private int level;
	private int leagueId;
	private int totalLeaguePoints;
	private int ladderPosition;

	public long getPlayerId() { return this.playerId; }
	public void setPlayerId(long playerId) { this.playerId = playerId; };
	public String getPlayerName() { return this.playerName; }
	public void setPlayerName(String playerName) { this.playerName = playerName; };
	public int getBreed() { return this.breed; }
	public void setBreed(int breed) { this.breed = breed; };
	public boolean isSex() { return this.sex; }
	public void setSex(boolean sex) { this.sex = sex; };
	public int getLevel() { return this.level; }
	public void setLevel(int level) { this.level = level; };
	public int getLeagueId() { return this.leagueId; }
	public void setLeagueId(int leagueId) { this.leagueId = leagueId; };
	public int getTotalLeaguePoints() { return this.totalLeaguePoints; }
	public void setTotalLeaguePoints(int totalLeaguePoints) { this.totalLeaguePoints = totalLeaguePoints; };
	public int getLadderPosition() { return this.ladderPosition; }
	public void setLadderPosition(int ladderPosition) { this.ladderPosition = ladderPosition; };

	public LeagueFriendInformations(){
	}

	public LeagueFriendInformations(long playerId, String playerName, int breed, boolean sex, int level, int leagueId, int totalLeaguePoints, int ladderPosition){
		this.playerId = playerId;
		this.playerName = playerName;
		this.breed = breed;
		this.sex = sex;
		this.level = level;
		this.leagueId = leagueId;
		this.totalLeaguePoints = totalLeaguePoints;
		this.ladderPosition = ladderPosition;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.playerId);
			writer.writeUTF(this.playerName);
			writer.writeByte(this.breed);
			writer.writeBoolean(this.sex);
			writer.writeVarShort(this.level);
			writer.writeVarShort(this.leagueId);
			writer.writeVarShort(this.totalLeaguePoints);
			writer.writeInt(this.ladderPosition);
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
			this.level = reader.readVarShort();
			this.leagueId = reader.readVarShort();
			this.totalLeaguePoints = reader.readVarShort();
			this.ladderPosition = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
