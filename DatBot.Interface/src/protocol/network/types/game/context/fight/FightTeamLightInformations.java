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

	public int teamMembersCount;
	public int meanLevel;
	public boolean hasFriend;
	public boolean hasGuildMember;
	public boolean hasAllianceMember;
	public boolean hasGroupMember;
	public boolean hasMyTaxCollector;

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

	//private void append(){
		//Network.appendDebug("teamMembersCount : " + this.teamMembersCount);
		//Network.appendDebug("meanLevel : " + this.meanLevel);
		//Network.appendDebug("hasFriend : " + this.hasFriend);
		//Network.appendDebug("hasGuildMember : " + this.hasGuildMember);
		//Network.appendDebug("hasAllianceMember : " + this.hasAllianceMember);
		//Network.appendDebug("hasGroupMember : " + this.hasGroupMember);
		//Network.appendDebug("hasMyTaxCollector : " + this.hasMyTaxCollector);
	//}
}
