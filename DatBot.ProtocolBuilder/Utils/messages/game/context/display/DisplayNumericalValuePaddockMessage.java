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

	private int rideId;
	private int value;
	private int type;

	public int getRideId() { return this.rideId; };
	public void setRideId(int rideId) { this.rideId = rideId; };
	public int getValue() { return this.value; };
	public void setValue(int value) { this.value = value; };
	public int getType() { return this.type; };
	public void setType(int type) { this.type = type; };

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
	}

}
