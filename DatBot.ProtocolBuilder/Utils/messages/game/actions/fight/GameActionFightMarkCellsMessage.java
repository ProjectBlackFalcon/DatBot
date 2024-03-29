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
import protocol.network.types.game.actions.fight.GameActionMark;

@SuppressWarnings("unused")
public class GameActionFightMarkCellsMessage extends AbstractGameActionMessage {
	public static final int ProtocolId = 5540;

	private GameActionMark mark;

	public GameActionMark getMark() { return this.mark; }
	public void setMark(GameActionMark mark) { this.mark = mark; };

	public GameActionFightMarkCellsMessage(){
	}

	public GameActionFightMarkCellsMessage(GameActionMark mark){
		this.mark = mark;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			mark.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.mark = new GameActionMark();
			this.mark.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
