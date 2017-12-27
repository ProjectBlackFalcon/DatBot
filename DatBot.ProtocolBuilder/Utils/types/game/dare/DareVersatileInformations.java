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

	private double dareId;
	private int countEntrants;
	private int countWinners;

	public double getDareId() { return this.dareId; };
	public void setDareId(double dareId) { this.dareId = dareId; };
	public int getCountEntrants() { return this.countEntrants; };
	public void setCountEntrants(int countEntrants) { this.countEntrants = countEntrants; };
	public int getCountWinners() { return this.countWinners; };
	public void setCountWinners(int countWinners) { this.countWinners = countWinners; };

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
	}

}
