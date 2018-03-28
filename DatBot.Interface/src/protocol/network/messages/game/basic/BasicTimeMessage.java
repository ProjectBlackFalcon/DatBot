package protocol.network.messages.game.basic;

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
public class BasicTimeMessage extends NetworkMessage {
	public static final int ProtocolId = 175;

	private double timestamp;
	private int timezoneOffset;

	public double getTimestamp() { return this.timestamp; }
	public void setTimestamp(double timestamp) { this.timestamp = timestamp; };
	public int getTimezoneOffset() { return this.timezoneOffset; }
	public void setTimezoneOffset(int timezoneOffset) { this.timezoneOffset = timezoneOffset; };

	public BasicTimeMessage(){
	}

	public BasicTimeMessage(double timestamp, int timezoneOffset){
		this.timestamp = timestamp;
		this.timezoneOffset = timezoneOffset;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.timestamp);
			writer.writeShort(this.timezoneOffset);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.timestamp = reader.readDouble();
			this.timezoneOffset = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
