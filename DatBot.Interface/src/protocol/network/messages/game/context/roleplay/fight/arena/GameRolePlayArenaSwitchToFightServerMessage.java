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
public class GameRolePlayArenaSwitchToFightServerMessage extends NetworkMessage {
	public static final int ProtocolId = 6575;

	public String address;
	public List<Integer> ports;
	public List<Integer> ticket;

	public GameRolePlayArenaSwitchToFightServerMessage(){
	}

	public GameRolePlayArenaSwitchToFightServerMessage(String address, List<Integer> ports, List<Integer> ticket){
		this.address = address;
		this.ports = ports;
		this.ticket = ticket;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.address);
			writer.writeShort(this.ports.size());
			int _loc2_ = 0;
			while( _loc2_ < this.ports.size()){
				writer.writeShort(this.ports.get(_loc2_));
				_loc2_++;
			}
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
			this.address = reader.readUTF();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.ports = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readShort();
				this.ports.add(_loc15_);
				_loc3_++;
			}
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
//		append();
	}

	private void append(){
//		Network.appendDebug("address : " + this.address);
		System.out.println(address);
		
		for(Integer a : ports) {
//			Network.appendDebug("ports : " + a);
			System.out.println("port" + a);
		}
		for(Integer a : ticket) {
			System.out.println("ticket" + a);
//			Network.appendDebug("ticket : " + a);
		}
	}
}