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
public class ExchangePodsModifiedMessage extends ExchangeObjectMessage {
	public static final int ProtocolId = 6670;

	public int currentWeight;
	public int maxWeight;

	public ExchangePodsModifiedMessage(){
	}

	public ExchangePodsModifiedMessage(int currentWeight, int maxWeight){
		this.currentWeight = currentWeight;
		this.maxWeight = maxWeight;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.currentWeight);
			writer.writeVarInt(this.maxWeight);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.currentWeight = reader.readVarInt();
			this.maxWeight = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("currentWeight : " + this.currentWeight);
		//Network.appendDebug("maxWeight : " + this.maxWeight);
	//}
}