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

	private int markId;
	private int markImpactCell;
	private double triggeringCharacterId;
	private int triggeredSpellId;

	public int getMarkId() { return this.markId; };
	public void setMarkId(int markId) { this.markId = markId; };
	public int getMarkImpactCell() { return this.markImpactCell; };
	public void setMarkImpactCell(int markImpactCell) { this.markImpactCell = markImpactCell; };
	public double getTriggeringCharacterId() { return this.triggeringCharacterId; };
	public void setTriggeringCharacterId(double triggeringCharacterId) { this.triggeringCharacterId = triggeringCharacterId; };
	public int getTriggeredSpellId() { return this.triggeredSpellId; };
	public void setTriggeredSpellId(int triggeredSpellId) { this.triggeredSpellId = triggeredSpellId; };

	public GameActionFightTriggerGlyphTrapMessage(){
	}

	public GameActionFightTriggerGlyphTrapMessage(int markId, int markImpactCell, double triggeringCharacterId, int triggeredSpellId){
		this.markId = markId;
		this.markImpactCell = markImpactCell;
		this.triggeringCharacterId = triggeringCharacterId;
		this.triggeredSpellId = triggeredSpellId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.markId);
			writer.writeVarShort(this.markImpactCell);
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
			this.markImpactCell = reader.readVarShort();
			this.triggeringCharacterId = reader.readDouble();
			this.triggeredSpellId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
