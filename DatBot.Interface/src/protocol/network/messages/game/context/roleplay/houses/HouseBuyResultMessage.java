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

	public int houseId;
	public int instanceId;
	public boolean secondHand;
	public boolean bought;
	public long realPrice;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("houseId : " + this.houseId);
		//Network.appendDebug("instanceId : " + this.instanceId);
		//Network.appendDebug("secondHand : " + this.secondHand);
		//Network.appendDebug("bought : " + this.bought);
		//Network.appendDebug("realPrice : " + this.realPrice);
	//}
}