package protocol.network.messages.game.context.roleplay.breach;

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
public class BreachEnterMessage extends NetworkMessage {
	public static final int ProtocolId = 6810;

	private long owner;

	public long getOwner() { return this.owner; }
	public void setOwner(long owner) { this.owner = owner; };

	public BreachEnterMessage(){
	}

	public BreachEnterMessage(long owner){
		this.owner = owner;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarLong(this.owner);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.owner = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
