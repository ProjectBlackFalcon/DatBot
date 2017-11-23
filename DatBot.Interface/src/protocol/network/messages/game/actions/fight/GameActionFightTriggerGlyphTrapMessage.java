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
import protocol.network.messages.game.actions.AbstractGameActionMessage;

@SuppressWarnings("unused")
public class GameActionFightTriggerGlyphTrapMessage extends AbstractGameActionMessage {
	public static final int ProtocolId = 5741;

	public int markId;
	public double triggeringCharacterId;
	public int triggeredSpellId;

	public GameActionFightTriggerGlyphTrapMessage(){
	}

	public GameActionFightTriggerGlyphTrapMessage(int markId, double triggeringCharacterId, int triggeredSpellId){
		this.markId = markId;
		this.triggeringCharacterId = triggeringCharacterId;
		this.triggeredSpellId = triggeredSpellId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.markId);
			writer.writeDouble(this.triggeringCharacterId);
			writer.writeVarShort(this.triggeredSpellId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.markId = reader.readShort();
			this.triggeringCharacterId = reader.readDouble();
			this.triggeredSpellId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("markId : " + this.markId);
		//Network.appendDebug("triggeringCharacterId : " + this.triggeringCharacterId);
		//Network.appendDebug("triggeredSpellId : " + this.triggeredSpellId);
	//}
}
