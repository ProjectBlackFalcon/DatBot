package protocol.network.types.game.context.roleplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.roleplay.BasicNamedAllianceInformations;
import protocol.network.types.game.guild.GuildEmblem;

@SuppressWarnings("unused")
public class AllianceInformations extends BasicNamedAllianceInformations {
	public static final int ProtocolId = 417;

	private GuildEmblem allianceEmblem;

	public GuildEmblem getAllianceEmblem() { return this.allianceEmblem; };
	public void setAllianceEmblem(GuildEmblem allianceEmblem) { this.allianceEmblem = allianceEmblem; };

	public AllianceInformations(){
	}

	public AllianceInformations(GuildEmblem allianceEmblem){
		this.allianceEmblem = allianceEmblem;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			allianceEmblem.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.allianceEmblem = new GuildEmblem();
			this.allianceEmblem.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
