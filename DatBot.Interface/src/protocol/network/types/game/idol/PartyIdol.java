package protocol.network.types.game.idol;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.idol.Idol;

@SuppressWarnings("unused")
public class PartyIdol extends Idol {
	public static final int ProtocolId = 490;

	public List<Long> ownersIds;

	public PartyIdol(){
	}

	public PartyIdol(List<Long> ownersIds){
		this.ownersIds = ownersIds;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.ownersIds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.ownersIds.size()){
				writer.writeVarLong(this.ownersIds.get(_loc2_));
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
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.ownersIds = new ArrayList<Long>();
			while( _loc3_ <  _loc2_){
				long _loc15_ = reader.readVarLong();
				this.ownersIds.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//for(Long a : ownersIds) {
			//Network.appendDebug("ownersIds : " + a);
		//}
	//}
}
