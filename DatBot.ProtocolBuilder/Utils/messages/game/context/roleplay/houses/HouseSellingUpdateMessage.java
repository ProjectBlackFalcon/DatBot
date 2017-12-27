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
public class HouseSellingUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 6727;

	private int houseId;
	private int instanceId;
	private boolean secondHand;
	private long realPrice;
	private String buyerName;

	public int getHouseId() { return this.houseId; };
	public void setHouseId(int houseId) { this.houseId = houseId; };
	public int getInstanceId() { return this.instanceId; };
	public void setInstanceId(int instanceId) { this.instanceId = instanceId; };
	public boolean isSecondHand() { return this.secondHand; };
	public void setSecondHand(boolean secondHand) { this.secondHand = secondHand; };
	public long getRealPrice() { return this.realPrice; };
	public void setRealPrice(long realPrice) { this.realPrice = realPrice; };
	public String getBuyerName() { return this.buyerName; };
	public void setBuyerName(String buyerName) { this.buyerName = buyerName; };

	public HouseSellingUpdateMessage(){
	}

	public HouseSellingUpdateMessage(int houseId, int instanceId, boolean secondHand, long realPrice, String buyerName){
		this.houseId = houseId;
		this.instanceId = instanceId;
		this.secondHand = secondHand;
		this.realPrice = realPrice;
		this.buyerName = buyerName;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.houseId);
			writer.writeInt(this.instanceId);
			writer.writeBoolean(this.secondHand);
			writer.writeVarLong(this.realPrice);
			writer.writeUTF(this.buyerName);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.houseId = reader.readVarInt();
			this.instanceId = reader.readInt();
			this.secondHand = reader.readBoolean();
			this.realPrice = reader.readVarLong();
			this.buyerName = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
