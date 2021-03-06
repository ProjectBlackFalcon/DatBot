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
import protocol.network.messages.game.character.choice.CharactersListMessage;
import protocol.network.types.game.character.choice.CharacterToRemodelInformations;

@SuppressWarnings("unused")
public class CharactersListWithRemodelingMessage extends CharactersListMessage {
	public static final int ProtocolId = 6550;

	private List<CharacterToRemodelInformations> charactersToRemodel;

	public List<CharacterToRemodelInformations> getCharactersToRemodel() { return this.charactersToRemodel; }
	public void setCharactersToRemodel(List<CharacterToRemodelInformations> charactersToRemodel) { this.charactersToRemodel = charactersToRemodel; };

	public CharactersListWithRemodelingMessage(){
	}

	public CharactersListWithRemodelingMessage(List<CharacterToRemodelInformations> charactersToRemodel){
		this.charactersToRemodel = charactersToRemodel;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.charactersToRemodel.size());
			int _loc2_ = 0;
			while( _loc2_ < this.charactersToRemodel.size()){
				this.charactersToRemodel.get(_loc2_).Serialize(writer);
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
			this.charactersToRemodel = new ArrayList<CharacterToRemodelInformations>();
			while( _loc3_ <  _loc2_){
				CharacterToRemodelInformations _loc15_ = new CharacterToRemodelInformations();
				_loc15_.Deserialize(reader);
				this.charactersToRemodel.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
