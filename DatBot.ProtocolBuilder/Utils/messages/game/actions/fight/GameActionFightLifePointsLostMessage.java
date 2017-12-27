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
public class GameActionFightLifePointsLostMessage extends AbstractGameActionMessage {
	public static final int ProtocolId = 6312;

	private double targetId;
	private int loss;
	private int permanentDamages;

	public double getTargetId() { return this.targetId; };
	public void setTargetId(double targetId) { this.targetId = targetId; };
	public int getLoss() { return this.loss; };
	public void setLoss(int loss) { this.loss = loss; };
	public int getPermanentDamages() { return this.permanentDamages; };
	public void setPermanentDamages(int permanentDamages) { this.permanentDamages = permanentDamages; };

	public GameActionFightLifePointsLostMessage(){
	}

	public GameActionFightLifePointsLostMessage(double targetId, int loss, int permanentDamages){
		this.targetId = targetId;
		this.loss = loss;
		this.permanentDamages = permanentDamages;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeDouble(this.targetId);
			writer.writeVarInt(this.loss);
			writer.writeVarInt(this.permanentDamages);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.targetId = reader.readDouble();
			this.loss = reader.readVarInt();
			this.permanentDamages = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
