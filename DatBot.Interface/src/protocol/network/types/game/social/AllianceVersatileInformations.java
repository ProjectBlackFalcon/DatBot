package protocol.network.types.game.social;

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
public class AllianceVersatileInformations extends NetworkMessage {
	public static final int ProtocolId = 432;

	public int allianceId;
	public int nbGuilds;
	public int nbMembers;
	public int nbSubarea;

	public AllianceVersatileInformations(){
	}

	public AllianceVersatileInformations(int allianceId, int nbGuilds, int nbMembers, int nbSubarea){
		this.allianceId = allianceId;
		this.nbGuilds = nbGuilds;
		this.nbMembers = nbMembers;
		this.nbSubarea = nbSubarea;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.allianceId);
			writer.writeVarShort(this.nbGuilds);
			writer.writeVarShort(this.nbMembers);
			writer.writeVarShort(this.nbSubarea);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.allianceId = reader.readVarInt();
			this.nbGuilds = reader.readVarShort();
			this.nbMembers = reader.readVarShort();
			this.nbSubarea = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("allianceId : " + this.allianceId);
		//Network.appendDebug("nbGuilds : " + this.nbGuilds);
		//Network.appendDebug("nbMembers : " + this.nbMembers);
		//Network.appendDebug("nbSubarea : " + this.nbSubarea);
	//}
}