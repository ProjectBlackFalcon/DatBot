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
public class ExchangeOnHumanVendorRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5772;

	public long humanVendorId;
	public int humanVendorCell;

	public ExchangeOnHumanVendorRequestMessage(){
	}

	public ExchangeOnHumanVendorRequestMessage(long humanVendorId, int humanVendorCell){
		this.humanVendorId = humanVendorId;
		this.humanVendorCell = humanVendorCell;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.humanVendorId);
			writer.writeVarShort(this.humanVendorCell);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.humanVendorId = reader.readVarLong();
			this.humanVendorCell = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("humanVendorId : " + this.humanVendorId);
		//Network.appendDebug("humanVendorCell : " + this.humanVendorCell);
	//}
}
