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

	public int getNbMembers() { return this.nbMembers; };
	public void setNbMembers(int nbMembers) { this.nbMembers = nbMembers; };

	public GuildInAllianceInformations(){
	}

	public GuildInAllianceInformations(int nbMembers){
		this.nbMembers = nbMembers;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.nbMembers);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.nbMembers = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
