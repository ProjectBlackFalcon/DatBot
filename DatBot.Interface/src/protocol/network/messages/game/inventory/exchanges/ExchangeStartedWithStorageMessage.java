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
import protocol.network.messages.game.inventory.exchanges.ExchangeStartedMessage;

@SuppressWarnings("unused")
public class ExchangeStartedWithStorageMessage extends ExchangeStartedMessage {
	public static final int ProtocolId = 6236;

	public int storageMaxSlot;

	public ExchangeStartedWithStorageMessage(){
	}

	public ExchangeStartedWithStorageMessage(int storageMaxSlot){
		this.storageMaxSlot = storageMaxSlot;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.storageMaxSlot);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.storageMaxSlot = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("storageMaxSlot : " + this.storageMaxSlot);
	//}
}
