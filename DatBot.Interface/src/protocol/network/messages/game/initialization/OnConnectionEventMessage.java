package protocol.network.messages.game.initialization;

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
public class OnConnectionEventMessage extends NetworkMessage {
	public static final int ProtocolId = 5726;

	private int eventType;

	public int getEventType() { return this.eventType; }
	public void setEventType(int eventType) { this.eventType = eventType; };

	public OnConnectionEventMessage(){
	}

	public OnConnectionEventMessage(int eventType){
		this.eventType = eventType;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.eventType);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.eventType = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
