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
import protocol.network.NetworkMessage;

@SuppressWarnings("unused")
public class PartyInvitationRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5585;

	private String name;

	public String getName() { return this.name; }
	public void setName(String name) { this.name = name; };

	public PartyInvitationRequestMessage(){
	}

	public PartyInvitationRequestMessage(String name){
		this.name = name;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.name);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.name = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
