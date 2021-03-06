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
import protocol.network.types.game.character.choice.CharacterToRecolorInformation;
import protocol.network.types.game.character.choice.CharacterToRelookInformation;

@SuppressWarnings("unused")
public class CharactersListWithModificationsMessage extends CharactersListMessage {
	public static final int ProtocolId = 6120;

	public List<CharacterToRecolorInformation> charactersToRecolor;
	public List<Integer> charactersToRename;
	public List<Integer> unusableCharacters;
	public List<CharacterToRelookInformation> charactersToRelook;

	public CharactersListWithModificationsMessage(){
	}

	public CharactersListWithModificationsMessage(List<CharacterToRecolorInformation> charactersToRecolor, List<Integer> charactersToRename, List<Integer> unusableCharacters, List<CharacterToRelookInformation> charactersToRelook){
		this.charactersToRecolor = charactersToRecolor;
		this.charactersToRename = charactersToRename;
		this.unusableCharacters = unusableCharacters;
		this.charactersToRelook = charactersToRelook;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.charactersToRecolor.size());
			int _loc2_ = 0;
			while( _loc2_ < this.charactersToRecolor.size()){
				this.charactersToRecolor.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeShort(this.charactersToRename.size());
			int _loc3_ = 0;
			while( _loc3_ < this.charactersToRename.size()){
				writer.writeInt(this.charactersToRename.get(_loc3_));
				_loc3_++;
			}
			writer.writeShort(this.unusableCharacters.size());
			int _loc4_ = 0;
			while( _loc4_ < this.unusableCharacters.size()){
				writer.writeInt(this.unusableCharacters.get(_loc4_));
				_loc4_++;
			}
			writer.writeShort(this.charactersToRelook.size());
			int _loc5_ = 0;
			while( _loc5_ < this.charactersToRelook.size()){
				this.charactersToRelook.get(_loc5_).Serialize(writer);
				_loc5_++;
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
			this.charactersToRecolor = new ArrayList<CharacterToRecolorInformation>();
			while( _loc3_ <  _loc2_){
				CharacterToRecolorInformation _loc15_ = new CharacterToRecolorInformation();
				_loc15_.Deserialize(reader);
				this.charactersToRecolor.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.charactersToRename = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readInt();
				this.charactersToRename.add(_loc16_);
				_loc5_++;
			}
			int _loc6_  = reader.readShort();
			int _loc7_  = 0;
			this.unusableCharacters = new ArrayList<Integer>();
			while( _loc7_ <  _loc6_){
				int _loc17_ = reader.readInt();
				this.unusableCharacters.add(_loc17_);
				_loc7_++;
			}
			int _loc8_  = reader.readShort();
			int _loc9_  = 0;
			this.charactersToRelook = new ArrayList<CharacterToRelookInformation>();
			while( _loc9_ <  _loc8_){
				CharacterToRelookInformation _loc18_ = new CharacterToRelookInformation();
				_loc18_.Deserialize(reader);
				this.charactersToRelook.add(_loc18_);
				_loc9_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(CharacterToRecolorInformation a : charactersToRecolor) {
			//Network.appendDebug("charactersToRecolor : " + a);
		//}
		//for(Integer a : charactersToRename) {
			//Network.appendDebug("charactersToRename : " + a);
		//}
		//for(Integer a : unusableCharacters) {
			//Network.appendDebug("unusableCharacters : " + a);
		//}
		//for(CharacterToRelookInformation a : charactersToRelook) {
			//Network.appendDebug("charactersToRelook : " + a);
		//}
	//}
}
