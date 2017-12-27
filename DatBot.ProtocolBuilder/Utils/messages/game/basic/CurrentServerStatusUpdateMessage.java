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
public class CurrentServerStatusUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 6525;

	private int status;

	public int getStatus() { return this.status; };
	public void setStatus(int status) { this.status = status; };

	public CurrentServerStatusUpdateMessage(){
	}

	public CurrentServerStatusUpdateMessage(int status){
		this.status = status;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.status);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.status = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
