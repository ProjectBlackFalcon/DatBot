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
import protocol.network.types.game.context.roleplay.GuildInformations;

@SuppressWarnings("unused")
public class GuildFactSheetInformations extends GuildInformations {
	public static final int ProtocolId = 424;

	public long leaderId;
	public int nbMembers;

	public GuildFactSheetInformations(){
	}

	public GuildFactSheetInformations(long leaderId, int nbMembers){
		this.leaderId = leaderId;
		this.nbMembers = nbMembers;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.leaderId);
			writer.writeVarShort(this.nbMembers);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.leaderId = reader.readVarLong();
			this.nbMembers = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("leaderId : " + this.leaderId);
		//Network.appendDebug("nbMembers : " + this.nbMembers);
	//}
}