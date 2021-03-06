package protocol.network.types.game.context.fight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.fight.FightResultListEntry;

@SuppressWarnings("unused")
public class FightResultFighterListEntry extends FightResultListEntry {
	public static final int ProtocolId = 189;

	private double id;
	private boolean alive;

	public double getId() { return this.id; }
	public void setId(double id) { this.id = id; };
	public boolean isAlive() { return this.alive; }
	public void setAlive(boolean alive) { this.alive = alive; };

	public FightResultFighterListEntry(){
	}

	public FightResultFighterListEntry(double id, boolean alive){
		this.id = id;
		this.alive = alive;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeDouble(this.id);
			writer.writeBoolean(this.alive);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.id = reader.readDouble();
			this.alive = reader.readBoolean();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
