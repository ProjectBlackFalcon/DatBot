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

	public int objectToBuyId;
	public int quantity;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("objectToBuyId : " + this.objectToBuyId);
		//Network.appendDebug("quantity : " + this.quantity);
	//}
}
