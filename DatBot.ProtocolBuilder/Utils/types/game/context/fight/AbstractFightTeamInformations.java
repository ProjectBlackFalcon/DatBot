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

	private int teamId;
	private double leaderId;
	private int teamSide;
	private int teamTypeId;
	private int nbWaves;

	public int getTeamId() { return this.teamId; }
	public void setTeamId(int teamId) { this.teamId = teamId; };
	public double getLeaderId() { return this.leaderId; }
	public void setLeaderId(double leaderId) { this.leaderId = leaderId; };
	public int getTeamSide() { return this.teamSide; }
	public void setTeamSide(int teamSide) { this.teamSide = teamSide; };
	public int getTeamTypeId() { return this.teamTypeId; }
	public void setTeamTypeId(int teamTypeId) { this.teamTypeId = teamTypeId; };
	public int getNbWaves() { return this.nbWaves; }
	public void setNbWaves(int nbWaves) { this.nbWaves = nbWaves; };

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

}
