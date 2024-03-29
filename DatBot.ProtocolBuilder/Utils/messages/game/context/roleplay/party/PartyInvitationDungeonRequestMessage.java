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
import protocol.network.messages.game.context.roleplay.party.PartyInvitationRequestMessage;

@SuppressWarnings("unused")
public class PartyInvitationDungeonRequestMessage extends PartyInvitationRequestMessage {
	public static final int ProtocolId = 6245;

	private int dungeonId;

	public int getDungeonId() { return this.dungeonId; }
	public void setDungeonId(int dungeonId) { this.dungeonId = dungeonId; };

	public PartyInvitationDungeonRequestMessage(){
	}

	public PartyInvitationDungeonRequestMessage(int dungeonId){
		this.dungeonId = dungeonId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.dungeonId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.dungeonId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
