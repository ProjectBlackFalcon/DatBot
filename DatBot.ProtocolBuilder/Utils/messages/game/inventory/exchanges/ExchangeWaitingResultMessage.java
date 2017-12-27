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
public class ExchangeWaitingResultMessage extends NetworkMessage {
	public static final int ProtocolId = 5786;

	private boolean bwait;

	public boolean isBwait() { return this.bwait; };
	public void setBwait(boolean bwait) { this.bwait = bwait; };

	public ExchangeWaitingResultMessage(){
	}

	public ExchangeWaitingResultMessage(boolean bwait){
		this.bwait = bwait;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.bwait);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.bwait = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
