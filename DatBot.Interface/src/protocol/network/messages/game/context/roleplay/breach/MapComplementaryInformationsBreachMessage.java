package protocol.network.messages.game.context.roleplay.breach;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.MapComplementaryInformationsDataMessage;
import protocol.network.types.game.context.roleplay.breach.BreachBranch;

@SuppressWarnings("unused")
public class MapComplementaryInformationsBreachMessage extends MapComplementaryInformationsDataMessage {
	public static final int ProtocolId = 6791;

	private int floor;
	private int room;
	private List<BreachBranch> branches;

	public int getFloor() { return this.floor; }
	public void setFloor(int floor) { this.floor = floor; };
	public int getRoom() { return this.room; }
	public void setRoom(int room) { this.room = room; };
	public List<BreachBranch> getBranches() { return this.branches; }
	public void setBranches(List<BreachBranch> branches) { this.branches = branches; };

	public MapComplementaryInformationsBreachMessage(){
	}

	public MapComplementaryInformationsBreachMessage(int floor, int room, List<BreachBranch> branches){
		this.floor = floor;
		this.room = room;
		this.branches = branches;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.floor);
			writer.writeByte(this.room);
			writer.writeShort(this.branches.size());
			int _loc2_ = 0;
			while( _loc2_ < this.branches.size()){
				this.branches.get(_loc2_).Serialize(writer);
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
			this.floor = reader.readVarInt();
			this.room = reader.readByte();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.branches = new ArrayList<BreachBranch>();
			while( _loc3_ <  _loc2_){
				BreachBranch _loc15_ = new BreachBranch();
				_loc15_.Deserialize(reader);
				this.branches.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
