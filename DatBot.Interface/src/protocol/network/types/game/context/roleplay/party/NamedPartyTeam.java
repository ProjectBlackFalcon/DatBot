package protocol.network.types.game.context.roleplay.party;

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
public class NamedPartyTeam extends NetworkMessage {
	public static final int ProtocolId = 469;

	public int teamId;
	public String partyName;

	public NamedPartyTeam(){
	}

	public NamedPartyTeam(int teamId, String partyName){
		this.teamId = teamId;
		this.partyName = partyName;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.teamId);
			writer.writeUTF(this.partyName);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.teamId = reader.readByte();
			this.partyName = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("teamId : " + this.teamId);
		//Network.appendDebug("partyName : " + this.partyName);
	//}
}
