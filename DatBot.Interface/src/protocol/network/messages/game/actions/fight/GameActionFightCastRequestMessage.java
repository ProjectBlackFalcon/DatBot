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
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class GameActionFightCastRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 1005;

	public int spellId;
	public int cellId;

	public GameActionFightCastRequestMessage(){
	}

	public GameActionFightCastRequestMessage(int spellId, int cellId){
		this.spellId = spellId;
		this.cellId = cellId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.spellId);
			writer.writeShort(this.cellId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.spellId = reader.readVarShort();
			this.cellId = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("spellId : " + this.spellId);
		//Network.appendDebug("cellId : " + this.cellId);
	//}
}
