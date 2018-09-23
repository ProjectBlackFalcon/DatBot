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
import protocol.network.types.game.context.roleplay.fight.arena.ArenaRanking;
import protocol.network.types.game.context.roleplay.fight.arena.ArenaLeagueRanking;

@SuppressWarnings("unused")
public class ArenaRankInfos extends NetworkMessage {
	public static final int ProtocolId = 499;

	private ArenaRanking ranking;
	private ArenaLeagueRanking leagueRanking;
	private int victoryCount;
	private int fightcount;
	private int numFightNeededForLadder;

	public ArenaRanking getRanking() { return this.ranking; }
	public void setRanking(ArenaRanking ranking) { this.ranking = ranking; };
	public ArenaLeagueRanking getLeagueRanking() { return this.leagueRanking; }
	public void setLeagueRanking(ArenaLeagueRanking leagueRanking) { this.leagueRanking = leagueRanking; };
	public int getVictoryCount() { return this.victoryCount; }
	public void setVictoryCount(int victoryCount) { this.victoryCount = victoryCount; };
	public int getFightcount() { return this.fightcount; }
	public void setFightcount(int fightcount) { this.fightcount = fightcount; };
	public int getNumFightNeededForLadder() { return this.numFightNeededForLadder; }
	public void setNumFightNeededForLadder(int numFightNeededForLadder) { this.numFightNeededForLadder = numFightNeededForLadder; };

	public ArenaRankInfos(){
	}

	public ArenaRankInfos(ArenaRanking ranking, ArenaLeagueRanking leagueRanking, int victoryCount, int fightcount, int numFightNeededForLadder){
		this.ranking = ranking;
		this.leagueRanking = leagueRanking;
		this.victoryCount = victoryCount;
		this.fightcount = fightcount;
		this.numFightNeededForLadder = numFightNeededForLadder;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			ranking.Serialize(writer);
			leagueRanking.Serialize(writer);
			writer.writeVarShort(this.victoryCount);
			writer.writeVarShort(this.fightcount);
			writer.writeShort(this.numFightNeededForLadder);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.ranking = new ArenaRanking();
			this.ranking.Deserialize(reader);
			this.leagueRanking = new ArenaLeagueRanking();
			this.leagueRanking.Deserialize(reader);
			this.victoryCount = reader.readVarShort();
			this.fightcount = reader.readVarShort();
			this.numFightNeededForLadder = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
