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
public class ContactLookRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5932;

	private int requestId;
	private int contactType;

	public int getRequestId() { return this.requestId; }
	public void setRequestId(int requestId) { this.requestId = requestId; };
	public int getContactType() { return this.contactType; }
	public void setContactType(int contactType) { this.contactType = contactType; };

	public ContactLookRequestMessage(){
	}

	public ContactLookRequestMessage(int requestId, int contactType){
		this.requestId = requestId;
		this.contactType = contactType;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.requestId);
			writer.writeByte(this.contactType);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.requestId = reader.readByte();
			this.contactType = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
