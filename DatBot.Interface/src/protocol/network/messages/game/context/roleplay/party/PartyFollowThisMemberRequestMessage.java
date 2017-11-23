package protocol.network.messages.game.context.roleplay.party;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.roleplay.party.PartyFollowMemberRequestMessage;

@SuppressWarnings("unused")
public class PartyFollowThisMemberRequestMessage extends PartyFollowMemberRequestMessage {
	public static final int ProtocolId = 5588;

	public boolean enabled;

	public PartyFollowThisMemberRequestMessage(){
	}

	public PartyFollowThisMemberRequestMessage(boolean enabled){
		this.enabled = enabled;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeBoolean(this.enabled);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.enabled = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("enabled : " + this.enabled);
	//}
}
