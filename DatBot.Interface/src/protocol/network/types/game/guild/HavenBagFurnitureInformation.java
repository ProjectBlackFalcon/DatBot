package protocol.network.types.game.guild;

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
public class HavenBagFurnitureInformation extends NetworkMessage {
	public static final int ProtocolId = 498;

	public int cellId;
	public int funitureId;
	public int orientation;

	public HavenBagFurnitureInformation(){
	}

	public HavenBagFurnitureInformation(int cellId, int funitureId, int orientation){
		this.cellId = cellId;
		this.funitureId = funitureId;
		this.orientation = orientation;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.cellId);
			writer.writeInt(this.funitureId);
			writer.writeByte(this.orientation);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.cellId = reader.readVarShort();
			this.funitureId = reader.readInt();
			this.orientation = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("cellId : " + this.cellId);
		//Network.appendDebug("funitureId : " + this.funitureId);
		//Network.appendDebug("orientation : " + this.orientation);
	//}
}
