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
public class ExchangeWeightMessage extends NetworkMessage {
	public static final int ProtocolId = 5793;

	private int currentWeight;
	private int maxWeight;

	public int getCurrentWeight() { return this.currentWeight; }
	public void setCurrentWeight(int currentWeight) { this.currentWeight = currentWeight; };
	public int getMaxWeight() { return this.maxWeight; }
	public void setMaxWeight(int maxWeight) { this.maxWeight = maxWeight; };

	public ExchangeWeightMessage(){
	}

	public ExchangeWeightMessage(int currentWeight, int maxWeight){
		this.currentWeight = currentWeight;
		this.maxWeight = maxWeight;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.currentWeight);
			writer.writeVarInt(this.maxWeight);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.currentWeight = reader.readVarInt();
			this.maxWeight = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
