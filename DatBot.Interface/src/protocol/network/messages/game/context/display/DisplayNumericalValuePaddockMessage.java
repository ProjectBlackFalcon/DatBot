package protocol.network.messages.game.context.display;

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
public class DisplayNumericalValuePaddockMessage extends NetworkMessage {
	public static final int ProtocolId = 6563;

	public int rideId;
	public int value;
	public int type;

	public DisplayNumericalValuePaddockMessage(){
	}

	public DisplayNumericalValuePaddockMessage(int rideId, int value, int type){
		this.rideId = rideId;
		this.value = value;
		this.type = type;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.rideId);
			writer.writeInt(this.value);
			writer.writeByte(this.type);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.rideId = reader.readInt();
			this.value = reader.readInt();
			this.type = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("rideId : " + this.rideId);
		//Network.appendDebug("value : " + this.value);
		//Network.appendDebug("type : " + this.type);
	//}
}
