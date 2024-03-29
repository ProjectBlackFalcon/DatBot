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
import protocol.network.messages.game.inventory.exchanges.ExchangeRequestMessage;

@SuppressWarnings("unused")
public class ExchangePlayerRequestMessage extends ExchangeRequestMessage {
	public static final int ProtocolId = 5773;

	private long target;

	public long getTarget() { return this.target; }
	public void setTarget(long target) { this.target = target; };

	public ExchangePlayerRequestMessage(){
	}

	public ExchangePlayerRequestMessage(long target){
		this.target = target;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.target);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.target = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
