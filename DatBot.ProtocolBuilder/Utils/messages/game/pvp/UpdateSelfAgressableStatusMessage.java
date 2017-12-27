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

	private int status;
	private int probationTime;

	public int getStatus() { return this.status; };
	public void setStatus(int status) { this.status = status; };
	public int getProbationTime() { return this.probationTime; };
	public void setProbationTime(int probationTime) { this.probationTime = probationTime; };

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
	}

}
