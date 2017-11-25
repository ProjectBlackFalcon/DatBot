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
public class ExchangeRequestOnTaxCollectorMessage extends NetworkMessage {
	public static final int ProtocolId = 5779;

	public double taxCollectorId;

	public ExchangeRequestOnTaxCollectorMessage(){
	}

	public ExchangeRequestOnTaxCollectorMessage(double taxCollectorId){
		this.taxCollectorId = taxCollectorId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.taxCollectorId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.taxCollectorId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("taxCollectorId : " + this.taxCollectorId);
	//}
}