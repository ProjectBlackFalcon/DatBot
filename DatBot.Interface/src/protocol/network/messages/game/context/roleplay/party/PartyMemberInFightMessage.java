package protocol.network.messages.game.context.roleplay.party;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.party.AbstractPartyMessage;
import protocol.network.types.game.context.MapCoordinatesExtended;

@SuppressWarnings("unused")
public class PartyMemberInFightMessage extends AbstractPartyMessage {
	public static final int ProtocolId = 6342;

	public int reason;
	public long memberId;
	public int memberAccountId;
	public String memberName;
	public int fightId;
	public MapCoordinatesExtended fightMap;
	public int timeBeforeFightStart;

	public PartyMemberInFightMessage(){
	}

	public PartyMemberInFightMessage(int reason, long memberId, int memberAccountId, String memberName, int fightId, MapCoordinatesExtended fightMap, int timeBeforeFightStart){
		this.reason = reason;
		this.memberId = memberId;
		this.memberAccountId = memberAccountId;
		this.memberName = memberName;
		this.fightId = fightId;
		this.fightMap = fightMap;
		this.timeBeforeFightStart = timeBeforeFightStart;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.reason);
			writer.writeVarLong(this.memberId);
			writer.writeInt(this.memberAccountId);
			writer.writeUTF(this.memberName);
			writer.writeInt(this.fightId);
			fightMap.Serialize(writer);
			writer.writeVarShort(this.timeBeforeFightStart);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.reason = reader.readByte();
			this.memberId = reader.readVarLong();
			this.memberAccountId = reader.readInt();
			this.memberName = reader.readUTF();
			this.fightId = reader.readInt();
			this.fightMap = new MapCoordinatesExtended();
			this.fightMap.Deserialize(reader);
			this.timeBeforeFightStart = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("reason : " + this.reason);
		//Network.appendDebug("memberId : " + this.memberId);
		//Network.appendDebug("memberAccountId : " + this.memberAccountId);
		//Network.appendDebug("memberName : " + this.memberName);
		//Network.appendDebug("fightId : " + this.fightId);
		//Network.appendDebug("fightMap : " + this.fightMap);
		//Network.appendDebug("timeBeforeFightStart : " + this.timeBeforeFightStart);
	//}
}
