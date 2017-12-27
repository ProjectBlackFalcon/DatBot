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
public class ExchangeCraftPaymentModifiedMessage extends NetworkMessage {
	public static final int ProtocolId = 6578;

	private long goldSum;

	public long getGoldSum() { return this.goldSum; };
	public void setGoldSum(long goldSum) { this.goldSum = goldSum; };

	public ExchangeCraftPaymentModifiedMessage(){
	}

	public ExchangeCraftPaymentModifiedMessage(long goldSum){
		this.goldSum = goldSum;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.goldSum);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.goldSum = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
