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
public class GameActionFightTeleportOnSameMapMessage extends AbstractGameActionMessage {
	public static final int ProtocolId = 5528;

	private double targetId;
	private int cellId;

	public double getTargetId() { return this.targetId; }
	public void setTargetId(double targetId) { this.targetId = targetId; };
	public int getCellId() { return this.cellId; }
	public void setCellId(int cellId) { this.cellId = cellId; };

	public GameActionFightTeleportOnSameMapMessage(){
	}

	public GameActionFightTeleportOnSameMapMessage(double targetId, int cellId){
		this.targetId = targetId;
		this.cellId = cellId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeDouble(this.targetId);
			writer.writeShort(this.cellId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.targetId = reader.readDouble();
			this.cellId = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
