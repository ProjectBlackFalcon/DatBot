package protocol.network.messages.game.subscriber;

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
public class SubscriptionZoneMessage extends NetworkMessage {
	public static final int ProtocolId = 5573;

	public boolean active;

	public SubscriptionZoneMessage(){
	}

	public SubscriptionZoneMessage(boolean active){
		this.active = active;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.active);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.active = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("active : " + this.active);
	//}
}
