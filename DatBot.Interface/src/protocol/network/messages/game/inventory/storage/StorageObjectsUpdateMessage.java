package protocol.network.messages.game.inventory.storage;

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
public class StorageObjectsUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 6036;

	public List<ObjectItem> objectList;

	public StorageObjectsUpdateMessage(){
	}

	public StorageObjectsUpdateMessage(List<ObjectItem> objectList){
		this.objectList = objectList;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.objectList.size());
			int _loc2_ = 0;
			while( _loc2_ < this.objectList.size()){
				this.objectList.get(_loc2_).Serialize(writer);
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
			this.objectList = new ArrayList<ObjectItem>();
			while( _loc3_ <  _loc2_){
				ObjectItem _loc15_ = new ObjectItem();
				_loc15_.Deserialize(reader);
				this.objectList.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(ObjectItem a : objectList) {
			//Network.appendDebug("objectList : " + a);
		//}
	//}
}
