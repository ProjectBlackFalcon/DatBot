package protocol.network.messages.game.context.roleplay.lockable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.lockable.LockableStateUpdateAbstractMessage;

@SuppressWarnings("unused")
public class LockableStateUpdateStorageMessage extends LockableStateUpdateAbstractMessage {
	public static final int ProtocolId = 5669;

	private double mapId;
	private int elementId;

	public double getMapId() { return this.mapId; };
	public void setMapId(double mapId) { this.mapId = mapId; };
	public int getElementId() { return this.elementId; };
	public void setElementId(int elementId) { this.elementId = elementId; };

	public LockableStateUpdateStorageMessage(){
	}

	public LockableStateUpdateStorageMessage(double mapId, int elementId){
		this.mapId = mapId;
		this.elementId = elementId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeDouble(this.mapId);
			writer.writeVarInt(this.elementId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.mapId = reader.readDouble();
			this.elementId = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
