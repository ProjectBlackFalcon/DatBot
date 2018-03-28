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
import protocol.network.types.game.context.roleplay.party.DungeonPartyFinderPlayer;

@SuppressWarnings("unused")
public class DungeonPartyFinderRoomContentMessage extends NetworkMessage {
	public static final int ProtocolId = 6247;

	private int dungeonId;
	private List<DungeonPartyFinderPlayer> players;

	public int getDungeonId() { return this.dungeonId; }
	public void setDungeonId(int dungeonId) { this.dungeonId = dungeonId; };
	public List<DungeonPartyFinderPlayer> getPlayers() { return this.players; }
	public void setPlayers(List<DungeonPartyFinderPlayer> players) { this.players = players; };

	public DungeonPartyFinderRoomContentMessage(){
	}

	public DungeonPartyFinderRoomContentMessage(int dungeonId, List<DungeonPartyFinderPlayer> players){
		this.dungeonId = dungeonId;
		this.players = players;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.dungeonId);
			writer.writeShort(this.players.size());
			int _loc2_ = 0;
			while( _loc2_ < this.players.size()){
				this.players.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.dungeonId = reader.readVarShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.players = new ArrayList<DungeonPartyFinderPlayer>();
			while( _loc3_ <  _loc2_){
				DungeonPartyFinderPlayer _loc15_ = new DungeonPartyFinderPlayer();
				_loc15_.Deserialize(reader);
				this.players.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
