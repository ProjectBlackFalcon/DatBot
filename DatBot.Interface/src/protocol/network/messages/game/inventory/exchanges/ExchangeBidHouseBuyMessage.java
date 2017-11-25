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
public class ExchangeBidHouseBuyMessage extends NetworkMessage {
	public static final int ProtocolId = 5804;

	public int uid;
	public int qty;
	public long price;

	public ExchangeBidHouseBuyMessage(){
	}

	public ExchangeBidHouseBuyMessage(int uid, int qty, long price){
		this.uid = uid;
		this.qty = qty;
		this.price = price;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.uid);
			writer.writeVarInt(this.qty);
			writer.writeVarLong(this.price);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.uid = reader.readVarInt();
			this.qty = reader.readVarInt();
			this.price = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("uid : " + this.uid);
		//Network.appendDebug("qty : " + this.qty);
		//Network.appendDebug("price : " + this.price);
	//}
}