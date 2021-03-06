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
public class GameActionFightSpellCooldownVariationMessage extends AbstractGameActionMessage {
	public static final int ProtocolId = 6219;

	private double targetId;
	private int spellId;
	private int value;

	public double getTargetId() { return this.targetId; }
	public void setTargetId(double targetId) { this.targetId = targetId; };
	public int getSpellId() { return this.spellId; }
	public void setSpellId(int spellId) { this.spellId = spellId; };
	public int getValue() { return this.value; }
	public void setValue(int value) { this.value = value; };

	public GameActionFightSpellCooldownVariationMessage(){
	}

	public GameActionFightSpellCooldownVariationMessage(double targetId, int spellId, int value){
		this.targetId = targetId;
		this.spellId = spellId;
		this.value = value;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeDouble(this.targetId);
			writer.writeVarShort(this.spellId);
			writer.writeVarShort(this.value);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.targetId = reader.readDouble();
			this.spellId = reader.readVarShort();
			this.value = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
