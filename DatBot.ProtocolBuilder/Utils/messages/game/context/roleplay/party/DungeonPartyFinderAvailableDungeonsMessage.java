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
public class DungeonPartyFinderAvailableDungeonsMessage extends NetworkMessage {
	public static final int ProtocolId = 6242;

	private List<Integer> dungeonIds;

	public List<Integer> getDungeonIds() { return this.dungeonIds; };
	public void setDungeonIds(List<Integer> dungeonIds) { this.dungeonIds = dungeonIds; };

	public DungeonPartyFinderAvailableDungeonsMessage(){
	}

	public DungeonPartyFinderAvailableDungeonsMessage(List<Integer> dungeonIds){
		this.dungeonIds = dungeonIds;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.dungeonIds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.dungeonIds.size()){
				writer.writeVarShort(this.dungeonIds.get(_loc2_));
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.dungeonIds = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.dungeonIds.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
