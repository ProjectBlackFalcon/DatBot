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
public class GameActionFightDispellMessage extends AbstractGameActionMessage {
	public static final int ProtocolId = 5533;

	private double targetId;
	private boolean verboseCast;

	public double getTargetId() { return this.targetId; }
	public void setTargetId(double targetId) { this.targetId = targetId; };
	public boolean isVerboseCast() { return this.verboseCast; }
	public void setVerboseCast(boolean verboseCast) { this.verboseCast = verboseCast; };

	public GameActionFightDispellMessage(){
	}

	public GameActionFightDispellMessage(double targetId, boolean verboseCast){
		this.targetId = targetId;
		this.verboseCast = verboseCast;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeDouble(this.targetId);
			writer.writeBoolean(this.verboseCast);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.targetId = reader.readDouble();
			this.verboseCast = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
