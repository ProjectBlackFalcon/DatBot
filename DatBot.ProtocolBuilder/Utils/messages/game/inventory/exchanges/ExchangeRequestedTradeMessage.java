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
import protocol.network.messages.game.inventory.exchanges.ExchangeRequestedMessage;

@SuppressWarnings("unused")
public class ExchangeRequestedTradeMessage extends ExchangeRequestedMessage {
	public static final int ProtocolId = 5523;

	private long source;
	private long target;

	public long getSource() { return this.source; };
	public void setSource(long source) { this.source = source; };
	public long getTarget() { return this.target; };
	public void setTarget(long target) { this.target = target; };

	public ExchangeRequestedTradeMessage(){
	}

	public ExchangeRequestedTradeMessage(long source, long target){
		this.source = source;
		this.target = target;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.source);
			writer.writeVarLong(this.target);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.source = reader.readVarLong();
			this.target = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
