package protocol.network.messages.game.chat.smiley;

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
public class MoodSmileyUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 6388;

	public int accountId;
	public long playerId;
	public int smileyId;

	public MoodSmileyUpdateMessage(){
	}

	public MoodSmileyUpdateMessage(int accountId, long playerId, int smileyId){
		this.accountId = accountId;
		this.playerId = playerId;
		this.smileyId = smileyId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.accountId);
			writer.writeVarLong(this.playerId);
			writer.writeVarShort(this.smileyId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.accountId = reader.readInt();
			this.playerId = reader.readVarLong();
			this.smileyId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("accountId : " + this.accountId);
		//Network.appendDebug("playerId : " + this.playerId);
		//Network.appendDebug("smileyId : " + this.smileyId);
	//}
}