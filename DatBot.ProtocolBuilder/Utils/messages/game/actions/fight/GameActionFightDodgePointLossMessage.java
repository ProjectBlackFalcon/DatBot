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
public class GameActionFightDodgePointLossMessage extends AbstractGameActionMessage {
	public static final int ProtocolId = 5828;

	private double targetId;
	private int amount;

	public double getTargetId() { return this.targetId; };
	public void setTargetId(double targetId) { this.targetId = targetId; };
	public int getAmount() { return this.amount; };
	public void setAmount(int amount) { this.amount = amount; };

	public GameActionFightDodgePointLossMessage(){
	}

	public GameActionFightDodgePointLossMessage(double targetId, int amount){
		this.targetId = targetId;
		this.amount = amount;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeDouble(this.targetId);
			writer.writeVarShort(this.amount);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.targetId = reader.readDouble();
			this.amount = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
