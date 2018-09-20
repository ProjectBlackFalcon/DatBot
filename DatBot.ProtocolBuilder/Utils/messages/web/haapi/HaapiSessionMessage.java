package protocol.network.messages.web.haapi;

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
public class HaapiSessionMessage extends NetworkMessage {
	public static final int ProtocolId = 6769;

	private String key;
	private int type;

	public String getKey() { return this.key; }
	public void setKey(String key) { this.key = key; };
	public int getType() { return this.type; }
	public void setType(int type) { this.type = type; };

	public HaapiSessionMessage(){
	}

	public HaapiSessionMessage(String key, int type){
		this.key = key;
		this.type = type;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.key);
			writer.writeByte(this.type);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.key = reader.readUTF();
			this.type = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
