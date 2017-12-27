package protocol.network.messages.game.context.mount;

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
public class MountInformationRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5972;

	private double id;
	private double time;

	public double getId() { return this.id; };
	public void setId(double id) { this.id = id; };
	public double getTime() { return this.time; };
	public void setTime(double time) { this.time = time; };

	public MountInformationRequestMessage(){
	}

	public MountInformationRequestMessage(double id, double time){
		this.id = id;
		this.time = time;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.id);
			writer.writeDouble(this.time);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.id = reader.readDouble();
			this.time = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
