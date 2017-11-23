package protocol.network.types.game.actions.fight;

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
public class GameActionMarkedCell extends NetworkMessage {
	public static final int ProtocolId = 85;

	public int cellId;
	public int zoneSize;
	public int cellColor;
	public int cellsType;

	public GameActionMarkedCell(){
	}

	public GameActionMarkedCell(int cellId, int zoneSize, int cellColor, int cellsType){
		this.cellId = cellId;
		this.zoneSize = zoneSize;
		this.cellColor = cellColor;
		this.cellsType = cellsType;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.cellId);
			writer.writeByte(this.zoneSize);
			writer.writeInt(this.cellColor);
			writer.writeByte(this.cellsType);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.cellId = reader.readVarShort();
			this.zoneSize = reader.readByte();
			this.cellColor = reader.readInt();
			this.cellsType = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("cellId : " + this.cellId);
		//Network.appendDebug("zoneSize : " + this.zoneSize);
		//Network.appendDebug("cellColor : " + this.cellColor);
		//Network.appendDebug("cellsType : " + this.cellsType);
	//}
}
