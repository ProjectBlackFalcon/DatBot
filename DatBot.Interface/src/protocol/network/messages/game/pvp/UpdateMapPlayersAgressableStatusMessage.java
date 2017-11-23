package protocol.network.messages.game.pvp;

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
public class UpdateMapPlayersAgressableStatusMessage extends NetworkMessage {
	public static final int ProtocolId = 6454;

	public List<Long> playerIds;
	public List<Integer> enable;

	public UpdateMapPlayersAgressableStatusMessage(){
	}

	public UpdateMapPlayersAgressableStatusMessage(List<Long> playerIds, List<Integer> enable){
		this.playerIds = playerIds;
		this.enable = enable;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.playerIds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.playerIds.size()){
				writer.writeVarLong(this.playerIds.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.enable.size());
			int _loc3_ = 0;
			while( _loc3_ < this.enable.size()){
				writer.writeByte(this.enable.get(_loc3_));
				_loc3_++;
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
			this.playerIds = new ArrayList<Long>();
			while( _loc3_ <  _loc2_){
				long _loc15_ = reader.readVarLong();
				this.playerIds.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.enable = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readByte();
				this.enable.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(Long a : playerIds) {
			//Network.appendDebug("playerIds : " + a);
		//}
		//for(Integer a : enable) {
			//Network.appendDebug("enable : " + a);
		//}
	//}
}
