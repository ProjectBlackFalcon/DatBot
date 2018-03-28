package protocol.network.messages.game.approach;

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
public class ReloginTokenStatusMessage extends NetworkMessage {
	public static final int ProtocolId = 6539;

	private boolean validToken;
	private List<Integer> ticket;

	public boolean isValidToken() { return this.validToken; }
	public void setValidToken(boolean validToken) { this.validToken = validToken; };
	public List<Integer> getTicket() { return this.ticket; }
	public void setTicket(List<Integer> ticket) { this.ticket = ticket; };

	public ReloginTokenStatusMessage(){
	}

	public ReloginTokenStatusMessage(boolean validToken, List<Integer> ticket){
		this.validToken = validToken;
		this.ticket = ticket;
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
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
