package protocol.network.messages.game.pvp;

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
public class UpdateSelfAgressableStatusMessage extends NetworkMessage {
	public static final int ProtocolId = 6456;

	public int status;
	public int probationTime;

	public UpdateSelfAgressableStatusMessage(){
	}

	public UpdateSelfAgressableStatusMessage(int status, int probationTime){
		this.status = status;
		this.probationTime = probationTime;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.status);
			writer.writeInt(this.probationTime);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.status = reader.readByte();
			this.probationTime = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("status : " + this.status);
		//Network.appendDebug("probationTime : " + this.probationTime);
	//}
}
