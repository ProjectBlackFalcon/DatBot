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
import protocol.network.types.game.actions.fight.AbstractFightDispellableEffect;

@SuppressWarnings("unused")
public class FightTemporarySpellImmunityEffect extends AbstractFightDispellableEffect {
	public static final int ProtocolId = 366;

	private int immuneSpellId;

	public int getImmuneSpellId() { return this.immuneSpellId; }
	public void setImmuneSpellId(int immuneSpellId) { this.immuneSpellId = immuneSpellId; };

	public FightTemporarySpellImmunityEffect(){
	}

	public FightTemporarySpellImmunityEffect(int immuneSpellId){
		this.immuneSpellId = immuneSpellId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeInt(this.immuneSpellId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.immuneSpellId = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
