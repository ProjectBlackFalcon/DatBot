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

	public int houseId;
	public int houseInstanceId;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("houseId : " + this.houseId);
		//Network.appendDebug("houseInstanceId : " + this.houseInstanceId);
	//}
}
