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
public class CharacterRemodelingInformation extends AbstractCharacterInformation {
	public static final int ProtocolId = 479;

	private String name;
	private int breed;
	private boolean sex;
	private int cosmeticId;
	private List<Integer> colors;

	public String getName() { return this.name; };
	public void setName(String name) { this.name = name; };
	public int getBreed() { return this.breed; };
	public void setBreed(int breed) { this.breed = breed; };
	public boolean isSex() { return this.sex; };
	public void setSex(boolean sex) { this.sex = sex; };
	public int getCosmeticId() { return this.cosmeticId; };
	public void setCosmeticId(int cosmeticId) { this.cosmeticId = cosmeticId; };
	public List<Integer> getColors() { return this.colors; };
	public void setColors(List<Integer> colors) { this.colors = colors; };

	public CharacterRemodelingInformation(){
	}

	public CharacterRemodelingInformation(String name, int breed, boolean sex, int cosmeticId, List<Integer> colors){
		this.name = name;
		this.breed = breed;
		this.sex = sex;
		this.cosmeticId = cosmeticId;
		this.colors = colors;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
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
			super.Deserialize(reader);
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

}
