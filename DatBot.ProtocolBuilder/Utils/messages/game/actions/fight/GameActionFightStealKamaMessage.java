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
public class GameActionFightStealKamaMessage extends AbstractGameActionMessage {
	public static final int ProtocolId = 5535;

	private double targetId;
	private long amount;

	public double getTargetId() { return this.targetId; };
	public void setTargetId(double targetId) { this.targetId = targetId; };
	public long getAmount() { return this.amount; };
	public void setAmount(long amount) { this.amount = amount; };

	public GameActionFightStealKamaMessage(){
	}

	public GameActionFightStealKamaMessage(double targetId, long amount){
		this.targetId = targetId;
		this.amount = amount;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeDouble(this.targetId);
			writer.writeVarLong(this.amount);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.targetId = reader.readDouble();
			this.amount = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
