package protocol.network.messages.game.actions.fight;

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
public class GameActionFightNoSpellCastMessage extends NetworkMessage {
	public static final int ProtocolId = 6132;

	private int spellLevelId;

	public int getSpellLevelId() { return this.spellLevelId; };
	public void setSpellLevelId(int spellLevelId) { this.spellLevelId = spellLevelId; };

	public GameActionFightNoSpellCastMessage(){
	}

	public GameActionFightNoSpellCastMessage(int spellLevelId){
		this.spellLevelId = spellLevelId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.spellLevelId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.spellLevelId = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
