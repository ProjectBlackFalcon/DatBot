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
public class ExchangeReadyMessage extends NetworkMessage {
	public static final int ProtocolId = 5511;

	public boolean ready;
	public int step;

	public ExchangeReadyMessage(){
	}

	public ExchangeReadyMessage(boolean ready, int step){
		this.ready = ready;
		this.step = step;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.ready);
			writer.writeVarShort(this.step);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.ready = reader.readBoolean();
			this.step = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("ready : " + this.ready);
		//Network.appendDebug("step : " + this.step);
	//}
}
