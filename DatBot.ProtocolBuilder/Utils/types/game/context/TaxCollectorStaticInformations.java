package protocol.network.types.game.context;

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
import protocol.network.types.game.context.roleplay.GuildInformations;

@SuppressWarnings("unused")
public class TaxCollectorStaticInformations extends NetworkMessage {
	public static final int ProtocolId = 147;

	private int firstNameId;
	private int lastNameId;
	private GuildInformations guildIdentity;

	public int getFirstNameId() { return this.firstNameId; };
	public void setFirstNameId(int firstNameId) { this.firstNameId = firstNameId; };
	public int getLastNameId() { return this.lastNameId; };
	public void setLastNameId(int lastNameId) { this.lastNameId = lastNameId; };
	public GuildInformations getGuildIdentity() { return this.guildIdentity; };
	public void setGuildIdentity(GuildInformations guildIdentity) { this.guildIdentity = guildIdentity; };

	public TaxCollectorStaticInformations(){
	}

	public TaxCollectorStaticInformations(int firstNameId, int lastNameId, GuildInformations guildIdentity){
		this.firstNameId = firstNameId;
		this.lastNameId = lastNameId;
		this.guildIdentity = guildIdentity;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.firstNameId);
			writer.writeVarShort(this.lastNameId);
			guildIdentity.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.firstNameId = reader.readVarShort();
			this.lastNameId = reader.readVarShort();
			this.guildIdentity = new GuildInformations();
			this.guildIdentity.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
