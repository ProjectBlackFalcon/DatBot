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
public class ExchangeBidPriceMessage extends NetworkMessage {
	public static final int ProtocolId = 5755;

	private int genericId;
	private long averagePrice;

	public int getGenericId() { return this.genericId; };
	public void setGenericId(int genericId) { this.genericId = genericId; };
	public long getAveragePrice() { return this.averagePrice; };
	public void setAveragePrice(long averagePrice) { this.averagePrice = averagePrice; };

	public ExchangeBidPriceMessage(){
	}

	public ExchangeBidPriceMessage(int genericId, long averagePrice){
		this.genericId = genericId;
		this.averagePrice = averagePrice;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.genericId);
			writer.writeVarLong(this.averagePrice);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.genericId = reader.readVarShort();
			this.averagePrice = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
