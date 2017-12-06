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
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class RemodelingInformation extends NetworkMessage {
	public static final int ProtocolId = 480;

	public String name;
	public int breed;
	public boolean sex;
	public int cosmeticId;
	public List<Integer> colors;

	public RemodelingInformation(){
	}

	public RemodelingInformation(String name, int breed, boolean sex, int cosmeticId, List<Integer> colors){
		this.name = name;
		this.breed = breed;
		this.sex = sex;
		this.cosmeticId = cosmeticId;
		this.colors = colors;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.name);
			writer.writeByte(this.breed);
			writer.writeBoolean(this.sex);
			writer.writeVarShort(this.cosmeticId);
			writer.writeShort(this.colors.size());
			int _loc2_ = 0;
			while( _loc2_ < this.colors.size()){
				writer.writeInt(this.colors.get(_loc2_));
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.name = reader.readUTF();
			this.breed = reader.readByte();
			this.sex = reader.readBoolean();
			this.cosmeticId = reader.readVarShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.colors = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readInt();
				this.colors.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("name : " + this.name);
		//Network.appendDebug("breed : " + this.breed);
		//Network.appendDebug("sex : " + this.sex);
		//Network.appendDebug("cosmeticId : " + this.cosmeticId);
		//for(Integer a : colors) {
			//Network.appendDebug("colors : " + a);
		//}
	//}
}
