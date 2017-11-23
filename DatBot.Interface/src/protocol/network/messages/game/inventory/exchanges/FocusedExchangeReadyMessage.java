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
import protocol.network.messages.game.inventory.exchanges.ExchangeReadyMessage;

@SuppressWarnings("unused")
public class FocusedExchangeReadyMessage extends ExchangeReadyMessage {
	public static final int ProtocolId = 6701;

	public int focusActionId;

	public FocusedExchangeReadyMessage(){
	}

	public FocusedExchangeReadyMessage(int focusActionId){
		this.focusActionId = focusActionId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.focusActionId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.focusActionId = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("focusActionId : " + this.focusActionId);
	//}
}
