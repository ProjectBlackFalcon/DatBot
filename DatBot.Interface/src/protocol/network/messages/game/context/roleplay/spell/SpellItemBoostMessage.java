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
public class SpellItemBoostMessage extends NetworkMessage {
	public static final int ProtocolId = 6011;

	public int statId;
	public int spellId;
	public int value;

	public SpellItemBoostMessage(){
	}

	public SpellItemBoostMessage(int statId, int spellId, int value){
		this.statId = statId;
		this.spellId = spellId;
		this.value = value;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.statId);
			writer.writeVarShort(this.spellId);
			writer.writeVarShort(this.value);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.statId = reader.readVarInt();
			this.spellId = reader.readVarShort();
			this.value = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("statId : " + this.statId);
		//Network.appendDebug("spellId : " + this.spellId);
		//Network.appendDebug("value : " + this.value);
	//}
}
