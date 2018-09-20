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
	private List<Integer> ports;
	private boolean canCreateNewCharacter;
	private List<Integer> ticket;

	public int getServerId() { return this.serverId; }
	public void setServerId(int serverId) { this.serverId = serverId; };
	public String getAddress() { return this.address; }
	public void setAddress(String address) { this.address = address; };
	public List<Integer> getPorts() { return this.ports; }
	public void setPorts(List<Integer> ports) { this.ports = ports; };
	public boolean isCanCreateNewCharacter() { return this.canCreateNewCharacter; }
	public void setCanCreateNewCharacter(boolean canCreateNewCharacter) { this.canCreateNewCharacter = canCreateNewCharacter; };
	public List<Integer> getTicket() { return this.ticket; }
	public void setTicket(List<Integer> ticket) { this.ticket = ticket; };

	public SelectedServerDataMessage(){
	}

	public SelectedServerDataMessage(int serverId, String address, List<Integer> ports, boolean canCreateNewCharacter, List<Integer> ticket){
		this.serverId = serverId;
		this.address = address;
		this.ports = ports;
		this.canCreateNewCharacter = canCreateNewCharacter;
		this.ticket = ticket;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.serverId);
			writer.writeUTF(this.address);
			writer.writeShort(this.ports.size());
			int _loc2_ = 0;
			while( _loc2_ < this.ports.size()){
				writer.writeInt(this.ports.get(_loc2_));
				_loc2_++;
			}
			writer.writeBoolean(this.canCreateNewCharacter);
			writer.writeVarInt(this.ticket.size());
			int _loc3_ = 0;
			while( _loc3_ < this.ticket.size()){
				writer.writeByte(this.ticket.get(_loc3_));
				_loc3_++;
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
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.ports = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readInt();
				this.ports.add(_loc15_);
				_loc3_++;
			}
			this.canCreateNewCharacter = reader.readBoolean();
			int _loc4_  = reader.readVarInt();
			int _loc5_  = 0;
			this.ticket = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readByte();
				this.ticket.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
