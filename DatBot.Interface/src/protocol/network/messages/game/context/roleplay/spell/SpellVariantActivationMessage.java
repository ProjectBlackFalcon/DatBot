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

	public boolean result;
	public int activatedSpellId;
	public int deactivatedSpellId;

	public SpellVariantActivationMessage(){
	}

	public SpellVariantActivationMessage(boolean result, int activatedSpellId, int deactivatedSpellId){
		this.result = result;
		this.activatedSpellId = activatedSpellId;
		this.deactivatedSpellId = deactivatedSpellId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.result);
			writer.writeVarShort(this.activatedSpellId);
			writer.writeVarShort(this.deactivatedSpellId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.result = reader.readBoolean();
			this.activatedSpellId = reader.readVarShort();
			this.deactivatedSpellId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("result : " + this.result);
		//Network.appendDebug("activatedSpellId : " + this.activatedSpellId);
		//Network.appendDebug("deactivatedSpellId : " + this.deactivatedSpellId);
	//}
}
