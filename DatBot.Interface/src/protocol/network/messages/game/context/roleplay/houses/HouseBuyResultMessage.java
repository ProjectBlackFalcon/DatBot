package protocol.network.messages.game.context.roleplay.houses;

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
public class HouseBuyResultMessage extends NetworkMessage {
	public static final int ProtocolId = 5735;

	private int houseId;
	private int instanceId;
	private boolean secondHand;
	private boolean bought;
	private long realPrice;

	public int getHouseId() { return this.houseId; }
	public void setHouseId(int houseId) { this.houseId = houseId; };
	public int getInstanceId() { return this.instanceId; }
	public void setInstanceId(int instanceId) { this.instanceId = instanceId; };
	public boolean isSecondHand() { return this.secondHand; }
	public void setSecondHand(boolean secondHand) { this.secondHand = secondHand; };
	public boolean isBought() { return this.bought; }
	public void setBought(boolean bought) { this.bought = bought; };
	public long getRealPrice() { return this.realPrice; }
	public void setRealPrice(long realPrice) { this.realPrice = realPrice; };

	public HouseBuyResultMessage(){
	}

	public HouseBuyResultMessage(int houseId, int instanceId, boolean secondHand, boolean bought, long realPrice){
		this.houseId = houseId;
		this.instanceId = instanceId;
		this.secondHand = secondHand;
		this.bought = bought;
		this.realPrice = realPrice;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, secondHand);
			flag = BooleanByteWrapper.SetFlag(1, flag, bought);
			writer.writeByte(flag);
			writer.writeVarInt(this.houseId);
			writer.writeInt(this.instanceId);
			writer.writeVarLong(this.realPrice);
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
			this.bought = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.houseId = reader.readVarInt();
			this.instanceId = reader.readInt();
			this.realPrice = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
