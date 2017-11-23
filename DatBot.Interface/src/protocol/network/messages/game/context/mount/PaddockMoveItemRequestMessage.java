package protocol.network.messages.game.context.mount;

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
public class PaddockMoveItemRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6052;

	public int oldCellId;
	public int newCellId;

	public PaddockMoveItemRequestMessage(){
	}

	public PaddockMoveItemRequestMessage(int oldCellId, int newCellId){
		this.oldCellId = oldCellId;
		this.newCellId = newCellId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.oldCellId);
			writer.writeVarShort(this.newCellId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.oldCellId = reader.readVarShort();
			this.newCellId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("oldCellId : " + this.oldCellId);
		//Network.appendDebug("newCellId : " + this.newCellId);
	//}
}
