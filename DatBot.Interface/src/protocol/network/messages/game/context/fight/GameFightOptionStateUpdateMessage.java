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
public class GameFightOptionStateUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 5927;

	public int fightId;
	public int teamId;
	public int option;
	public boolean state;

	public GameFightOptionStateUpdateMessage(){
	}

	public GameFightOptionStateUpdateMessage(int fightId, int teamId, int option, boolean state){
		this.fightId = fightId;
		this.teamId = teamId;
		this.option = option;
		this.state = state;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.fightId);
			writer.writeByte(this.teamId);
			writer.writeByte(this.option);
			writer.writeBoolean(this.state);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.fightId = reader.readShort();
			this.teamId = reader.readByte();
			this.option = reader.readByte();
			this.state = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("fightId : " + this.fightId);
		//Network.appendDebug("teamId : " + this.teamId);
		//Network.appendDebug("option : " + this.option);
		//Network.appendDebug("state : " + this.state);
	//}
}
