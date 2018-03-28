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
import protocol.network.NetworkMessage;
import protocol.network.types.connection.GameServerInformations;

@SuppressWarnings("unused")
public class ServersListMessage extends NetworkMessage {
	public static final int ProtocolId = 30;

	private List<GameServerInformations> servers;
	private int alreadyConnectedToServerId;
	private boolean canCreateNewCharacter;

	public List<GameServerInformations> getServers() { return this.servers; }
	public void setServers(List<GameServerInformations> servers) { this.servers = servers; };
	public int getAlreadyConnectedToServerId() { return this.alreadyConnectedToServerId; }
	public void setAlreadyConnectedToServerId(int alreadyConnectedToServerId) { this.alreadyConnectedToServerId = alreadyConnectedToServerId; };
	public boolean isCanCreateNewCharacter() { return this.canCreateNewCharacter; }
	public void setCanCreateNewCharacter(boolean canCreateNewCharacter) { this.canCreateNewCharacter = canCreateNewCharacter; };

	public ServersListMessage(){
	}

	public ServersListMessage(List<GameServerInformations> servers, int alreadyConnectedToServerId, boolean canCreateNewCharacter){
		this.servers = servers;
		this.alreadyConnectedToServerId = alreadyConnectedToServerId;
		this.canCreateNewCharacter = canCreateNewCharacter;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.servers.size());
			int _loc2_ = 0;
			while( _loc2_ < this.servers.size()){
				this.servers.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeVarShort(this.alreadyConnectedToServerId);
			writer.writeBoolean(this.canCreateNewCharacter);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.servers = new ArrayList<GameServerInformations>();
			while( _loc3_ <  _loc2_){
				GameServerInformations _loc15_ = new GameServerInformations();
				_loc15_.Deserialize(reader);
				this.servers.add(_loc15_);
				_loc3_++;
			}
			this.alreadyConnectedToServerId = reader.readVarShort();
			this.canCreateNewCharacter = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
