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
import protocol.network.types.game.context.roleplay.HumanOption;
import protocol.network.types.game.context.roleplay.GuildInformations;

@SuppressWarnings("unused")
public class HumanOptionGuild extends HumanOption {
	public static final int ProtocolId = 409;

	public GuildInformations guildInformations;

	public HumanOptionGuild(){
	}

	public HumanOptionGuild(GuildInformations guildInformations){
		this.guildInformations = guildInformations;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			guildInformations.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.guildInformations = new GuildInformations();
			this.guildInformations.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("guildInformations : " + this.guildInformations);
	//}
}
