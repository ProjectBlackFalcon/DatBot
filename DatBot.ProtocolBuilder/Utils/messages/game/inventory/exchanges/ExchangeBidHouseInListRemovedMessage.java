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
public class ExchangeBidHouseInListRemovedMessage extends NetworkMessage {
	public static final int ProtocolId = 5950;

	private int itemUID;

	public int getItemUID() { return this.itemUID; }
	public void setItemUID(int itemUID) { this.itemUID = itemUID; };

	public ExchangeBidHouseInListRemovedMessage(){
	}

	public ExchangeBidHouseInListRemovedMessage(int itemUID){
		this.itemUID = itemUID;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.itemUID);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.itemUID = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
