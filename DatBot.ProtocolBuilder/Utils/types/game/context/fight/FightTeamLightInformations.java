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
import protocol.network.types.game.context.fight.AbstractFightTeamInformations;

@SuppressWarnings("unused")
public class FightTeamLightInformations extends AbstractFightTeamInformations {
	public static final int ProtocolId = 115;

	private int teamMembersCount;
	private int meanLevel;
	private boolean hasFriend;
	private boolean hasGuildMember;
	private boolean hasAllianceMember;
	private boolean hasGroupMember;
	private boolean hasMyTaxCollector;

	public int getTeamMembersCount() { return this.teamMembersCount; };
	public void setTeamMembersCount(int teamMembersCount) { this.teamMembersCount = teamMembersCount; };
	public int getMeanLevel() { return this.meanLevel; };
	public void setMeanLevel(int meanLevel) { this.meanLevel = meanLevel; };
	public boolean isHasFriend() { return this.hasFriend; };
	public void setHasFriend(boolean hasFriend) { this.hasFriend = hasFriend; };
	public boolean isHasGuildMember() { return this.hasGuildMember; };
	public void setHasGuildMember(boolean hasGuildMember) { this.hasGuildMember = hasGuildMember; };
	public boolean isHasAllianceMember() { return this.hasAllianceMember; };
	public void setHasAllianceMember(boolean hasAllianceMember) { this.hasAllianceMember = hasAllianceMember; };
	public boolean isHasGroupMember() { return this.hasGroupMember; };
	public void setHasGroupMember(boolean hasGroupMember) { this.hasGroupMember = hasGroupMember; };
	public boolean isHasMyTaxCollector() { return this.hasMyTaxCollector; };
	public void setHasMyTaxCollector(boolean hasMyTaxCollector) { this.hasMyTaxCollector = hasMyTaxCollector; };

	public FightTeamLightInformations(){
	}

	public FightTeamLightInformations(int teamMembersCount, int meanLevel, boolean hasFriend, boolean hasGuildMember, boolean hasAllianceMember, boolean hasGroupMember, boolean hasMyTaxCollector){
		this.teamMembersCount = teamMembersCount;
		this.meanLevel = meanLevel;
		this.hasFriend = hasFriend;
		this.hasGuildMember = hasGuildMember;
		this.hasAllianceMember = hasAllianceMember;
		this.hasGroupMember = hasGroupMember;
		this.hasMyTaxCollector = hasMyTaxCollector;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, hasFriend);
			flag = BooleanByteWrapper.SetFlag(1, flag, hasGuildMember);
			flag = BooleanByteWrapper.SetFlag(2, flag, hasAllianceMember);
			flag = BooleanByteWrapper.SetFlag(3, flag, hasGroupMember);
			flag = BooleanByteWrapper.SetFlag(4, flag, hasMyTaxCollector);
			writer.writeByte(flag);
			writer.writeByte(this.teamMembersCount);
			writer.writeVarInt(this.meanLevel);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			byte flag;
			flag = (byte) reader.readUnsignedByte();
			this.hasFriend = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.hasGuildMember = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.hasAllianceMember = BooleanByteWrapper.GetFlag(flag, (byte) 2);
			this.hasGroupMember = BooleanByteWrapper.GetFlag(flag, (byte) 3);
			this.hasMyTaxCollector = BooleanByteWrapper.GetFlag(flag, (byte) 4);
			this.teamMembersCount = reader.readByte();
			this.meanLevel = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
