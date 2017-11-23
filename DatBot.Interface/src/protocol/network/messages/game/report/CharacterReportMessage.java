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

	public long reportedId;
	public int reason;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("reportedId : " + this.reportedId);
		//Network.appendDebug("reason : " + this.reason);
	//}
}
