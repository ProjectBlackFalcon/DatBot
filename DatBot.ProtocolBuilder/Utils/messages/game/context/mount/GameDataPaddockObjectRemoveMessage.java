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
public class GameDataPaddockObjectRemoveMessage extends NetworkMessage {
	public static final int ProtocolId = 5993;

	private int cellId;

	public int getCellId() { return this.cellId; };
	public void setCellId(int cellId) { this.cellId = cellId; };

	public GameDataPaddockObjectRemoveMessage(){
	}

	public GameDataPaddockObjectRemoveMessage(int cellId){
		this.cellId = cellId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.cellId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.cellId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
