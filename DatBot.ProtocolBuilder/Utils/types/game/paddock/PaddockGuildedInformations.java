package protocol.network.types.game.paddock;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.paddock.PaddockBuyableInformations;
import protocol.network.types.game.context.roleplay.GuildInformations;

@SuppressWarnings("unused")
public class PaddockGuildedInformations extends PaddockBuyableInformations {
	public static final int ProtocolId = 508;

	private boolean deserted;
	private GuildInformations guildInfo;

	public boolean isDeserted() { return this.deserted; }
	public void setDeserted(boolean deserted) { this.deserted = deserted; };
	public GuildInformations getGuildInfo() { return this.guildInfo; }
	public void setGuildInfo(GuildInformations guildInfo) { this.guildInfo = guildInfo; };

	public PaddockGuildedInformations(){
	}

	public PaddockGuildedInformations(boolean deserted, GuildInformations guildInfo){
		this.deserted = deserted;
		this.guildInfo = guildInfo;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeBoolean(this.deserted);
			guildInfo.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.deserted = reader.readBoolean();
			this.guildInfo = new GuildInformations();
			this.guildInfo.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
