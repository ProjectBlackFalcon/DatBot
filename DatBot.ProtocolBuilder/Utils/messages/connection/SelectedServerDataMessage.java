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

@SuppressWarnings("unused")
public class SelectedServerDataMessage extends NetworkMessage {
	public static final int ProtocolId = 42;

	private int serverId;
	private String address;
	private int port;
	private boolean canCreateNewCharacter;
	private List<Integer> ticket;

	public int getServerId() { return this.serverId; }
	public void setServerId(int serverId) { this.serverId = serverId; };
	public String getAddress() { return this.address; }
	public void setAddress(String address) { this.address = address; };
	public int getPort() { return this.port; }
	public void setPort(int port) { this.port = port; };
	public boolean isCanCreateNewCharacter() { return this.canCreateNewCharacter; }
	public void setCanCreateNewCharacter(boolean canCreateNewCharacter) { this.canCreateNewCharacter = canCreateNewCharacter; };
	public List<Integer> getTicket() { return this.ticket; }
	public void setTicket(List<Integer> ticket) { this.ticket = ticket; };

	public SelectedServerDataMessage(){
	}

	public SelectedServerDataMessage(int serverId, String address, int port, boolean canCreateNewCharacter, List<Integer> ticket){
		this.serverId = serverId;
		this.address = address;
		this.port = port;
		this.canCreateNewCharacter = canCreateNewCharacter;
		this.ticket = ticket;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.serverId);
			writer.writeUTF(this.address);
			writer.writeShort(this.port);
			writer.writeBoolean(this.canCreateNewCharacter);
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
			this.serverId = reader.readVarShort();
			this.address = reader.readUTF();
			this.port = reader.readShort();
			this.canCreateNewCharacter = reader.readBoolean();
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
