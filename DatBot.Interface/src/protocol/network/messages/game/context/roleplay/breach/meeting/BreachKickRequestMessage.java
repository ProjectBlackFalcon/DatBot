package protocol.network.messages.game.context.roleplay.breach.meeting;

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
public class BreachKickRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6804;

	private long target;

	public long getTarget() { return this.target; }
	public void setTarget(long target) { this.target = target; };

	public BreachKickRequestMessage(){
	}

	public BreachKickRequestMessage(long target){
		this.target = target;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.target);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.target = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
