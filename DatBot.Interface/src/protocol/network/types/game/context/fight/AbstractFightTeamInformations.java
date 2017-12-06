package protocol.network.types.game.context.fight;

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
public class AbstractFightTeamInformations extends NetworkMessage {
	public static final int ProtocolId = 116;

	public int teamId;
	public double leaderId;
	public int teamSide;
	public int teamTypeId;
	public int nbWaves;

	public AbstractFightTeamInformations(){
	}

	public AbstractFightTeamInformations(int teamId, double leaderId, int teamSide, int teamTypeId, int nbWaves){
		this.teamId = teamId;
		this.leaderId = leaderId;
		this.teamSide = teamSide;
		this.teamTypeId = teamTypeId;
		this.nbWaves = nbWaves;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.teamId);
			writer.writeDouble(this.leaderId);
			writer.writeByte(this.teamSide);
			writer.writeByte(this.teamTypeId);
			writer.writeByte(this.nbWaves);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.teamId = reader.readByte();
			this.leaderId = reader.readDouble();
			this.teamSide = reader.readByte();
			this.teamTypeId = reader.readByte();
			this.nbWaves = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("teamId : " + this.teamId);
		//Network.appendDebug("leaderId : " + this.leaderId);
		//Network.appendDebug("teamSide : " + this.teamSide);
		//Network.appendDebug("teamTypeId : " + this.teamTypeId);
		//Network.appendDebug("nbWaves : " + this.nbWaves);
	//}
}
