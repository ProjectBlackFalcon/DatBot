package protocol.network.messages.game.prism;

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
import protocol.network.types.game.prism.PrismFightersInformation;

@SuppressWarnings("unused")
public class PrismFightAddedMessage extends NetworkMessage {
	public static final int ProtocolId = 6452;

	public PrismFightersInformation fight;

	public PrismFightAddedMessage(){
	}

	public PrismFightAddedMessage(PrismFightersInformation fight){
		this.fight = fight;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			fight.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.fight = new PrismFightersInformation();
			this.fight.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("fight : " + this.fight);
	//}
}
