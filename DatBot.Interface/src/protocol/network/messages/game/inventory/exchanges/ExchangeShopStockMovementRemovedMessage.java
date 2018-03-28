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
public class ExchangeShopStockMovementRemovedMessage extends NetworkMessage {
	public static final int ProtocolId = 5907;

	private int objectId;

	public int getObjectId() { return this.objectId; }
	public void setObjectId(int objectId) { this.objectId = objectId; };

	public ExchangeShopStockMovementRemovedMessage(){
	}

	public ExchangeShopStockMovementRemovedMessage(int objectId){
		this.objectId = objectId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.objectId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.objectId = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
