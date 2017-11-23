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
import protocol.network.types.game.context.roleplay.GameRolePlayActorInformations;
import protocol.network.types.game.context.roleplay.GroupMonsterStaticInformations;

@SuppressWarnings("unused")
public class GameRolePlayGroupMonsterInformations extends GameRolePlayActorInformations {
	public static final int ProtocolId = 160;

	public GroupMonsterStaticInformations staticInfos;
	public double creationTime;
	public int ageBonusRate;
	public int lootShare;
	public int alignmentSide;
	public boolean keyRingBonus;
	public boolean hasHardcoreDrop;
	public boolean hasAVARewardToken;

	public GameRolePlayGroupMonsterInformations(){
	}

	public GameRolePlayGroupMonsterInformations(GroupMonsterStaticInformations staticInfos, double creationTime, int ageBonusRate, int lootShare, int alignmentSide, boolean keyRingBonus, boolean hasHardcoreDrop, boolean hasAVARewardToken){
		this.staticInfos = staticInfos;
		this.creationTime = creationTime;
		this.ageBonusRate = ageBonusRate;
		this.lootShare = lootShare;
		this.alignmentSide = alignmentSide;
		this.keyRingBonus = keyRingBonus;
		this.hasHardcoreDrop = hasHardcoreDrop;
		this.hasAVARewardToken = hasAVARewardToken;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			byte flag = 0;
			flag = BooleanByteWrapper.SetFlag(0, flag, keyRingBonus);
			flag = BooleanByteWrapper.SetFlag(1, flag, hasHardcoreDrop);
			flag = BooleanByteWrapper.SetFlag(2, flag, hasAVARewardToken);
			writer.writeByte(flag);
			writer.writeShort(GroupMonsterStaticInformations.ProtocolId);
			writer.writeDouble(this.creationTime);
			writer.writeInt(this.ageBonusRate);
			writer.writeByte(this.lootShare);
			writer.writeByte(this.alignmentSide);
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
			this.keyRingBonus = BooleanByteWrapper.GetFlag(flag, (byte) 0);
			this.hasHardcoreDrop = BooleanByteWrapper.GetFlag(flag, (byte) 1);
			this.hasAVARewardToken = BooleanByteWrapper.GetFlag(flag, (byte) 2);
			this.staticInfos = (GroupMonsterStaticInformations) ProtocolTypeManager.getInstance(reader.readShort());
			this.staticInfos.Deserialize(reader);
			this.creationTime = reader.readDouble();
			this.ageBonusRate = reader.readInt();
			this.lootShare = reader.readByte();
			this.alignmentSide = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("staticInfos : " + this.staticInfos);
		//Network.appendDebug("creationTime : " + this.creationTime);
		//Network.appendDebug("ageBonusRate : " + this.ageBonusRate);
		//Network.appendDebug("lootShare : " + this.lootShare);
		//Network.appendDebug("alignmentSide : " + this.alignmentSide);
		//Network.appendDebug("keyRingBonus : " + this.keyRingBonus);
		//Network.appendDebug("hasHardcoreDrop : " + this.hasHardcoreDrop);
		//Network.appendDebug("hasAVARewardToken : " + this.hasAVARewardToken);
	//}
}
