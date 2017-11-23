package protocol.network.messages.game.context.roleplay.death;

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
public class GameRolePlayPlayerLifeStatusMessage extends NetworkMessage {
	public static final int ProtocolId = 5996;

	public int state;
	public double phenixMapId;

	public GameRolePlayPlayerLifeStatusMessage(){
	}

	public GameRolePlayPlayerLifeStatusMessage(int state, double phenixMapId){
		this.state = state;
		this.phenixMapId = phenixMapId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.state);
			writer.writeDouble(this.phenixMapId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.state = reader.readByte();
			this.phenixMapId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("state : " + this.state);
		//Network.appendDebug("phenixMapId : " + this.phenixMapId);
	//}
}
