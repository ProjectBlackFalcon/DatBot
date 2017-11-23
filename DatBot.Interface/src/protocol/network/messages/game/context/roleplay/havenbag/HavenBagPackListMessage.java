package protocol.network.messages.game.context.roleplay.havenbag;

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
public class HavenBagPackListMessage extends NetworkMessage {
	public static final int ProtocolId = 6620;

	public List<Integer> packIds;

	public HavenBagPackListMessage(){
	}

	public HavenBagPackListMessage(List<Integer> packIds){
		this.packIds = packIds;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.packIds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.packIds.size()){
				writer.writeByte(this.packIds.get(_loc2_));
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
			this.packIds = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readByte();
				this.packIds.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(Integer a : packIds) {
			//Network.appendDebug("packIds : " + a);
		//}
	//}
}
