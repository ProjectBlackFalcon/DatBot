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
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class ArenaLeagueRanking extends NetworkMessage {
	public static final int ProtocolId = 553;

	private int rank;
	private int leagueId;
	private int leaguePoints;
	private int totalLeaguePoints;
	private int ladderPosition;

	public int getRank() { return this.rank; }
	public void setRank(int rank) { this.rank = rank; };
	public int getLeagueId() { return this.leagueId; }
	public void setLeagueId(int leagueId) { this.leagueId = leagueId; };
	public int getLeaguePoints() { return this.leaguePoints; }
	public void setLeaguePoints(int leaguePoints) { this.leaguePoints = leaguePoints; };
	public int getTotalLeaguePoints() { return this.totalLeaguePoints; }
	public void setTotalLeaguePoints(int totalLeaguePoints) { this.totalLeaguePoints = totalLeaguePoints; };
	public int getLadderPosition() { return this.ladderPosition; }
	public void setLadderPosition(int ladderPosition) { this.ladderPosition = ladderPosition; };

	public ArenaLeagueRanking(){
	}

	public ArenaLeagueRanking(int rank, int leagueId, int leaguePoints, int totalLeaguePoints, int ladderPosition){
		this.rank = rank;
		this.leagueId = leagueId;
		this.leaguePoints = leaguePoints;
		this.totalLeaguePoints = totalLeaguePoints;
		this.ladderPosition = ladderPosition;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.rank);
			writer.writeVarShort(this.leagueId);
			writer.writeVarShort(this.leaguePoints);
			writer.writeVarShort(this.totalLeaguePoints);
			writer.writeInt(this.ladderPosition);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.rank = reader.readVarShort();
			this.leagueId = reader.readVarShort();
			this.leaguePoints = reader.readVarShort();
			this.totalLeaguePoints = reader.readVarShort();
			this.ladderPosition = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
