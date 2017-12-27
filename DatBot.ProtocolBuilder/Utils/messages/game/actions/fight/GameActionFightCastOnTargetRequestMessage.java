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
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class GameActionFightCastOnTargetRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6330;

	private int spellId;
	private double targetId;

	public int getSpellId() { return this.spellId; };
	public void setSpellId(int spellId) { this.spellId = spellId; };
	public double getTargetId() { return this.targetId; };
	public void setTargetId(double targetId) { this.targetId = targetId; };

	public GameActionFightCastOnTargetRequestMessage(){
	}

	public GameActionFightCastOnTargetRequestMessage(int spellId, double targetId){
		this.spellId = spellId;
		this.targetId = targetId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.spellId);
			writer.writeDouble(this.targetId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.spellId = reader.readVarShort();
			this.targetId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
