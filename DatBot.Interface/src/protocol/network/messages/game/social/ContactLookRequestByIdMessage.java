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
import protocol.network.messages.game.social.ContactLookRequestMessage;

@SuppressWarnings("unused")
public class ContactLookRequestByIdMessage extends ContactLookRequestMessage {
	public static final int ProtocolId = 5935;

	public long playerId;

	public ContactLookRequestByIdMessage(){
	}

	public ContactLookRequestByIdMessage(long playerId){
		this.playerId = playerId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.playerId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.playerId = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("playerId : " + this.playerId);
	//}
}