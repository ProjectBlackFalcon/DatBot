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
import protocol.network.types.game.character.CharacterMinimalGuildInformations;
import protocol.network.types.game.context.roleplay.BasicAllianceInformations;

@SuppressWarnings("unused")
public class CharacterMinimalAllianceInformations extends CharacterMinimalGuildInformations {
	public static final int ProtocolId = 444;

	private BasicAllianceInformations alliance;

	public BasicAllianceInformations getAlliance() { return this.alliance; };
	public void setAlliance(BasicAllianceInformations alliance) { this.alliance = alliance; };

	public CharacterMinimalAllianceInformations(){
	}

	public CharacterMinimalAllianceInformations(BasicAllianceInformations alliance){
		this.alliance = alliance;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			alliance.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.alliance = new BasicAllianceInformations();
			this.alliance.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
