package protocol.network.messages.connection.search;

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
public class AcquaintanceServerListMessage extends NetworkMessage {
	public static final int ProtocolId = 6142;

	private List<Integer> servers;

	public List<Integer> getServers() { return this.servers; }
	public void setServers(List<Integer> servers) { this.servers = servers; };

	public AcquaintanceServerListMessage(){
	}

	public AcquaintanceServerListMessage(List<Integer> servers){
		this.servers = servers;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.servers.size());
			int _loc2_ = 0;
			while( _loc2_ < this.servers.size()){
				writer.writeVarShort(this.servers.get(_loc2_));
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
			this.servers = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.servers.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
