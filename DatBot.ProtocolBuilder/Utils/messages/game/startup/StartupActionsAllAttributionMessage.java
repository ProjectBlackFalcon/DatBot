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
public class StartupActionsAllAttributionMessage extends NetworkMessage {
	public static final int ProtocolId = 6537;

	private long characterId;

	public long getCharacterId() { return this.characterId; };
	public void setCharacterId(long characterId) { this.characterId = characterId; };

	public StartupActionsAllAttributionMessage(){
	}

	public StartupActionsAllAttributionMessage(long characterId){
		this.characterId = characterId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.characterId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.characterId = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
