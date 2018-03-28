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
import protocol.network.types.game.social.GuildFactSheetInformations;

@SuppressWarnings("unused")
public class GuildInsiderFactSheetInformations extends GuildFactSheetInformations {
	public static final int ProtocolId = 423;

	private String leaderName;
	private int nbConnectedMembers;
	private int nbTaxCollectors;
	private int lastActivity;

	public String getLeaderName() { return this.leaderName; }
	public void setLeaderName(String leaderName) { this.leaderName = leaderName; };
	public int getNbConnectedMembers() { return this.nbConnectedMembers; }
	public void setNbConnectedMembers(int nbConnectedMembers) { this.nbConnectedMembers = nbConnectedMembers; };
	public int getNbTaxCollectors() { return this.nbTaxCollectors; }
	public void setNbTaxCollectors(int nbTaxCollectors) { this.nbTaxCollectors = nbTaxCollectors; };
	public int getLastActivity() { return this.lastActivity; }
	public void setLastActivity(int lastActivity) { this.lastActivity = lastActivity; };

	public GuildInsiderFactSheetInformations(){
	}

	public GuildInsiderFactSheetInformations(String leaderName, int nbConnectedMembers, int nbTaxCollectors, int lastActivity){
		this.leaderName = leaderName;
		this.nbConnectedMembers = nbConnectedMembers;
		this.nbTaxCollectors = nbTaxCollectors;
		this.lastActivity = lastActivity;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeUTF(this.leaderName);
			writer.writeVarShort(this.nbConnectedMembers);
			writer.writeByte(this.nbTaxCollectors);
			writer.writeInt(this.lastActivity);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.leaderName = reader.readUTF();
			this.nbConnectedMembers = reader.readVarShort();
			this.nbTaxCollectors = reader.readByte();
			this.lastActivity = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
