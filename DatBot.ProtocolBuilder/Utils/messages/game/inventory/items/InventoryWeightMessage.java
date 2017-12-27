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
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class InventoryWeightMessage extends NetworkMessage {
	public static final int ProtocolId = 3009;

	private int weight;
	private int weightMax;

	public int getWeight() { return this.weight; };
	public void setWeight(int weight) { this.weight = weight; };
	public int getWeightMax() { return this.weightMax; };
	public void setWeightMax(int weightMax) { this.weightMax = weightMax; };

	public InventoryWeightMessage(){
	}

	public InventoryWeightMessage(int weight, int weightMax){
		this.weight = weight;
		this.weightMax = weightMax;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.weight);
			writer.writeVarInt(this.weightMax);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.weight = reader.readVarInt();
			this.weightMax = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
