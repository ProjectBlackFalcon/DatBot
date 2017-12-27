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
public class ExchangeBuyMessage extends NetworkMessage {
	public static final int ProtocolId = 5774;

	private int objectToBuyId;
	private int quantity;

	public int getObjectToBuyId() { return this.objectToBuyId; };
	public void setObjectToBuyId(int objectToBuyId) { this.objectToBuyId = objectToBuyId; };
	public int getQuantity() { return this.quantity; };
	public void setQuantity(int quantity) { this.quantity = quantity; };

	public ExchangeBuyMessage(){
	}

	public ExchangeBuyMessage(int objectToBuyId, int quantity){
		this.objectToBuyId = objectToBuyId;
		this.quantity = quantity;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.objectToBuyId);
			writer.writeVarInt(this.quantity);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.objectToBuyId = reader.readVarInt();
			this.quantity = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
