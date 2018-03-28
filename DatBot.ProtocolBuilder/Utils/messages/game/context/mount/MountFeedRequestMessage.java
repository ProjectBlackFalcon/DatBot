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

	private int mountUid;
	private int mountLocation;
	private int mountFoodUid;
	private int quantity;

	public int getMountUid() { return this.mountUid; }
	public void setMountUid(int mountUid) { this.mountUid = mountUid; };
	public int getMountLocation() { return this.mountLocation; }
	public void setMountLocation(int mountLocation) { this.mountLocation = mountLocation; };
	public int getMountFoodUid() { return this.mountFoodUid; }
	public void setMountFoodUid(int mountFoodUid) { this.mountFoodUid = mountFoodUid; };
	public int getQuantity() { return this.quantity; }
	public void setQuantity(int quantity) { this.quantity = quantity; };

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
	}

}
