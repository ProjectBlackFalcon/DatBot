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
public class HouseInstanceInformations extends NetworkMessage {
	public static final int ProtocolId = 511;

	public int instanceId;
	public boolean secondHand;
	public boolean isLocked;
	public String ownerName;
	public long price;
	public boolean isSaleLocked;

	public HouseInstanceInformations(){
	}

	public HouseInstanceInformations(int instanceId, boolean secondHand, boolean isLocked, String ownerName, long price, boolean isSaleLocked){
		this.instanceId = instanceId;
		this.secondHand = secondHand;
		this.isLocked = isLocked;
		this.ownerName = ownerName;
		this.price = price;
		this.isSaleLocked = isSaleLocked;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, secondHand);
			flag = BooleanByteWrapper.SetFlag(1, flag, isLocked);
			flag = BooleanByteWrapper.SetFlag(2, flag, isSaleLocked);
			writer.writeByte(flag);
			writer.writeInt(this.instanceId);
			writer.writeUTF(this.ownerName);
			writer.writeVarLong(this.price);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			byte flag;
			flag = (byte) reader.readUnsignedByte();
			this.secondHand = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.isLocked = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.isSaleLocked = BooleanByteWrapper.GetFlag(flag, (byte) 2);
			this.instanceId = reader.readInt();
			this.ownerName = reader.readUTF();
			this.price = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("instanceId : " + this.instanceId);
		//Network.appendDebug("secondHand : " + this.secondHand);
		//Network.appendDebug("isLocked : " + this.isLocked);
		//Network.appendDebug("ownerName : " + this.ownerName);
		//Network.appendDebug("price : " + this.price);
		//Network.appendDebug("isSaleLocked : " + this.isSaleLocked);
	//}
}
