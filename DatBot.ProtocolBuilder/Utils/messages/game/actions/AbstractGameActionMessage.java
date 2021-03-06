package protocol.network.messages.game.actions;

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
public class AbstractGameActionMessage extends NetworkMessage {
	public static final int ProtocolId = 1000;

	private int actionId;
	private double sourceId;

	public int getActionId() { return this.actionId; }
	public void setActionId(int actionId) { this.actionId = actionId; };
	public double getSourceId() { return this.sourceId; }
	public void setSourceId(double sourceId) { this.sourceId = sourceId; };

	public AbstractGameActionMessage(){
	}

	public AbstractGameActionMessage(int actionId, double sourceId){
		this.actionId = actionId;
		this.sourceId = sourceId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.actionId);
			writer.writeDouble(this.sourceId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.actionId = reader.readVarShort();
			this.sourceId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
