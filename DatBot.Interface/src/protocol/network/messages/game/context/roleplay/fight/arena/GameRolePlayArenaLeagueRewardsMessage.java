package protocol.network.messages.game.context.roleplay.fight.arena;

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
public class GameRolePlayArenaLeagueRewardsMessage extends NetworkMessage {
	public static final int ProtocolId = 6785;

	private int seasonId;
	private int leagueId;
	private int ladderPosition;
	private boolean endSeasonReward;

	public int getSeasonId() { return this.seasonId; }
	public void setSeasonId(int seasonId) { this.seasonId = seasonId; };
	public int getLeagueId() { return this.leagueId; }
	public void setLeagueId(int leagueId) { this.leagueId = leagueId; };
	public int getLadderPosition() { return this.ladderPosition; }
	public void setLadderPosition(int ladderPosition) { this.ladderPosition = ladderPosition; };
	public boolean isEndSeasonReward() { return this.endSeasonReward; }
	public void setEndSeasonReward(boolean endSeasonReward) { this.endSeasonReward = endSeasonReward; };

	public GameRolePlayArenaLeagueRewardsMessage(){
	}

	public GameRolePlayArenaLeagueRewardsMessage(int seasonId, int leagueId, int ladderPosition, boolean endSeasonReward){
		this.seasonId = seasonId;
		this.leagueId = leagueId;
		this.ladderPosition = ladderPosition;
		this.endSeasonReward = endSeasonReward;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.seasonId);
			writer.writeVarShort(this.leagueId);
			writer.writeInt(this.ladderPosition);
			writer.writeBoolean(this.endSeasonReward);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.seasonId = reader.readVarShort();
			this.leagueId = reader.readVarShort();
			this.ladderPosition = reader.readInt();
			this.endSeasonReward = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
