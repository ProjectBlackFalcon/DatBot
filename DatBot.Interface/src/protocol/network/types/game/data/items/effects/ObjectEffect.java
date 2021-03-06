package protocol.network.types.game.data.items.effects;

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
public class ObjectEffect extends NetworkMessage {
	public static final int ProtocolId = 76;

	private int actionId;

	public int getActionId() { return this.actionId; }
	public void setActionId(int actionId) { this.actionId = actionId; };

	public ObjectEffect(){
	}

	public ObjectEffect(int actionId){
		this.actionId = actionId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.actionId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.actionId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
