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
import protocol.network.messages.game.actions.fight.GameActionFightDispellMessage;

@SuppressWarnings("unused")
public class GameActionFightDispellSpellMessage extends GameActionFightDispellMessage {
	public static final int ProtocolId = 6176;

	private int spellId;

	public int getSpellId() { return this.spellId; };
	public void setSpellId(int spellId) { this.spellId = spellId; };

	public GameActionFightDispellSpellMessage(){
	}

	public GameActionFightDispellSpellMessage(int spellId){
		this.spellId = spellId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.spellId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.spellId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
