package protocol.network.messages.game.house;

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
public class HouseTeleportRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6726;

	private int houseId;
	private int houseInstanceId;

	public int getHouseId() { return this.houseId; }
	public void setHouseId(int houseId) { this.houseId = houseId; };
	public int getHouseInstanceId() { return this.houseInstanceId; }
	public void setHouseInstanceId(int houseInstanceId) { this.houseInstanceId = houseInstanceId; };

	public HouseTeleportRequestMessage(){
	}

	public HouseTeleportRequestMessage(int houseId, int houseInstanceId){
		this.houseId = houseId;
		this.houseInstanceId = houseInstanceId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.houseId);
			writer.writeInt(this.houseInstanceId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.houseId = reader.readVarInt();
			this.houseInstanceId = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
