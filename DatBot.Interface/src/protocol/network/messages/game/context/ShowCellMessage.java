package protocol.network.messages.game.context;

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
public class ShowCellMessage extends NetworkMessage {
	public static final int ProtocolId = 5612;

	public double sourceId;
	public int cellId;

	public ShowCellMessage(){
	}

	public ShowCellMessage(double sourceId, int cellId){
		this.sourceId = sourceId;
		this.cellId = cellId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.sourceId);
			writer.writeVarShort(this.cellId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.sourceId = reader.readDouble();
			this.cellId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("sourceId : " + this.sourceId);
		//Network.appendDebug("cellId : " + this.cellId);
	//}
}
