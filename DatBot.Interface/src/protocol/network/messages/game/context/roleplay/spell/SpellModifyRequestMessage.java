package protocol.network.messages.game.context.roleplay.spell;

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
public class SpellModifyRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6655;

	public int spellId;
	public int spellLevel;

	public SpellModifyRequestMessage(){
	}

	public SpellModifyRequestMessage(int spellId, int spellLevel){
		this.spellId = spellId;
		this.spellLevel = spellLevel;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.spellId);
			writer.writeShort(this.spellLevel);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.spellId = reader.readVarShort();
			this.spellLevel = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("spellId : " + this.spellId);
		//Network.appendDebug("spellLevel : " + this.spellLevel);
	//}
}
