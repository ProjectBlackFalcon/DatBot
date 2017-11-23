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
public class HouseSellRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5697;

	public int instanceId;
	public long amount;
	public boolean forSale;

	public HouseSellRequestMessage(){
	}

	public HouseSellRequestMessage(int instanceId, long amount, boolean forSale){
		this.instanceId = instanceId;
		this.amount = amount;
		this.forSale = forSale;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.instanceId);
			writer.writeVarLong(this.amount);
			writer.writeBoolean(this.forSale);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.instanceId = reader.readInt();
			this.amount = reader.readVarLong();
			this.forSale = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("instanceId : " + this.instanceId);
		//Network.appendDebug("amount : " + this.amount);
		//Network.appendDebug("forSale : " + this.forSale);
	//}
}
