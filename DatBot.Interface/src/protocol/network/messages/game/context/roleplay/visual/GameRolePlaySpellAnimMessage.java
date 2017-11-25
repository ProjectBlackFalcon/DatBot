package protocol.network.messages.game.context.roleplay.visual;

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
public class GameRolePlaySpellAnimMessage extends NetworkMessage {
	public static final int ProtocolId = 6114;

	public long casterId;
	public int targetCellId;
	public int spellId;
	public int spellLevel;

	public GameRolePlaySpellAnimMessage(){
	}

	public GameRolePlaySpellAnimMessage(long casterId, int targetCellId, int spellId, int spellLevel){
		this.casterId = casterId;
		this.targetCellId = targetCellId;
		this.spellId = spellId;
		this.spellLevel = spellLevel;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.casterId);
			writer.writeVarShort(this.targetCellId);
			writer.writeVarShort(this.spellId);
			writer.writeShort(this.spellLevel);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.casterId = reader.readVarLong();
			this.targetCellId = reader.readVarShort();
			this.spellId = reader.readVarShort();
			this.spellLevel = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("casterId : " + this.casterId);
		//Network.appendDebug("targetCellId : " + this.targetCellId);
		//Network.appendDebug("spellId : " + this.spellId);
		//Network.appendDebug("spellLevel : " + this.spellLevel);
	//}
}