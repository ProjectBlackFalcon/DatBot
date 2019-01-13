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
public class BreachRewardBoughtMessage extends NetworkMessage {
	public static final int ProtocolId = 6797;

	private int id;
	private boolean bought;

	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; };
	public boolean isBought() { return this.bought; }
	public void setBought(boolean bought) { this.bought = bought; };

	public BreachRewardBoughtMessage(){
	}

	public BreachRewardBoughtMessage(int id, boolean bought){
		this.id = id;
		this.bought = bought;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.id);
			writer.writeBoolean(this.bought);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.id = reader.readVarInt();
			this.bought = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
