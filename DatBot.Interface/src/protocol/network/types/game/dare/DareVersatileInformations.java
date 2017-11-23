package protocol.network.types.game.dare;

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
public class DareVersatileInformations extends NetworkMessage {
	public static final int ProtocolId = 504;

	public double dareId;
	public int countEntrants;
	public int countWinners;

	public DareVersatileInformations(){
	}

	public DareVersatileInformations(double dareId, int countEntrants, int countWinners){
		this.dareId = dareId;
		this.countEntrants = countEntrants;
		this.countWinners = countWinners;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.dareId);
			writer.writeInt(this.countEntrants);
			writer.writeInt(this.countWinners);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.dareId = reader.readDouble();
			this.countEntrants = reader.readInt();
			this.countWinners = reader.readInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("dareId : " + this.dareId);
		//Network.appendDebug("countEntrants : " + this.countEntrants);
		//Network.appendDebug("countWinners : " + this.countWinners);
	//}
}
