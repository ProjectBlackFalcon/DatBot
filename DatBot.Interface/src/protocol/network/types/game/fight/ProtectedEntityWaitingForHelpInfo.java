package protocol.network.types.game.fight;

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
public class ProtectedEntityWaitingForHelpInfo extends NetworkMessage {
	public static final int ProtocolId = 186;

	private int timeLeftBeforeFight;
	private int waitTimeForPlacement;
	private int nbPositionForDefensors;

	public int getTimeLeftBeforeFight() { return this.timeLeftBeforeFight; }
	public void setTimeLeftBeforeFight(int timeLeftBeforeFight) { this.timeLeftBeforeFight = timeLeftBeforeFight; };
	public int getWaitTimeForPlacement() { return this.waitTimeForPlacement; }
	public void setWaitTimeForPlacement(int waitTimeForPlacement) { this.waitTimeForPlacement = waitTimeForPlacement; };
	public int getNbPositionForDefensors() { return this.nbPositionForDefensors; }
	public void setNbPositionForDefensors(int nbPositionForDefensors) { this.nbPositionForDefensors = nbPositionForDefensors; };

	public ProtectedEntityWaitingForHelpInfo(){
	}

	public ProtectedEntityWaitingForHelpInfo(int timeLeftBeforeFight, int waitTimeForPlacement, int nbPositionForDefensors){
		this.timeLeftBeforeFight = timeLeftBeforeFight;
		this.waitTimeForPlacement = waitTimeForPlacement;
		this.nbPositionForDefensors = nbPositionForDefensors;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.timeLeftBeforeFight);
			writer.writeInt(this.waitTimeForPlacement);
			writer.writeByte(this.nbPositionForDefensors);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.timeLeftBeforeFight = reader.readInt();
			this.waitTimeForPlacement = reader.readInt();
			this.nbPositionForDefensors = reader.readByte();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
