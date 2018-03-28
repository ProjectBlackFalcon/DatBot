package protocol.network.messages.game.context.dungeon;

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
public class DungeonKeyRingMessage extends NetworkMessage {
	public static final int ProtocolId = 6299;

	private List<Integer> availables;
	private List<Integer> unavailables;

	public List<Integer> getAvailables() { return this.availables; }
	public void setAvailables(List<Integer> availables) { this.availables = availables; };
	public List<Integer> getUnavailables() { return this.unavailables; }
	public void setUnavailables(List<Integer> unavailables) { this.unavailables = unavailables; };

	public DungeonKeyRingMessage(){
	}

	public DungeonKeyRingMessage(List<Integer> availables, List<Integer> unavailables){
		this.availables = availables;
		this.unavailables = unavailables;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.availables.size());
			int _loc2_ = 0;
			while( _loc2_ < this.availables.size()){
				writer.writeVarShort(this.availables.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.unavailables.size());
			int _loc3_ = 0;
			while( _loc3_ < this.unavailables.size()){
				writer.writeVarShort(this.unavailables.get(_loc3_));
				_loc3_++;
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
			this.availables = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.availables.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.unavailables = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readVarShort();
				this.unavailables.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
