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
public class GameActionFightLifePointsGainMessage extends AbstractGameActionMessage {
	public static final int ProtocolId = 6311;

	private double targetId;
	private int delta;

	public double getTargetId() { return this.targetId; };
	public void setTargetId(double targetId) { this.targetId = targetId; };
	public int getDelta() { return this.delta; };
	public void setDelta(int delta) { this.delta = delta; };

	public GameActionFightLifePointsGainMessage(){
	}

	public GameActionFightLifePointsGainMessage(double targetId, int delta){
		this.targetId = targetId;
		this.delta = delta;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeDouble(this.targetId);
			writer.writeVarInt(this.delta);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.targetId = reader.readDouble();
			this.delta = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
