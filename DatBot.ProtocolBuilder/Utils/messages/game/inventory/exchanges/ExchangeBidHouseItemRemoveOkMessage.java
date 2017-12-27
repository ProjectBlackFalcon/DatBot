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
public class ExchangeBidHouseItemRemoveOkMessage extends NetworkMessage {
	public static final int ProtocolId = 5946;

	private int sellerId;

	public int getSellerId() { return this.sellerId; };
	public void setSellerId(int sellerId) { this.sellerId = sellerId; };

	public ExchangeBidHouseItemRemoveOkMessage(){
	}

	public ExchangeBidHouseItemRemoveOkMessage(int sellerId){
		this.sellerId = sellerId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.sellerId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.sellerId = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
