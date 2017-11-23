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

	public int rank;
	public int bestRank;
	public int victoryCount;
	public int fightcount;

	public ArenaRankInfos(){
	}

	public ArenaRankInfos(int rank, int bestRank, int victoryCount, int fightcount){
		this.rank = rank;
		this.bestRank = bestRank;
		this.victoryCount = victoryCount;
		this.fightcount = fightcount;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.rank);
			writer.writeVarShort(this.bestRank);
			writer.writeVarShort(this.victoryCount);
			writer.writeVarShort(this.fightcount);
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
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("rank : " + this.rank);
		//Network.appendDebug("bestRank : " + this.bestRank);
		//Network.appendDebug("victoryCount : " + this.victoryCount);
		//Network.appendDebug("fightcount : " + this.fightcount);
	//}
}
