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
public class ArenaRankInfos extends NetworkMessage {
	public static final int ProtocolId = 499;

	private int rank;
	private int bestRank;
	private int victoryCount;
	private int fightcount;
	private boolean validForLadder;

	public int getRank() { return this.rank; };
	public void setRank(int rank) { this.rank = rank; };
	public int getBestRank() { return this.bestRank; };
	public void setBestRank(int bestRank) { this.bestRank = bestRank; };
	public int getVictoryCount() { return this.victoryCount; };
	public void setVictoryCount(int victoryCount) { this.victoryCount = victoryCount; };
	public int getFightcount() { return this.fightcount; };
	public void setFightcount(int fightcount) { this.fightcount = fightcount; };
	public boolean isValidForLadder() { return this.validForLadder; };
	public void setValidForLadder(boolean validForLadder) { this.validForLadder = validForLadder; };

	public ArenaRankInfos(){
	}

	public ArenaRankInfos(int rank, int bestRank, int victoryCount, int fightcount, boolean validForLadder){
		this.rank = rank;
		this.bestRank = bestRank;
		this.victoryCount = victoryCount;
		this.fightcount = fightcount;
		this.validForLadder = validForLadder;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.rank);
			writer.writeVarShort(this.bestRank);
			writer.writeVarShort(this.victoryCount);
			writer.writeVarShort(this.fightcount);
			writer.writeBoolean(this.validForLadder);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.rank = reader.readVarShort();
			this.bestRank = reader.readVarShort();
			this.victoryCount = reader.readVarShort();
			this.fightcount = reader.readVarShort();
			this.validForLadder = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
