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
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class ServerSessionConstant extends NetworkMessage {
	public static final int ProtocolId = 430;

	private int id;

	public int getId() { return this.id; };
	public void setId(int id) { this.id = id; };

	public ServerSessionConstant(){
	}

	public ServerSessionConstant(int id){
		this.id = id;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.id);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.id = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
