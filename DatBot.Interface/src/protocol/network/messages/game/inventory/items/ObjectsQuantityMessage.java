package protocol.network.messages.game.inventory.items;

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
import protocol.network.types.game.data.items.ObjectItemQuantity;

@SuppressWarnings("unused")
public class ObjectsQuantityMessage extends NetworkMessage {
	public static final int ProtocolId = 6206;

	private List<ObjectItemQuantity> objectsUIDAndQty;

	public List<ObjectItemQuantity> getObjectsUIDAndQty() { return this.objectsUIDAndQty; }
	public void setObjectsUIDAndQty(List<ObjectItemQuantity> objectsUIDAndQty) { this.objectsUIDAndQty = objectsUIDAndQty; };

	public ObjectsQuantityMessage(){
	}

	public ObjectsQuantityMessage(List<ObjectItemQuantity> objectsUIDAndQty){
		this.objectsUIDAndQty = objectsUIDAndQty;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.objectsUIDAndQty.size());
			int _loc2_ = 0;
			while( _loc2_ < this.objectsUIDAndQty.size()){
				this.objectsUIDAndQty.get(_loc2_).Serialize(writer);
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
			this.objectsUIDAndQty = new ArrayList<ObjectItemQuantity>();
			while( _loc3_ <  _loc2_){
				ObjectItemQuantity _loc15_ = new ObjectItemQuantity();
				_loc15_.Deserialize(reader);
				this.objectsUIDAndQty.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
