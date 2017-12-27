package protocol.network.messages.game.report;

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
public class CharacterReportMessage extends NetworkMessage {
	public static final int ProtocolId = 6079;

	private long reportedId;
	private int reason;

	public long getReportedId() { return this.reportedId; };
	public void setReportedId(long reportedId) { this.reportedId = reportedId; };
	public int getReason() { return this.reason; };
	public void setReason(int reason) { this.reason = reason; };

	public CharacterReportMessage(){
	}

	public CharacterReportMessage(long reportedId, int reason){
		this.reportedId = reportedId;
		this.reason = reason;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.reportedId);
			writer.writeByte(this.reason);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.reportedId = reader.readVarLong();
			this.reason = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
