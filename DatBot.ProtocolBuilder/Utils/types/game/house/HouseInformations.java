package protocol.network.types.game.house;

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
public class HouseInformations extends NetworkMessage {
	public static final int ProtocolId = 111;

	private int houseId;
	private int modelId;

	public int getHouseId() { return this.houseId; };
	public void setHouseId(int houseId) { this.houseId = houseId; };
	public int getModelId() { return this.modelId; };
	public void setModelId(int modelId) { this.modelId = modelId; };

	public HouseInformations(){
	}

	public HouseInformations(int houseId, int modelId){
		this.houseId = houseId;
		this.modelId = modelId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.houseId);
			writer.writeVarShort(this.modelId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.houseId = reader.readVarInt();
			this.modelId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
