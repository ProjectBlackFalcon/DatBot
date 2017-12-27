package protocol.network.types.game.character;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.character.CharacterMinimalPlusLookInformations;
import protocol.network.types.game.context.roleplay.BasicGuildInformations;

@SuppressWarnings("unused")
public class CharacterMinimalGuildInformations extends CharacterMinimalPlusLookInformations {
	public static final int ProtocolId = 445;

	private BasicGuildInformations guild;

	public BasicGuildInformations getGuild() { return this.guild; };
	public void setGuild(BasicGuildInformations guild) { this.guild = guild; };

	public CharacterMinimalGuildInformations(){
	}

	public CharacterMinimalGuildInformations(BasicGuildInformations guild){
		this.guild = guild;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			guild.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.guild = new BasicGuildInformations();
			this.guild.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
