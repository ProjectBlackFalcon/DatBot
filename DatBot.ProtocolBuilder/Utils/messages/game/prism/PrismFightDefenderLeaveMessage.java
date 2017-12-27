package protocol.network.messages.game.prism;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class PrismFightDefenderLeaveMessage extends NetworkMessage {
	public static final int ProtocolId = 5892;

	private int subAreaId;
	private int fightId;
	private long fighterToRemoveId;

	public int getSubAreaId() { return this.subAreaId; };
	public void setSubAreaId(int subAreaId) { this.subAreaId = subAreaId; };
	public int getFightId() { return this.fightId; };
	public void setFightId(int fightId) { this.fightId = fightId; };
	public long getFighterToRemoveId() { return this.fighterToRemoveId; };
	public void setFighterToRemoveId(long fighterToRemoveId) { this.fighterToRemoveId = fighterToRemoveId; };

	public PrismFightDefenderLeaveMessage(){
	}

	public PrismFightDefenderLeaveMessage(int subAreaId, int fightId, long fighterToRemoveId){
		this.subAreaId = subAreaId;
		this.fightId = fightId;
		this.fighterToRemoveId = fighterToRemoveId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.subAreaId);
			writer.writeVarShort(this.fightId);
			writer.writeVarLong(this.fighterToRemoveId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.subAreaId = reader.readVarShort();
			this.fightId = reader.readVarShort();
			this.fighterToRemoveId = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
