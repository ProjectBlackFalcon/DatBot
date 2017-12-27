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

	private int instanceId;
	private boolean secondHand;
	private boolean isLocked;
	private String ownerName;
	private long price;
	private boolean isSaleLocked;

	public int getInstanceId() { return this.instanceId; };
	public void setInstanceId(int instanceId) { this.instanceId = instanceId; };
	public boolean isSecondHand() { return this.secondHand; };
	public void setSecondHand(boolean secondHand) { this.secondHand = secondHand; };
	public boolean isIsLocked() { return this.isLocked; };
	public void setIsLocked(boolean isLocked) { this.isLocked = isLocked; };
	public String getOwnerName() { return this.ownerName; };
	public void setOwnerName(String ownerName) { this.ownerName = ownerName; };
	public long getPrice() { return this.price; };
	public void setPrice(long price) { this.price = price; };
	public boolean isIsSaleLocked() { return this.isSaleLocked; };
	public void setIsSaleLocked(boolean isSaleLocked) { this.isSaleLocked = isSaleLocked; };

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
	}

}
