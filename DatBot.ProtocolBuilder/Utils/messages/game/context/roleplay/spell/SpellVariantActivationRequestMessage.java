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
public class SpellVariantActivationRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6707;

	private int spellId;

	public int getSpellId() { return this.spellId; };
	public void setSpellId(int spellId) { this.spellId = spellId; };

	public SpellVariantActivationRequestMessage(){
	}

	public SpellVariantActivationRequestMessage(int spellId){
		this.spellId = spellId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.spellId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.spellId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
