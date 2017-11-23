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
public class FightTemporaryBoostEffect extends AbstractFightDispellableEffect {
	public static final int ProtocolId = 209;

	public int delta;

	public FightTemporaryBoostEffect(){
	}

	public FightTemporaryBoostEffect(int delta){
		this.delta = delta;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.delta);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.delta = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("delta : " + this.delta);
	//}
}
