package protocol.network.messages.game.achievement;

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
public class AchievementFinishedMessage extends NetworkMessage {
	public static final int ProtocolId = 6208;

	public int id;
	public int finishedlevel;

	public AchievementFinishedMessage(){
	}

	public AchievementFinishedMessage(int id, int finishedlevel){
		this.id = id;
		this.finishedlevel = finishedlevel;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.id);
			writer.writeByte(this.finishedlevel);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.id = reader.readVarShort();
			this.finishedlevel = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("id : " + this.id);
		//Network.appendDebug("finishedlevel : " + this.finishedlevel);
	//}
}
