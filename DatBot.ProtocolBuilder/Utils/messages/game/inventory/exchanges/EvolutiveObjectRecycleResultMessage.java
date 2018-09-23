package protocol.network.messages.game.inventory.exchanges;

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
import protocol.network.types.game.inventory.exchanges.RecycledItem;

@SuppressWarnings("unused")
public class EvolutiveObjectRecycleResultMessage extends NetworkMessage {
	public static final int ProtocolId = 6779;

	private List<RecycledItem> recycledItems;

	public List<RecycledItem> getRecycledItems() { return this.recycledItems; }
	public void setRecycledItems(List<RecycledItem> recycledItems) { this.recycledItems = recycledItems; };

	public EvolutiveObjectRecycleResultMessage(){
	}

	public EvolutiveObjectRecycleResultMessage(List<RecycledItem> recycledItems){
		this.recycledItems = recycledItems;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.recycledItems.size());
			int _loc2_ = 0;
			while( _loc2_ < this.recycledItems.size()){
				this.recycledItems.get(_loc2_).Serialize(writer);
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
			this.recycledItems = new ArrayList<RecycledItem>();
			while( _loc3_ <  _loc2_){
				RecycledItem _loc15_ = new RecycledItem();
				_loc15_.Deserialize(reader);
				this.recycledItems.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
