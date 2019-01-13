package protocol.network.messages.game.context.roleplay.breach.reward;

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
public class BreachSaveBoughtMessage extends NetworkMessage {
	public static final int ProtocolId = 6788;

	private boolean bought;

	public boolean isBought() { return this.bought; }
	public void setBought(boolean bought) { this.bought = bought; };

	public BreachSaveBoughtMessage(){
	}

	public BreachSaveBoughtMessage(boolean bought){
		this.bought = bought;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeBoolean(this.bought);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.bought = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
