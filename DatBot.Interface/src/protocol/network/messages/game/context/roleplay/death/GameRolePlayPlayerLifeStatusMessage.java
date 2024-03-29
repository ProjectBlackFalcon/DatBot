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

	private int state;
	private double phenixMapId;

	public int getState() { return this.state; }
	public void setState(int state) { this.state = state; };
	public double getPhenixMapId() { return this.phenixMapId; }
	public void setPhenixMapId(double phenixMapId) { this.phenixMapId = phenixMapId; };

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
	}

}
