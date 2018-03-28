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
public class TeleportToBuddyAnswerMessage extends NetworkMessage {
	public static final int ProtocolId = 6293;

	private int dungeonId;
	private long buddyId;
	private boolean accept;

	public int getDungeonId() { return this.dungeonId; }
	public void setDungeonId(int dungeonId) { this.dungeonId = dungeonId; };
	public long getBuddyId() { return this.buddyId; }
	public void setBuddyId(long buddyId) { this.buddyId = buddyId; };
	public boolean isAccept() { return this.accept; }
	public void setAccept(boolean accept) { this.accept = accept; };

	public TeleportToBuddyAnswerMessage(){
	}

	public TeleportToBuddyAnswerMessage(int dungeonId, long buddyId, boolean accept){
		this.dungeonId = dungeonId;
		this.buddyId = buddyId;
		this.accept = accept;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.dungeonId);
			writer.writeVarLong(this.buddyId);
			writer.writeBoolean(this.accept);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.dungeonId = reader.readVarShort();
			this.buddyId = reader.readVarLong();
			this.accept = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
