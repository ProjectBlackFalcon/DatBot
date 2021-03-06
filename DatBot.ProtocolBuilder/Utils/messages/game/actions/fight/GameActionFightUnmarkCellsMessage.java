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
public class GameActionFightUnmarkCellsMessage extends AbstractGameActionMessage {
	public static final int ProtocolId = 5570;

	private int markId;

	public int getMarkId() { return this.markId; }
	public void setMarkId(int markId) { this.markId = markId; };

	public GameActionFightUnmarkCellsMessage(){
	}

	public GameActionFightUnmarkCellsMessage(int markId){
		this.markId = markId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.markId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.markId = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
