package protocol.network.types.game.context.roleplay;

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
public class MonsterBoosts extends NetworkMessage {
	public static final int ProtocolId = 497;

	public int id;
	public int xpBoost;
	public int dropBoost;

	public MonsterBoosts(){
	}

	public MonsterBoosts(int id, int xpBoost, int dropBoost){
		this.id = id;
		this.xpBoost = xpBoost;
		this.dropBoost = dropBoost;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.id);
			writer.writeVarShort(this.xpBoost);
			writer.writeVarShort(this.dropBoost);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.id = reader.readVarInt();
			this.xpBoost = reader.readVarShort();
			this.dropBoost = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("id : " + this.id);
		//Network.appendDebug("xpBoost : " + this.xpBoost);
		//Network.appendDebug("dropBoost : " + this.dropBoost);
	//}
}
