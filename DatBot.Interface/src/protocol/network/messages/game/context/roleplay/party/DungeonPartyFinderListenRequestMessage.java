package protocol.network.messages.game.context.roleplay.party;

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
public class DungeonPartyFinderListenRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6246;

	private int dungeonId;

	public int getDungeonId() { return this.dungeonId; }
	public void setDungeonId(int dungeonId) { this.dungeonId = dungeonId; };

	public DungeonPartyFinderListenRequestMessage(){
	}

	public DungeonPartyFinderListenRequestMessage(int dungeonId){
		this.dungeonId = dungeonId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.dungeonId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.dungeonId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
