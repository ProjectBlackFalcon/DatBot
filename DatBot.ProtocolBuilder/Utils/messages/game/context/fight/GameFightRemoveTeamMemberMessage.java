package protocol.network.messages.game.context.fight;

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
public class GameFightRemoveTeamMemberMessage extends NetworkMessage {
	public static final int ProtocolId = 711;

	private int fightId;
	private int teamId;
	private double charId;

	public int getFightId() { return this.fightId; }
	public void setFightId(int fightId) { this.fightId = fightId; };
	public int getTeamId() { return this.teamId; }
	public void setTeamId(int teamId) { this.teamId = teamId; };
	public double getCharId() { return this.charId; }
	public void setCharId(double charId) { this.charId = charId; };

	public GameFightRemoveTeamMemberMessage(){
	}

	public GameFightRemoveTeamMemberMessage(int fightId, int teamId, double charId){
		this.fightId = fightId;
		this.teamId = teamId;
		this.charId = charId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.fightId);
			writer.writeByte(this.teamId);
			writer.writeDouble(this.charId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.fightId = reader.readVarShort();
			this.teamId = reader.readByte();
			this.charId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
