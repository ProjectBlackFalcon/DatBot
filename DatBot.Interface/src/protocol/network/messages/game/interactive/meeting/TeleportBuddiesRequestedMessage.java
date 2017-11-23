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
public class TeleportBuddiesRequestedMessage extends NetworkMessage {
	public static final int ProtocolId = 6302;

	public int dungeonId;
	public long inviterId;
	public List<Long> invalidBuddiesIds;

	public TeleportBuddiesRequestedMessage(){
	}

	public TeleportBuddiesRequestedMessage(int dungeonId, long inviterId, List<Long> invalidBuddiesIds){
		this.dungeonId = dungeonId;
		this.inviterId = inviterId;
		this.invalidBuddiesIds = invalidBuddiesIds;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.dungeonId);
			writer.writeVarLong(this.inviterId);
			writer.writeShort(this.invalidBuddiesIds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.invalidBuddiesIds.size()){
				writer.writeVarLong(this.invalidBuddiesIds.get(_loc2_));
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
			this.inviterId = reader.readVarLong();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.invalidBuddiesIds = new ArrayList<Long>();
			while( _loc3_ <  _loc2_){
				long _loc15_ = reader.readVarLong();
				this.invalidBuddiesIds.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("dungeonId : " + this.dungeonId);
		//Network.appendDebug("inviterId : " + this.inviterId);
		//for(Long a : invalidBuddiesIds) {
			//Network.appendDebug("invalidBuddiesIds : " + a);
		//}
	//}
}
