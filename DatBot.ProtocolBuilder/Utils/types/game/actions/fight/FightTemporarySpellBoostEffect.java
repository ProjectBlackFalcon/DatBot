package protocol.network.types.game.actions.fight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.actions.fight.FightTemporaryBoostEffect;

@SuppressWarnings("unused")
public class FightTemporarySpellBoostEffect extends FightTemporaryBoostEffect {
	public static final int ProtocolId = 207;

	private int boostedSpellId;

	public int getBoostedSpellId() { return this.boostedSpellId; };
	public void setBoostedSpellId(int boostedSpellId) { this.boostedSpellId = boostedSpellId; };

	public FightTemporarySpellBoostEffect(){
	}

	public FightTemporarySpellBoostEffect(int boostedSpellId){
		this.boostedSpellId = boostedSpellId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.boostedSpellId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.boostedSpellId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
