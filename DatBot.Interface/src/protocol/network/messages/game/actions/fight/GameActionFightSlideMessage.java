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
public class GameActionFightSlideMessage extends AbstractGameActionMessage {
	public static final int ProtocolId = 5525;

	public double targetId;
	public int startCellId;
	public int endCellId;

	public GameActionFightSlideMessage(){
	}

	public GameActionFightSlideMessage(double targetId, int startCellId, int endCellId){
		this.targetId = targetId;
		this.startCellId = startCellId;
		this.endCellId = endCellId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeDouble(this.targetId);
			writer.writeShort(this.startCellId);
			writer.writeShort(this.endCellId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.targetId = reader.readDouble();
			this.startCellId = reader.readShort();
			this.endCellId = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("targetId : " + this.targetId);
		//Network.appendDebug("startCellId : " + this.startCellId);
		//Network.appendDebug("endCellId : " + this.endCellId);
	//}
}
