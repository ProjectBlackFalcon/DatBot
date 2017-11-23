package protocol.network.messages.game.context.mount;

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
public class MountFeedRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6189;

	public int mountUid;
	public int mountLocation;
	public int mountFoodUid;
	public int quantity;

	public MountFeedRequestMessage(){
	}

	public MountFeedRequestMessage(int mountUid, int mountLocation, int mountFoodUid, int quantity){
		this.mountUid = mountUid;
		this.mountLocation = mountLocation;
		this.mountFoodUid = mountFoodUid;
		this.quantity = quantity;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.mountUid);
			writer.writeByte(this.mountLocation);
			writer.writeVarInt(this.mountFoodUid);
			writer.writeVarInt(this.quantity);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.mountUid = reader.readVarInt();
			this.mountLocation = reader.readByte();
			this.mountFoodUid = reader.readVarInt();
			this.quantity = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("mountUid : " + this.mountUid);
		//Network.appendDebug("mountLocation : " + this.mountLocation);
		//Network.appendDebug("mountFoodUid : " + this.mountFoodUid);
		//Network.appendDebug("quantity : " + this.quantity);
	//}
}
