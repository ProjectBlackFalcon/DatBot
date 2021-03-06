package protocol.network.types.game.interactive;

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
public class MapObstacle extends NetworkMessage {
	public static final int ProtocolId = 200;

	private int obstacleCellId;
	private int state;

	public int getObstacleCellId() { return this.obstacleCellId; }
	public void setObstacleCellId(int obstacleCellId) { this.obstacleCellId = obstacleCellId; };
	public int getState() { return this.state; }
	public void setState(int state) { this.state = state; };

	public MapObstacle(){
	}

	public MapObstacle(int obstacleCellId, int state){
		this.obstacleCellId = obstacleCellId;
		this.state = state;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.obstacleCellId);
			writer.writeByte(this.state);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.obstacleCellId = reader.readVarShort();
			this.state = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
