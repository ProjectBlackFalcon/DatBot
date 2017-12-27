package protocol.network.messages.game.character.choice;

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
import protocol.network.types.game.character.choice.CharacterBaseInformations;

@SuppressWarnings("unused")
public class BasicCharactersListMessage extends NetworkMessage {
	public static final int ProtocolId = 6475;

	private List<CharacterBaseInformations> characters;

	public List<CharacterBaseInformations> getCharacters() { return this.characters; };
	public void setCharacters(List<CharacterBaseInformations> characters) { this.characters = characters; };

	public BasicCharactersListMessage(){
	}

	public BasicCharactersListMessage(List<CharacterBaseInformations> characters){
		this.characters = characters;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.characters.size());
			int _loc2_ = 0;
			while( _loc2_ < this.characters.size()){
				writer.writeShort(CharacterBaseInformations.ProtocolId);
				this.characters.get(_loc2_).Serialize(writer);
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
			this.characters = new ArrayList<CharacterBaseInformations>();
			while( _loc3_ <  _loc2_){
				CharacterBaseInformations _loc15_ = (CharacterBaseInformations) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.characters.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
