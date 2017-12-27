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

	private int id;
	private int xpBoost;
	private int dropBoost;

	public int getId() { return this.id; };
	public void setId(int id) { this.id = id; };
	public int getXpBoost() { return this.xpBoost; };
	public void setXpBoost(int xpBoost) { this.xpBoost = xpBoost; };
	public int getDropBoost() { return this.dropBoost; };
	public void setDropBoost(int dropBoost) { this.dropBoost = dropBoost; };

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

}
