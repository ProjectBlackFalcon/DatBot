package protocol.network.messages.subscription;

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
public class SubscriptionUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 6616;

	public double timestamp;

	public SubscriptionUpdateMessage(){
	}

	public SubscriptionUpdateMessage(double timestamp){
		this.timestamp = timestamp;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.timestamp);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.timestamp = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("timestamp : " + this.timestamp);
	//}
}
