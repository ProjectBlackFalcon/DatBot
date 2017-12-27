package protocol.network.messages.game.context;

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
public class GameContextKickMessage extends NetworkMessage {
	public static final int ProtocolId = 6081;

	private double targetId;

	public double getTargetId() { return this.targetId; };
	public void setTargetId(double targetId) { this.targetId = targetId; };

	public GameContextKickMessage(){
	}

	public GameContextKickMessage(double targetId){
		this.targetId = targetId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.targetId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.targetId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
