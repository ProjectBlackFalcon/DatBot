package protocol.network.messages.game.character.creation;

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
public class CharacterCreationRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 160;

	public String name;
	public int breed;
	public boolean sex;
	public List<Integer> colors;
	public int cosmeticId;

	public CharacterCreationRequestMessage(){
	}

	public CharacterCreationRequestMessage(String name, int breed, boolean sex, List<Integer> colors, int cosmeticId){
		this.name = name;
		this.breed = breed;
		this.sex = sex;
		this.colors = colors;
		this.cosmeticId = cosmeticId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.name);
			writer.writeByte(this.breed);
			writer.writeBoolean(this.sex);
			int _loc2_ = 0;
			while( _loc2_ < 5){
				writer.writeInt(this.colors.get(_loc2_));
				_loc2_++;
			}
			writer.writeVarShort(this.cosmeticId);
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
			int _loc2_  = 0;
			this.colors = new ArrayList<Integer>();
			while( _loc2_ < 5){
				this.colors.add(reader.readInt());
				_loc2_++;
			}
			this.cosmeticId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("name : " + this.name);
		//Network.appendDebug("breed : " + this.breed);
		//Network.appendDebug("sex : " + this.sex);
		//for(Integer a : colors) {
			//Network.appendDebug("colors : " + a);
		//}
		//Network.appendDebug("cosmeticId : " + this.cosmeticId);
	//}
}
