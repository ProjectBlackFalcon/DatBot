package protocol.network.messages.game.inventory.exchanges;

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
public class ExchangeBidHouseBuyResultMessage extends NetworkMessage {
	public static final int ProtocolId = 6272;

	public int uid;
	public boolean bought;

	public ExchangeBidHouseBuyResultMessage(){
	}

	public ExchangeBidHouseBuyResultMessage(int uid, boolean bought){
		this.uid = uid;
		this.bought = bought;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.uid);
			writer.writeBoolean(this.bought);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.uid = reader.readVarInt();
			this.bought = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("uid : " + this.uid);
		//Network.appendDebug("bought : " + this.bought);
	//}
}
