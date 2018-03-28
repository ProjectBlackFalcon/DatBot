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
public class ExchangeReplyTaxVendorMessage extends NetworkMessage {
	public static final int ProtocolId = 5787;

	private long objectValue;
	private long totalTaxValue;

	public long getObjectValue() { return this.objectValue; }
	public void setObjectValue(long objectValue) { this.objectValue = objectValue; };
	public long getTotalTaxValue() { return this.totalTaxValue; }
	public void setTotalTaxValue(long totalTaxValue) { this.totalTaxValue = totalTaxValue; };

	public ExchangeReplyTaxVendorMessage(){
	}

	public ExchangeReplyTaxVendorMessage(long objectValue, long totalTaxValue){
		this.objectValue = objectValue;
		this.totalTaxValue = totalTaxValue;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.objectValue);
			writer.writeVarLong(this.totalTaxValue);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.objectValue = reader.readVarLong();
			this.totalTaxValue = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
