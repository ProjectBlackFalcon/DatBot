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
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectMoveMessage;

@SuppressWarnings("unused")
public class ExchangeObjectMovePricedMessage extends ExchangeObjectMoveMessage {
	public static final int ProtocolId = 5514;

	private long price;

	public long getPrice() { return this.price; }
	public void setPrice(long price) { this.price = price; };

	public ExchangeObjectMovePricedMessage(){
	}

	public ExchangeObjectMovePricedMessage(long price){
		this.price = price;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.price);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.price = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
