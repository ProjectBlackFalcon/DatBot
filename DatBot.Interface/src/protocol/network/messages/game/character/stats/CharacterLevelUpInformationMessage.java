package protocol.network.messages.game.character.stats;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.character.stats.CharacterLevelUpMessage;

@SuppressWarnings("unused")
public class CharacterLevelUpInformationMessage extends CharacterLevelUpMessage {
	public static final int ProtocolId = 6076;

	private String name;
	private long id;

	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; };
	public long getId() { return this.id; }
	public void setId(long id) { this.id = id; };

	public CharacterLevelUpInformationMessage(){
	}

	public CharacterLevelUpInformationMessage(String name, long id){
		this.name = name;
		this.id = id;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeUTF(this.name);
			writer.writeVarLong(this.id);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.name = reader.readUTF();
			this.id = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
