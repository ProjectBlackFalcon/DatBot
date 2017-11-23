package protocol.network.messages.game.context.dungeon;

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
public class DungeonKeyRingUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 6296;

	public int dungeonId;
	public boolean available;

	public DungeonKeyRingUpdateMessage(){
	}

	public DungeonKeyRingUpdateMessage(int dungeonId, boolean available){
		this.dungeonId = dungeonId;
		this.available = available;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.dungeonId);
			writer.writeBoolean(this.available);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.dungeonId = reader.readVarShort();
			this.available = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("dungeonId : " + this.dungeonId);
		//Network.appendDebug("available : " + this.available);
	//}
}
