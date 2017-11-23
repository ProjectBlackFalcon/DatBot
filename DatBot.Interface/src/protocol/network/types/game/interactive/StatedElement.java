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
public class StatedElement extends NetworkMessage {
	public static final int ProtocolId = 108;

	public int elementId;
	public int elementCellId;
	public int elementState;
	public boolean onCurrentMap;

	public StatedElement(){
	}

	public StatedElement(int elementId, int elementCellId, int elementState, boolean onCurrentMap){
		this.elementId = elementId;
		this.elementCellId = elementCellId;
		this.elementState = elementState;
		this.onCurrentMap = onCurrentMap;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.elementId);
			writer.writeVarShort(this.elementCellId);
			writer.writeVarInt(this.elementState);
			writer.writeBoolean(this.onCurrentMap);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.elementId = reader.readInt();
			this.elementCellId = reader.readVarShort();
			this.elementState = reader.readVarInt();
			this.onCurrentMap = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("elementId : " + this.elementId);
		//Network.appendDebug("elementCellId : " + this.elementCellId);
		//Network.appendDebug("elementState : " + this.elementState);
		//Network.appendDebug("onCurrentMap : " + this.onCurrentMap);
	//}
}
