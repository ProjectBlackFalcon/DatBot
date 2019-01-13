package protocol.network.messages.game.context.roleplay.breach;

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
public class BreachCharactersMessage extends NetworkMessage {
	public static final int ProtocolId = 6811;

	private List<Long> characters;

	public List<Long> getCharacters() { return this.characters; }
	public void setCharacters(List<Long> characters) { this.characters = characters; };

	public BreachCharactersMessage(){
	}

	public BreachCharactersMessage(List<Long> characters){
		this.characters = characters;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.characters.size());
			int _loc2_ = 0;
			while( _loc2_ < this.characters.size()){
				writer.writeVarLong(this.characters.get(_loc2_));
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
			this.characters = new ArrayList<Long>();
			while( _loc3_ <  _loc2_){
				long _loc15_ = reader.readVarLong();
				this.characters.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
