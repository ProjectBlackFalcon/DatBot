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

	private int reason;
	private long memberId;
	private int memberAccountId;
	private String memberName;
	private int fightId;
	private MapCoordinatesExtended fightMap;
	private int timeBeforeFightStart;

	public int getReason() { return this.reason; }
	public void setReason(int reason) { this.reason = reason; };
	public long getMemberId() { return this.memberId; }
	public void setMemberId(long memberId) { this.memberId = memberId; };
	public int getMemberAccountId() { return this.memberAccountId; }
	public void setMemberAccountId(int memberAccountId) { this.memberAccountId = memberAccountId; };
	public String getMemberName() { return this.memberName; }
	public void setMemberName(String memberName) { this.memberName = memberName; };
	public int getFightId() { return this.fightId; }
	public void setFightId(int fightId) { this.fightId = fightId; };
	public MapCoordinatesExtended getFightMap() { return this.fightMap; }
	public void setFightMap(MapCoordinatesExtended fightMap) { this.fightMap = fightMap; };
	public int getTimeBeforeFightStart() { return this.timeBeforeFightStart; }
	public void setTimeBeforeFightStart(int timeBeforeFightStart) { this.timeBeforeFightStart = timeBeforeFightStart; };

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
			writer.writeVarShort(this.fightId);
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
			this.fightId = reader.readVarShort();
			this.fightMap = new MapCoordinatesExtended();
			this.fightMap.Deserialize(reader);
			this.timeBeforeFightStart = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
