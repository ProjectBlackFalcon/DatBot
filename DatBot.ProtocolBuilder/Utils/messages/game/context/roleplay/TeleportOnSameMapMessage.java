package protocol.network.messages.game.context.roleplay;

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
public class TeleportOnSameMapMessage extends NetworkMessage {
	public static final int ProtocolId = 6048;

	private double targetId;
	private int cellId;

	public double getTargetId() { return this.targetId; };
	public void setTargetId(double targetId) { this.targetId = targetId; };
	public int getCellId() { return this.cellId; };
	public void setCellId(int cellId) { this.cellId = cellId; };

	public TeleportOnSameMapMessage(){
	}

	public TeleportOnSameMapMessage(double targetId, int cellId){
		this.targetId = targetId;
		this.cellId = cellId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.targetId);
			writer.writeVarShort(this.cellId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.targetId = reader.readDouble();
			this.cellId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
