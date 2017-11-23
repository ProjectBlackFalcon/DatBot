package protocol.network.messages.game.interactive.meeting;

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
public class TeleportToBuddyOfferMessage extends NetworkMessage {
	public static final int ProtocolId = 6287;

	public int dungeonId;
	public long buddyId;
	public int timeLeft;

	public TeleportToBuddyOfferMessage(){
	}

	public TeleportToBuddyOfferMessage(int dungeonId, long buddyId, int timeLeft){
		this.dungeonId = dungeonId;
		this.buddyId = buddyId;
		this.timeLeft = timeLeft;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.dungeonId);
			writer.writeVarLong(this.buddyId);
			writer.writeVarInt(this.timeLeft);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.dungeonId = reader.readVarShort();
			this.buddyId = reader.readVarLong();
			this.timeLeft = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("dungeonId : " + this.dungeonId);
		//Network.appendDebug("buddyId : " + this.buddyId);
		//Network.appendDebug("timeLeft : " + this.timeLeft);
	//}
}
