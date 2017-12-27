package protocol.network.messages.game.character.stats;

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
public class CharacterLevelUpMessage extends NetworkMessage {
	public static final int ProtocolId = 5670;

	private int newLevel;

	public int getNewLevel() { return this.newLevel; };
	public void setNewLevel(int newLevel) { this.newLevel = newLevel; };

	public CharacterLevelUpMessage(){
	}

	public CharacterLevelUpMessage(int newLevel){
		this.newLevel = newLevel;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.newLevel);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.newLevel = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
