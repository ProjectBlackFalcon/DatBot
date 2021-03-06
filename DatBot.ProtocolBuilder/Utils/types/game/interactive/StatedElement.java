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

	private int elementId;
	private int elementCellId;
	private int elementState;
	private boolean onCurrentMap;

	public int getElementId() { return this.elementId; }
	public void setElementId(int elementId) { this.elementId = elementId; };
	public int getElementCellId() { return this.elementCellId; }
	public void setElementCellId(int elementCellId) { this.elementCellId = elementCellId; };
	public int getElementState() { return this.elementState; }
	public void setElementState(int elementState) { this.elementState = elementState; };
	public boolean isOnCurrentMap() { return this.onCurrentMap; }
	public void setOnCurrentMap(boolean onCurrentMap) { this.onCurrentMap = onCurrentMap; };

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
	}

}
