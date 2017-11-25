package protocol.network.messages.game.context.roleplay.fight;

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
public class GameRolePlayPlayerFightRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5731;

	public long targetId;
	public int targetCellId;
	public boolean friendly;

	public GameRolePlayPlayerFightRequestMessage(){
	}

	public GameRolePlayPlayerFightRequestMessage(long targetId, int targetCellId, boolean friendly){
		this.targetId = targetId;
		this.targetCellId = targetCellId;
		this.friendly = friendly;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.targetId);
			writer.writeShort(this.targetCellId);
			writer.writeBoolean(this.friendly);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.targetId = reader.readVarLong();
			this.targetCellId = reader.readShort();
			this.friendly = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("targetId : " + this.targetId);
		//Network.appendDebug("targetCellId : " + this.targetCellId);
		//Network.appendDebug("friendly : " + this.friendly);
	//}
}