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
public class ExchangeSellMessage extends NetworkMessage {
	public static final int ProtocolId = 5778;

	private int objectToSellId;
	private int quantity;

	public int getObjectToSellId() { return this.objectToSellId; };
	public void setObjectToSellId(int objectToSellId) { this.objectToSellId = objectToSellId; };
	public int getQuantity() { return this.quantity; };
	public void setQuantity(int quantity) { this.quantity = quantity; };

	public ExchangeSellMessage(){
	}

	public ExchangeSellMessage(int objectToSellId, int quantity){
		this.objectToSellId = objectToSellId;
		this.quantity = quantity;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.objectToSellId);
			writer.writeVarInt(this.quantity);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.objectToSellId = reader.readVarInt();
			this.quantity = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
