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

@SuppressWarnings("unused")
public class StorageObjectsRemoveMessage extends NetworkMessage {
	public static final int ProtocolId = 6035;

	private List<Integer> objectUIDList;

	public List<Integer> getObjectUIDList() { return this.objectUIDList; };
	public void setObjectUIDList(List<Integer> objectUIDList) { this.objectUIDList = objectUIDList; };

	public StorageObjectsRemoveMessage(){
	}

	public StorageObjectsRemoveMessage(List<Integer> objectUIDList){
		this.objectUIDList = objectUIDList;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.objectUIDList.size());
			int _loc2_ = 0;
			while( _loc2_ < this.objectUIDList.size()){
				writer.writeVarInt(this.objectUIDList.get(_loc2_));
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
			this.objectUIDList = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarInt();
				this.objectUIDList.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
