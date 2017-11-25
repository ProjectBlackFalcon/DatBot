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

	public int actionId;
	public long characterId;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("actionId : " + this.actionId);
		//Network.appendDebug("characterId : " + this.characterId);
	//}
}