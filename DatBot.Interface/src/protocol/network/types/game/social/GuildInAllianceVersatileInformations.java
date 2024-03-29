package protocol.network.types.game.social;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.social.GuildVersatileInformations;

@SuppressWarnings("unused")
public class GuildInAllianceVersatileInformations extends GuildVersatileInformations {
	public static final int ProtocolId = 437;

	private int allianceId;

	public int getAllianceId() { return this.allianceId; }
	public void setAllianceId(int allianceId) { this.allianceId = allianceId; };

	public GuildInAllianceVersatileInformations(){
	}

	public GuildInAllianceVersatileInformations(int allianceId){
		this.allianceId = allianceId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarInt(this.allianceId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.allianceId = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
