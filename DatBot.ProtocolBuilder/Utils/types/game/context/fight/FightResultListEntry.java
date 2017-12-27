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
import protocol.network.NetworkMessage;
import protocol.network.types.game.context.fight.FightLoot;

@SuppressWarnings("unused")
public class FightResultListEntry extends NetworkMessage {
	public static final int ProtocolId = 16;

	private int outcome;
	private int wave;
	private FightLoot rewards;

	public int getOutcome() { return this.outcome; };
	public void setOutcome(int outcome) { this.outcome = outcome; };
	public int getWave() { return this.wave; };
	public void setWave(int wave) { this.wave = wave; };
	public FightLoot getRewards() { return this.rewards; };
	public void setRewards(FightLoot rewards) { this.rewards = rewards; };

	public FightResultListEntry(){
	}

	public FightResultListEntry(int outcome, int wave, FightLoot rewards){
		this.outcome = outcome;
		this.wave = wave;
		this.rewards = rewards;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.outcome);
			writer.writeByte(this.wave);
			rewards.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.outcome = reader.readVarShort();
			this.wave = reader.readByte();
			this.rewards = new FightLoot();
			this.rewards.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
