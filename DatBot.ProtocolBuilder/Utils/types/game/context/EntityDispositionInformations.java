package protocol.network.types.game.context;

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
public class EntityDispositionInformations extends NetworkMessage {
	public static final int ProtocolId = 60;

	private int cellId;
	private int direction;

	public int getCellId() { return this.cellId; }
	public void setCellId(int cellId) { this.cellId = cellId; };
	public int getDirection() { return this.direction; }
	public void setDirection(int direction) { this.direction = direction; };

	public EntityDispositionInformations(){
	}

	public EntityDispositionInformations(int cellId, int direction){
		this.cellId = cellId;
		this.direction = direction;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.cellId);
			writer.writeByte(this.direction);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.cellId = reader.readShort();
			this.direction = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
