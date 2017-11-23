package protocol.network.messages.game.context.roleplay.houses.guild;

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
public class HouseGuildShareRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5704;

	public int houseId;
	public int instanceId;
	public boolean enable;
	public int rights;

	public HouseGuildShareRequestMessage(){
	}

	public HouseGuildShareRequestMessage(int houseId, int instanceId, boolean enable, int rights){
		this.houseId = houseId;
		this.instanceId = instanceId;
		this.enable = enable;
		this.rights = rights;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.houseId);
			writer.writeInt(this.instanceId);
			writer.writeBoolean(this.enable);
			writer.writeVarInt(this.rights);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.houseId = reader.readVarInt();
			this.instanceId = reader.readInt();
			this.enable = reader.readBoolean();
			this.rights = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("houseId : " + this.houseId);
		//Network.appendDebug("instanceId : " + this.instanceId);
		//Network.appendDebug("enable : " + this.enable);
		//Network.appendDebug("rights : " + this.rights);
	//}
}
