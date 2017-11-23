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

	public int fightId;
	public int teamId;
	public double charId;

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
			writer.writeShort(this.fightId);
			writer.writeByte(this.teamId);
			writer.writeDouble(this.charId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.fightId = reader.readShort();
			this.teamId = reader.readByte();
			this.charId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("fightId : " + this.fightId);
		//Network.appendDebug("teamId : " + this.teamId);
		//Network.appendDebug("charId : " + this.charId);
	//}
}
