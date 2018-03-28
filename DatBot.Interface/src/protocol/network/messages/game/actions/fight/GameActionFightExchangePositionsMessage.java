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
public class GameActionFightExchangePositionsMessage extends AbstractGameActionMessage {
	public static final int ProtocolId = 5527;

	private double targetId;
	private int casterCellId;
	private int targetCellId;

	public double getTargetId() { return this.targetId; }
	public void setTargetId(double targetId) { this.targetId = targetId; };
	public int getCasterCellId() { return this.casterCellId; }
	public void setCasterCellId(int casterCellId) { this.casterCellId = casterCellId; };
	public int getTargetCellId() { return this.targetCellId; }
	public void setTargetCellId(int targetCellId) { this.targetCellId = targetCellId; };

	public GameActionFightExchangePositionsMessage(){
	}

	public GameActionFightExchangePositionsMessage(double targetId, int casterCellId, int targetCellId){
		this.targetId = targetId;
		this.casterCellId = casterCellId;
		this.targetCellId = targetCellId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeDouble(this.targetId);
			writer.writeShort(this.casterCellId);
			writer.writeShort(this.targetCellId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.targetId = reader.readDouble();
			this.casterCellId = reader.readShort();
			this.targetCellId = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
