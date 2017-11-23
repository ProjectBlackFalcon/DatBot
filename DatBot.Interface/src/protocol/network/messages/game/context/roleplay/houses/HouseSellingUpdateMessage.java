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

	public int houseId;
	public int instanceId;
	public boolean secondHand;
	public long realPrice;
	public String buyerName;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("houseId : " + this.houseId);
		//Network.appendDebug("instanceId : " + this.instanceId);
		//Network.appendDebug("secondHand : " + this.secondHand);
		//Network.appendDebug("realPrice : " + this.realPrice);
		//Network.appendDebug("buyerName : " + this.buyerName);
	//}
}
