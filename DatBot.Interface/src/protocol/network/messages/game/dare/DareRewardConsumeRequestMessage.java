package protocol.network.messages.game.dare;

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
public class DareRewardConsumeRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6676;

	public double dareId;
	public int type;

	public DareRewardConsumeRequestMessage(){
	}

	public DareRewardConsumeRequestMessage(double dareId, int type){
		this.dareId = dareId;
		this.type = type;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.dareId);
			writer.writeByte(this.type);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.dareId = reader.readDouble();
			this.type = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("dareId : " + this.dareId);
		//Network.appendDebug("type : " + this.type);
	//}
}
