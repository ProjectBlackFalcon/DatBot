package protocol.network.messages.game.context.fight.breach;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.fight.GameFightEndMessage;

@SuppressWarnings("unused")
public class BreachGameFightEndMessage extends GameFightEndMessage {
	public static final int ProtocolId = 6809;

	private int budget;

	public int getBudget() { return this.budget; }
	public void setBudget(int budget) { this.budget = budget; };

	public BreachGameFightEndMessage(){
	}

	public BreachGameFightEndMessage(int budget){
		this.budget = budget;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeInt(this.budget);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.budget = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
