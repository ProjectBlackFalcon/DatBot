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
public class GameFightLeaveMessage extends NetworkMessage {
	public static final int ProtocolId = 721;

	private double charId;

	public double getCharId() { return this.charId; };
	public void setCharId(double charId) { this.charId = charId; };

	public GameFightLeaveMessage(){
	}

	public GameFightLeaveMessage(double charId){
		this.charId = charId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.charId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.charId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
