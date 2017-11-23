package protocol.network.messages.game.context.fight;

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
import protocol.network.types.game.context.fight.GameFightMinimalStats;

@SuppressWarnings("unused")
public class RefreshCharacterStatsMessage extends NetworkMessage {
	public static final int ProtocolId = 6699;

	public double fighterId;
	public GameFightMinimalStats stats;

	public RefreshCharacterStatsMessage(){
	}

	public RefreshCharacterStatsMessage(double fighterId, GameFightMinimalStats stats){
		this.fighterId = fighterId;
		this.stats = stats;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.fighterId);
			stats.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.fighterId = reader.readDouble();
			this.stats = new GameFightMinimalStats();
			this.stats.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("fighterId : " + this.fighterId);
		//Network.appendDebug("stats : " + this.stats);
	//}
}
