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

	private int fightId;
	private int teamId;
	private int option;
	private boolean state;

	public int getFightId() { return this.fightId; }
	public void setFightId(int fightId) { this.fightId = fightId; };
	public int getTeamId() { return this.teamId; }
	public void setTeamId(int teamId) { this.teamId = teamId; };
	public int getOption() { return this.option; }
	public void setOption(int option) { this.option = option; };
	public boolean isState() { return this.state; }
	public void setState(boolean state) { this.state = state; };

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
			writer.writeVarShort(this.fightId);
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
			this.fightId = reader.readVarShort();
			this.teamId = reader.readByte();
			this.option = reader.readByte();
			this.state = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
