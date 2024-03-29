package protocol.network.types.game.presets;

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
public class Preset extends NetworkMessage {
	public static final int ProtocolId = 355;

	private int id;

	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; };

	public Preset(){
	}

	public Preset(int id){
		this.id = id;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.id);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.id = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
