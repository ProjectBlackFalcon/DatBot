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
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class ExchangeStartOkNpcTradeMessage extends NetworkMessage {
	public static final int ProtocolId = 5785;

	private double npcId;

	public double getNpcId() { return this.npcId; }
	public void setNpcId(double npcId) { this.npcId = npcId; };

	public ExchangeStartOkNpcTradeMessage(){
	}

	public ExchangeStartOkNpcTradeMessage(double npcId){
		this.npcId = npcId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.npcId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.npcId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
