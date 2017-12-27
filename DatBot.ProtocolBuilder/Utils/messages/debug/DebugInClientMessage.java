package protocol.network.messages.debug;

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
public class DebugInClientMessage extends NetworkMessage {
	public static final int ProtocolId = 6028;

	private int level;
	private String message;

	public int getLevel() { return this.level; };
	public void setLevel(int level) { this.level = level; };
	public String getMessage() { return this.message; };
	public void setMessage(String message) { this.message = message; };

	public DebugInClientMessage(){
	}

	public DebugInClientMessage(int level, String message){
		this.level = level;
		this.message = message;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.level);
			writer.writeUTF(this.message);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.level = reader.readByte();
			this.message = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
