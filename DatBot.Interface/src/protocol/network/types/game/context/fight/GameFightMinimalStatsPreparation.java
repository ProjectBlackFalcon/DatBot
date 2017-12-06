package protocol.network.types.game.context.fight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.fight.GameFightMinimalStats;

@SuppressWarnings("unused")
public class GameFightMinimalStatsPreparation extends GameFightMinimalStats {
	public static final int ProtocolId = 360;

	public int initiative;

	public GameFightMinimalStatsPreparation(){
	}

	public GameFightMinimalStatsPreparation(int initiative){
		this.initiative = initiative;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.initiative);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.initiative = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("initiative : " + this.initiative);
	//}
}
