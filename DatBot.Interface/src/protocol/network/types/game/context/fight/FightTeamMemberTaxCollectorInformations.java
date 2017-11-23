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
import protocol.network.types.game.context.fight.FightTeamMemberInformations;

@SuppressWarnings("unused")
public class FightTeamMemberTaxCollectorInformations extends FightTeamMemberInformations {
	public static final int ProtocolId = 177;

	public int firstNameId;
	public int lastNameId;
	public int level;
	public int guildId;
	public double uid;

	public FightTeamMemberTaxCollectorInformations(){
	}

	public FightTeamMemberTaxCollectorInformations(int firstNameId, int lastNameId, int level, int guildId, double uid){
		this.firstNameId = firstNameId;
		this.lastNameId = lastNameId;
		this.level = level;
		this.guildId = guildId;
		this.uid = uid;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.firstNameId);
			writer.writeVarShort(this.lastNameId);
			writer.writeByte(this.level);
			writer.writeVarInt(this.guildId);
			writer.writeDouble(this.uid);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.firstNameId = reader.readVarShort();
			this.lastNameId = reader.readVarShort();
			this.level = reader.readByte();
			this.guildId = reader.readVarInt();
			this.uid = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("firstNameId : " + this.firstNameId);
		//Network.appendDebug("lastNameId : " + this.lastNameId);
		//Network.appendDebug("level : " + this.level);
		//Network.appendDebug("guildId : " + this.guildId);
		//Network.appendDebug("uid : " + this.uid);
	//}
}
