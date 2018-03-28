package protocol.network.messages.connection;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.connection.SelectedServerDataMessage;
import protocol.network.types.connection.GameServerInformations;

@SuppressWarnings("unused")
public class SelectedServerDataExtendedMessage extends SelectedServerDataMessage {
	public static final int ProtocolId = 6469;

	private List<GameServerInformations> servers;

	public List<GameServerInformations> getServers() { return this.servers; }
	public void setServers(List<GameServerInformations> servers) { this.servers = servers; };

	public SelectedServerDataExtendedMessage(){
	}

	public SelectedServerDataExtendedMessage(List<GameServerInformations> servers){
		this.servers = servers;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.servers.size());
			int _loc2_ = 0;
			while( _loc2_ < this.servers.size()){
				this.servers.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.servers = new ArrayList<GameServerInformations>();
			while( _loc3_ <  _loc2_){
				GameServerInformations _loc15_ = new GameServerInformations();
				_loc15_.Deserialize(reader);
				this.servers.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
