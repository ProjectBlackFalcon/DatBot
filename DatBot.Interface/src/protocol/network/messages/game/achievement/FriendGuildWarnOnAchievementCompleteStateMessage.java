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
public class FriendGuildWarnOnAchievementCompleteStateMessage extends NetworkMessage {
	public static final int ProtocolId = 6383;

	public boolean enable;

	public FriendGuildWarnOnAchievementCompleteStateMessage(){
	}

	public FriendGuildWarnOnAchievementCompleteStateMessage(boolean enable){
		this.enable = enable;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.enable);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.enable = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("enable : " + this.enable);
	//}
}
