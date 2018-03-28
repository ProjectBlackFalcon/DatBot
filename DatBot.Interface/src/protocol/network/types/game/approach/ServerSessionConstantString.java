package protocol.network.types.game.approach;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.approach.ServerSessionConstant;

@SuppressWarnings("unused")
public class ServerSessionConstantString extends ServerSessionConstant {
	public static final int ProtocolId = 436;

	private String value;

	public String getValue() { return this.value; }
	public void setValue(String value) { this.value = value; };

	public ServerSessionConstantString(){
	}

	public ServerSessionConstantString(String value){
		this.value = value;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeUTF(this.value);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.value = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
