package protocol.network.types.game.context.roleplay;

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
public class GuildInAllianceInformations extends GuildInformations {
	public static final int ProtocolId = 420;

	private int nbMembers;
	private int joinDate;

	public int getNbMembers() { return this.nbMembers; }
	public void setNbMembers(int nbMembers) { this.nbMembers = nbMembers; };
	public int getJoinDate() { return this.joinDate; }
	public void setJoinDate(int joinDate) { this.joinDate = joinDate; };

	public GuildInAllianceInformations(){
	}

	public GuildInAllianceInformations(int nbMembers, int joinDate){
		this.nbMembers = nbMembers;
		this.joinDate = joinDate;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.nbMembers);
			writer.writeInt(this.joinDate);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.nbMembers = reader.readByte();
			this.joinDate = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
