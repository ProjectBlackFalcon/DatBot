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

	public String leaderName;
	public int nbConnectedMembers;
	public int nbTaxCollectors;
	public int lastActivity;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("leaderName : " + this.leaderName);
		//Network.appendDebug("nbConnectedMembers : " + this.nbConnectedMembers);
		//Network.appendDebug("nbTaxCollectors : " + this.nbTaxCollectors);
		//Network.appendDebug("lastActivity : " + this.lastActivity);
	//}
}