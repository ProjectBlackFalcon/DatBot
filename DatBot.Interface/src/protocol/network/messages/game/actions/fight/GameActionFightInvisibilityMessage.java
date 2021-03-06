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
public class GameActionFightInvisibilityMessage extends AbstractGameActionMessage {
	public static final int ProtocolId = 5821;

	private double targetId;
	private int state;

	public double getTargetId() { return this.targetId; }
	public void setTargetId(double targetId) { this.targetId = targetId; };
	public int getState() { return this.state; }
	public void setState(int state) { this.state = state; };

	public GameActionFightInvisibilityMessage(){
	}

	public GameActionFightInvisibilityMessage(double targetId, int state){
		this.targetId = targetId;
		this.state = state;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeDouble(this.targetId);
			writer.writeByte(this.state);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.targetId = reader.readDouble();
			this.state = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
