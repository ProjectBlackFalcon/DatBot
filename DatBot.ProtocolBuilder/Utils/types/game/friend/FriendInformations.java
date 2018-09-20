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

	private int playerState;
	private int lastConnection;
	private int achievementPoints;
	private int leagueId;
	private int ladderPosition;

	public int getPlayerState() { return this.playerState; }
	public void setPlayerState(int playerState) { this.playerState = playerState; };
	public int getLastConnection() { return this.lastConnection; }
	public void setLastConnection(int lastConnection) { this.lastConnection = lastConnection; };
	public int getAchievementPoints() { return this.achievementPoints; }
	public void setAchievementPoints(int achievementPoints) { this.achievementPoints = achievementPoints; };
	public int getLeagueId() { return this.leagueId; }
	public void setLeagueId(int leagueId) { this.leagueId = leagueId; };
	public int getLadderPosition() { return this.ladderPosition; }
	public void setLadderPosition(int ladderPosition) { this.ladderPosition = ladderPosition; };

	public FriendInformations(){
	}

	public FriendInformations(int playerState, int lastConnection, int achievementPoints, int leagueId, int ladderPosition){
		this.playerState = playerState;
		this.lastConnection = lastConnection;
		this.achievementPoints = achievementPoints;
		this.leagueId = leagueId;
		this.ladderPosition = ladderPosition;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.playerState);
			writer.writeVarShort(this.lastConnection);
			writer.writeInt(this.achievementPoints);
			writer.writeVarShort(this.leagueId);
			writer.writeInt(this.ladderPosition);
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
			this.leagueId = reader.readVarShort();
			this.ladderPosition = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
