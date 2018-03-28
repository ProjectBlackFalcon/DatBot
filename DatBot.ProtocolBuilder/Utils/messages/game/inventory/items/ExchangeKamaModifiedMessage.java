package protocol.network.messages.game.inventory.items;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.inventory.exchanges.ExchangeObjectMessage;

@SuppressWarnings("unused")
public class ExchangeKamaModifiedMessage extends ExchangeObjectMessage {
	public static final int ProtocolId = 5521;

	private long quantity;

	public long getQuantity() { return this.quantity; }
	public void setQuantity(long quantity) { this.quantity = quantity; };

	public ExchangeKamaModifiedMessage(){
	}

	public ExchangeKamaModifiedMessage(long quantity){
		this.quantity = quantity;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.quantity);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.quantity = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
