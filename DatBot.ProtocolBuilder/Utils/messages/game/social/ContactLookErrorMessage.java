package protocol.network.messages.game.social;

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
public class ContactLookErrorMessage extends NetworkMessage {
	public static final int ProtocolId = 6045;

	private int requestId;

	public int getRequestId() { return this.requestId; }
	public void setRequestId(int requestId) { this.requestId = requestId; };

	public ContactLookErrorMessage(){
	}

	public ContactLookErrorMessage(int requestId){
		this.requestId = requestId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.requestId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.requestId = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
