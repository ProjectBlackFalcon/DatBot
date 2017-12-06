package protocol.network.types.game.inventory.preset;

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
public class IdolsPreset extends NetworkMessage {
	public static final int ProtocolId = 491;

	public int presetId;
	public int symbolId;
	public List<Integer> idolId;

	public IdolsPreset(){
	}

	public IdolsPreset(int presetId, int symbolId, List<Integer> idolId){
		this.presetId = presetId;
		this.symbolId = symbolId;
		this.idolId = idolId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.presetId);
			writer.writeByte(this.symbolId);
			writer.writeShort(this.idolId.size());
			int _loc2_ = 0;
			while( _loc2_ < this.idolId.size()){
				writer.writeVarShort(this.idolId.get(_loc2_));
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.presetId = reader.readByte();
			this.symbolId = reader.readByte();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.idolId = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.idolId.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("presetId : " + this.presetId);
		//Network.appendDebug("symbolId : " + this.symbolId);
		//for(Integer a : idolId) {
			//Network.appendDebug("idolId : " + a);
		//}
	//}
}
