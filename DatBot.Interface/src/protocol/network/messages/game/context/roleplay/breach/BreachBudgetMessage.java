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
public class BreachBudgetMessage extends NetworkMessage {
	public static final int ProtocolId = 6786;

	private int bugdet;

	public int getBugdet() { return this.bugdet; }
	public void setBugdet(int bugdet) { this.bugdet = bugdet; };

	public BreachBudgetMessage(){
	}

	public BreachBudgetMessage(int bugdet){
		this.bugdet = bugdet;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.bugdet);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.bugdet = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
