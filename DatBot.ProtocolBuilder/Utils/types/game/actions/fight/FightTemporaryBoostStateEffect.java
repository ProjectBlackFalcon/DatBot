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
public class FightTemporaryBoostStateEffect extends FightTemporaryBoostEffect {
	public static final int ProtocolId = 214;

	private int stateId;

	public int getStateId() { return this.stateId; };
	public void setStateId(int stateId) { this.stateId = stateId; };

	public FightTemporaryBoostStateEffect(){
	}

	public FightTemporaryBoostStateEffect(int stateId){
		this.stateId = stateId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.stateId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.stateId = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
