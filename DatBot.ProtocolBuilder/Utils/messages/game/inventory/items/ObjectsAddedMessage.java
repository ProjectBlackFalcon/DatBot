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
import protocol.network.types.game.data.items.ObjectItem;

@SuppressWarnings("unused")
public class ObjectsAddedMessage extends NetworkMessage {
	public static final int ProtocolId = 6033;

	private List<ObjectItem> object;

	public List<ObjectItem> getObject() { return this.object; }
	public void setObject(List<ObjectItem> object) { this.object = object; };

	public ObjectsAddedMessage(){
	}

	public ObjectsAddedMessage(List<ObjectItem> object){
		this.object = object;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.object.size());
			int _loc2_ = 0;
			while( _loc2_ < this.object.size()){
				this.object.get(_loc2_).Serialize(writer);
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
			this.object = new ArrayList<ObjectItem>();
			while( _loc3_ <  _loc2_){
				ObjectItem _loc15_ = new ObjectItem();
				_loc15_.Deserialize(reader);
				this.object.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
