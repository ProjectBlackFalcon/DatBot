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
public class FightTriggeredEffect extends AbstractFightDispellableEffect {
	public static final int ProtocolId = 210;

	public int param1;
	public int param2;
	public int param3;
	public int delay;

	public FightTriggeredEffect(){
	}

	public FightTriggeredEffect(int param1, int param2, int param3, int delay){
		this.param1 = param1;
		this.param2 = param2;
		this.param3 = param3;
		this.delay = delay;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeInt(this.param1);
			writer.writeInt(this.param2);
			writer.writeInt(this.param3);
			writer.writeShort(this.delay);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.param1 = reader.readInt();
			this.param2 = reader.readInt();
			this.param3 = reader.readInt();
			this.delay = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("param1 : " + this.param1);
		//Network.appendDebug("param2 : " + this.param2);
		//Network.appendDebug("param3 : " + this.param3);
		//Network.appendDebug("delay : " + this.delay);
	//}
}