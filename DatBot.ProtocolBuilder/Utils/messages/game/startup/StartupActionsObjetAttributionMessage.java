package protocol.network.messages.game.startup;

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
public class StartupActionsObjetAttributionMessage extends NetworkMessage {
	public static final int ProtocolId = 1303;

	private int actionId;
	private long characterId;

	public int getActionId() { return this.actionId; }
	public void setActionId(int actionId) { this.actionId = actionId; };
	public long getCharacterId() { return this.characterId; }
	public void setCharacterId(long characterId) { this.characterId = characterId; };

	public StartupActionsObjetAttributionMessage(){
	}

	public StartupActionsObjetAttributionMessage(int actionId, long characterId){
		this.actionId = actionId;
		this.characterId = characterId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.actionId);
			writer.writeVarLong(this.characterId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.actionId = reader.readInt();
			this.characterId = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
