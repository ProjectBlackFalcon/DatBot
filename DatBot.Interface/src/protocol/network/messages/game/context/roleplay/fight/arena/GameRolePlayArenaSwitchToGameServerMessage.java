package protocol.network.messages.game.context.roleplay.fight.arena;

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
public class GameRolePlayArenaSwitchToGameServerMessage extends NetworkMessage {
	public static final int ProtocolId = 6574;

	public boolean validToken;
	public List<Integer> ticket;
	public int homeServerId;

	public GameRolePlayArenaSwitchToGameServerMessage(){
	}

	public GameRolePlayArenaSwitchToGameServerMessage(boolean validToken, List<Integer> ticket, int homeServerId){
		this.validToken = validToken;
		this.ticket = ticket;
		this.homeServerId = homeServerId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.validToken);
			writer.writeVarInt(this.ticket.size());
			int _loc2_ = 0;
			while( _loc2_ < this.ticket.size()){
				writer.writeByte(this.ticket.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.homeServerId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.validToken = reader.readBoolean();
			int _loc2_  = reader.readVarInt();
			int _loc3_  = 0;
			this.ticket = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readByte();
				this.ticket.add(_loc15_);
				_loc3_++;
			}
			this.homeServerId = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("validToken : " + this.validToken);
		//for(Integer a : ticket) {
			//Network.appendDebug("ticket : " + a);
		//}
		//Network.appendDebug("homeServerId : " + this.homeServerId);
	//}
}
