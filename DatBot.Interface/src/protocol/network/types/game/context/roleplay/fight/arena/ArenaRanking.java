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
public class ArenaRanking extends NetworkMessage {
	public static final int ProtocolId = 554;

	private int rank;
	private int bestRank;

	public int getRank() { return this.rank; }
	public void setRank(int rank) { this.rank = rank; };
	public int getBestRank() { return this.bestRank; }
	public void setBestRank(int bestRank) { this.bestRank = bestRank; };

	public ArenaRanking(){
	}

	public ArenaRanking(int rank, int bestRank){
		this.rank = rank;
		this.bestRank = bestRank;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.rank);
			writer.writeVarShort(this.bestRank);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.rank = reader.readVarShort();
			this.bestRank = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
