package protocol.network.messages.game.character.stats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.character.stats.UpdateLifePointsMessage;

@SuppressWarnings("unused")
public class LifePointsRegenEndMessage extends UpdateLifePointsMessage {
	public static final int ProtocolId = 5686;

	public int lifePointsGained;

	public LifePointsRegenEndMessage(){
	}

	public LifePointsRegenEndMessage(int lifePointsGained){
		this.lifePointsGained = lifePointsGained;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.lifePointsGained);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.lifePointsGained = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("lifePointsGained : " + this.lifePointsGained);
	//}
}
