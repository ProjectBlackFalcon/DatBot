package protocol.network.messages.game.alliance;

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
public class AllianceCreationResultMessage extends NetworkMessage {
	public static final int ProtocolId = 6391;

	private int result;

	public int getResult() { return this.result; }
	public void setResult(int result) { this.result = result; };

	public AllianceCreationResultMessage(){
	}

	public AllianceCreationResultMessage(int result){
		this.result = result;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.result);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.result = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
