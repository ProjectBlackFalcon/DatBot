package protocol.network.types.game.house;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.house.HouseInstanceInformations;
import protocol.network.types.game.context.roleplay.GuildInformations;

@SuppressWarnings("unused")
public class HouseGuildedInformations extends HouseInstanceInformations {
	public static final int ProtocolId = 512;

	private GuildInformations guildInfo;

	public GuildInformations getGuildInfo() { return this.guildInfo; };
	public void setGuildInfo(GuildInformations guildInfo) { this.guildInfo = guildInfo; };

	public HouseGuildedInformations(){
	}

	public HouseGuildedInformations(GuildInformations guildInfo){
		this.guildInfo = guildInfo;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			guildInfo.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.guildInfo = new GuildInformations();
			this.guildInfo.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
