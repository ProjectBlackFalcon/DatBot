package protocol.network.types.game.character.choice;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.character.AbstractCharacterInformation;

@SuppressWarnings("unused")
public class AbstractCharacterToRefurbishInformation extends AbstractCharacterInformation {
	public static final int ProtocolId = 475;

	public List<Integer> colors;
	public int cosmeticId;

	public AbstractCharacterToRefurbishInformation(){
	}

	public AbstractCharacterToRefurbishInformation(List<Integer> colors, int cosmeticId){
		this.colors = colors;
		this.cosmeticId = cosmeticId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.colors.size());
			int _loc2_ = 0;
			while( _loc2_ < this.colors.size()){
				writer.writeInt(this.colors.get(_loc2_));
				_loc2_++;
			}
			writer.writeVarInt(this.cosmeticId);
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
			this.colors = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readInt();
				this.colors.add(_loc15_);
				_loc3_++;
			}
			this.cosmeticId = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(Integer a : colors) {
			//Network.appendDebug("colors : " + a);
		//}
		//Network.appendDebug("cosmeticId : " + this.cosmeticId);
	//}
}
