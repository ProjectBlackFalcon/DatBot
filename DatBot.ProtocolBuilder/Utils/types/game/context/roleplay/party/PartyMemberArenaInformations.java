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
import protocol.network.types.game.context.roleplay.party.PartyMemberInformations;

@SuppressWarnings("unused")
public class PartyMemberArenaInformations extends PartyMemberInformations {
	public static final int ProtocolId = 391;

	private int rank;

	public int getRank() { return this.rank; };
	public void setRank(int rank) { this.rank = rank; };

	public PartyMemberArenaInformations(){
	}

	public PartyMemberArenaInformations(int rank){
		this.rank = rank;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.rank);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.rank = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
