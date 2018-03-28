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

	private int firstNameId;
	private int lastNameId;
	private int level;
	private int guildId;
	private double uid;

	public int getFirstNameId() { return this.firstNameId; }
	public void setFirstNameId(int firstNameId) { this.firstNameId = firstNameId; };
	public int getLastNameId() { return this.lastNameId; }
	public void setLastNameId(int lastNameId) { this.lastNameId = lastNameId; };
	public int getLevel() { return this.level; }
	public void setLevel(int level) { this.level = level; };
	public int getGuildId() { return this.guildId; }
	public void setGuildId(int guildId) { this.guildId = guildId; };
	public double getUid() { return this.uid; }
	public void setUid(double uid) { this.uid = uid; };

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
	}

}
