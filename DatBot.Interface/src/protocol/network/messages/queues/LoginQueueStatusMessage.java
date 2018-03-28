package protocol.network.messages.queues;

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
public class LoginQueueStatusMessage extends NetworkMessage {
	public static final int ProtocolId = 10;

	private int position;
	private int total;

	public int getPosition() { return this.position; }
	public void setPosition(int position) { this.position = position; };
	public int getTotal() { return this.total; }
	public void setTotal(int total) { this.total = total; };

	public LoginQueueStatusMessage(){
	}

	public LoginQueueStatusMessage(int position, int total){
		this.position = position;
		this.total = total;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.position);
			writer.writeShort(this.total);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.position = reader.readShort();
			this.total = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
