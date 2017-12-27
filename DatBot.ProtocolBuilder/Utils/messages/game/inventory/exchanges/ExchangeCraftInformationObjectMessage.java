package protocol.network.messages.game.inventory.exchanges;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.inventory.exchanges.ExchangeCraftResultWithObjectIdMessage;

@SuppressWarnings("unused")
public class ExchangeCraftInformationObjectMessage extends ExchangeCraftResultWithObjectIdMessage {
	public static final int ProtocolId = 5794;

	private long playerId;

	public long getPlayerId() { return this.playerId; };
	public void setPlayerId(long playerId) { this.playerId = playerId; };

	public ExchangeCraftInformationObjectMessage(){
	}

	public ExchangeCraftInformationObjectMessage(long playerId){
		this.playerId = playerId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.playerId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.playerId = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
