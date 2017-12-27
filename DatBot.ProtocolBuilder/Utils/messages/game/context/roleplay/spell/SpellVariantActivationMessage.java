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
public class SpellVariantActivationMessage extends NetworkMessage {
	public static final int ProtocolId = 6705;

	private int spellId;
	private boolean result;

	public int getSpellId() { return this.spellId; };
	public void setSpellId(int spellId) { this.spellId = spellId; };
	public boolean isResult() { return this.result; };
	public void setResult(boolean result) { this.result = result; };

	public SpellVariantActivationMessage(){
	}

	public SpellVariantActivationMessage(int spellId, boolean result){
		this.spellId = spellId;
		this.result = result;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.spellId);
			writer.writeBoolean(this.result);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.spellId = reader.readVarShort();
			this.result = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
