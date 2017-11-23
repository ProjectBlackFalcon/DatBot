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
public class ExchangeOkMultiCraftMessage extends NetworkMessage {
	public static final int ProtocolId = 5768;

	public long initiatorId;
	public long otherId;
	public int role;

	public ExchangeOkMultiCraftMessage(){
	}

	public ExchangeOkMultiCraftMessage(long initiatorId, long otherId, int role){
		this.initiatorId = initiatorId;
		this.otherId = otherId;
		this.role = role;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.initiatorId);
			writer.writeVarLong(this.otherId);
			writer.writeByte(this.role);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.initiatorId = reader.readVarLong();
			this.otherId = reader.readVarLong();
			this.role = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("initiatorId : " + this.initiatorId);
		//Network.appendDebug("otherId : " + this.otherId);
		//Network.appendDebug("role : " + this.role);
	//}
}
