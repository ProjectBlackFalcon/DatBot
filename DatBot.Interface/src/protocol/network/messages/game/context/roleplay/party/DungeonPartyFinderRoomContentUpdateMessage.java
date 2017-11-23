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
public class DungeonPartyFinderRoomContentUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 6250;

	public int dungeonId;
	public List<DungeonPartyFinderPlayer> addedPlayers;
	public List<Long> removedPlayersIds;

	public DungeonPartyFinderRoomContentUpdateMessage(){
	}

	public DungeonPartyFinderRoomContentUpdateMessage(int dungeonId, List<DungeonPartyFinderPlayer> addedPlayers, List<Long> removedPlayersIds){
		this.dungeonId = dungeonId;
		this.addedPlayers = addedPlayers;
		this.removedPlayersIds = removedPlayersIds;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.dungeonId);
			writer.writeShort(this.addedPlayers.size());
			int _loc2_ = 0;
			while( _loc2_ < this.addedPlayers.size()){
				this.addedPlayers.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeShort(this.removedPlayersIds.size());
			int _loc3_ = 0;
			while( _loc3_ < this.removedPlayersIds.size()){
				writer.writeVarLong(this.removedPlayersIds.get(_loc3_));
				_loc3_++;
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
			this.addedPlayers = new ArrayList<DungeonPartyFinderPlayer>();
			while( _loc3_ <  _loc2_){
				DungeonPartyFinderPlayer _loc15_ = new DungeonPartyFinderPlayer();
				_loc15_.Deserialize(reader);
				this.addedPlayers.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.removedPlayersIds = new ArrayList<Long>();
			while( _loc5_ <  _loc4_){
				long _loc16_ = reader.readVarLong();
				this.removedPlayersIds.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("dungeonId : " + this.dungeonId);
		//for(DungeonPartyFinderPlayer a : addedPlayers) {
			//Network.appendDebug("addedPlayers : " + a);
		//}
		//for(Long a : removedPlayersIds) {
			//Network.appendDebug("removedPlayersIds : " + a);
		//}
	//}
}
