package protocol.network.types.game.context.fight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.fight.FightResultFighterListEntry;
import protocol.network.types.game.context.roleplay.BasicGuildInformations;

@SuppressWarnings("unused")
public class FightResultTaxCollectorListEntry extends FightResultFighterListEntry {
	public static final int ProtocolId = 84;

	public int level;
	public BasicGuildInformations guildInfo;
	public int experienceForGuild;

	public FightResultTaxCollectorListEntry(){
	}

	public FightResultTaxCollectorListEntry(int level, BasicGuildInformations guildInfo, int experienceForGuild){
		this.level = level;
		this.guildInfo = guildInfo;
		this.experienceForGuild = experienceForGuild;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.level);
			guildInfo.Serialize(writer);
			writer.writeInt(this.experienceForGuild);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.level = reader.readByte();
			this.guildInfo = new BasicGuildInformations();
			this.guildInfo.Deserialize(reader);
			this.experienceForGuild = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("level : " + this.level);
		//Network.appendDebug("guildInfo : " + this.guildInfo);
		//Network.appendDebug("experienceForGuild : " + this.experienceForGuild);
	//}
}
