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

	private int allianceId;
	private int nbGuilds;
	private int nbMembers;
	private int nbSubarea;

	public int getAllianceId() { return this.allianceId; };
	public void setAllianceId(int allianceId) { this.allianceId = allianceId; };
	public int getNbGuilds() { return this.nbGuilds; };
	public void setNbGuilds(int nbGuilds) { this.nbGuilds = nbGuilds; };
	public int getNbMembers() { return this.nbMembers; };
	public void setNbMembers(int nbMembers) { this.nbMembers = nbMembers; };
	public int getNbSubarea() { return this.nbSubarea; };
	public void setNbSubarea(int nbSubarea) { this.nbSubarea = nbSubarea; };

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
	}

}
