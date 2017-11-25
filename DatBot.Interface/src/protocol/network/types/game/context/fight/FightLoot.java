package protocol.network.types.game.context.fight;

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
public class FightLoot extends NetworkMessage {
	public static final int ProtocolId = 41;

	public List<Integer> objects;
	public long kamas;

	public FightLoot(){
	}

	public FightLoot(List<Integer> objects, long kamas){
		this.objects = objects;
		this.kamas = kamas;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.objects.size());
			int _loc2_ = 0;
			while( _loc2_ < this.objects.size()){
				writer.writeVarShort(this.objects.get(_loc2_));
				_loc2_++;
			}
			writer.writeVarLong(this.kamas);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.objects = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.objects.add(_loc15_);
				_loc3_++;
			}
			this.kamas = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(Integer a : objects) {
			//Network.appendDebug("objects : " + a);
		//}
		//Network.appendDebug("kamas : " + this.kamas);
	//}
}