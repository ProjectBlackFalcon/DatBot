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

	private String name;
	private int breed;
	private boolean sex;
	private List<Integer> colors;
	private int cosmeticId;

	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; };
	public int getBreed() { return this.breed; }
	public void setBreed(int breed) { this.breed = breed; };
	public boolean isSex() { return this.sex; }
	public void setSex(boolean sex) { this.sex = sex; };

	@Override
	public String toString() {
		return "CharacterCreationRequestMessage{" +
			"name='" + name + '\'' +
			", breed=" + breed +
			", sex=" + sex +
			", colors=" + colors +
			", cosmeticId=" + cosmeticId +
			'}';
	}

	public List<Integer> getColors() { return this.colors; }
	public void setColors(List<Integer> colors) { this.colors = colors; };
	public int getCosmeticId() { return this.cosmeticId; }
	public void setCosmeticId(int cosmeticId) { this.cosmeticId = cosmeticId; };

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
	}

}
