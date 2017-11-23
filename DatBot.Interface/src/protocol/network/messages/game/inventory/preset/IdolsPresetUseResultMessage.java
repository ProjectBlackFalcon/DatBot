package protocol.network.messages.game.inventory.preset;

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
public class IdolsPresetUseResultMessage extends NetworkMessage {
	public static final int ProtocolId = 6614;

	public int presetId;
	public int code;
	public List<Integer> missingIdols;

	public IdolsPresetUseResultMessage(){
	}

	public IdolsPresetUseResultMessage(int presetId, int code, List<Integer> missingIdols){
		this.presetId = presetId;
		this.code = code;
		this.missingIdols = missingIdols;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.presetId);
			writer.writeByte(this.code);
			writer.writeShort(this.missingIdols.size());
			int _loc2_ = 0;
			while( _loc2_ < this.missingIdols.size()){
				writer.writeVarShort(this.missingIdols.get(_loc2_));
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
			this.code = reader.readByte();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.missingIdols = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.missingIdols.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("presetId : " + this.presetId);
		//Network.appendDebug("code : " + this.code);
		//for(Integer a : missingIdols) {
			//Network.appendDebug("missingIdols : " + a);
		//}
	//}
}
