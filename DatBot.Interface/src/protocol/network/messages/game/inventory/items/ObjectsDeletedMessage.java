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

@SuppressWarnings("unused")
public class ObjectsDeletedMessage extends NetworkMessage {
	public static final int ProtocolId = 6034;

	public List<Integer> objectUID;

	public ObjectsDeletedMessage(){
	}

	public ObjectsDeletedMessage(List<Integer> objectUID){
		this.objectUID = objectUID;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.objectUID.size());
			int _loc2_ = 0;
			while( _loc2_ < this.objectUID.size()){
				writer.writeVarInt(this.objectUID.get(_loc2_));
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
			this.objectUID = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarInt();
				this.objectUID.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(Integer a : objectUID) {
			//Network.appendDebug("objectUID : " + a);
		//}
	//}
}
