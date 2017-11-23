package protocol.network.types.game.prism;

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
public class PrismInformation extends NetworkMessage {
	public static final int ProtocolId = 428;

	public int typeId;
	public int state;
	public int nextVulnerabilityDate;
	public int placementDate;
	public int rewardTokenCount;

	public PrismInformation(){
	}

	public PrismInformation(int typeId, int state, int nextVulnerabilityDate, int placementDate, int rewardTokenCount){
		this.typeId = typeId;
		this.state = state;
		this.nextVulnerabilityDate = nextVulnerabilityDate;
		this.placementDate = placementDate;
		this.rewardTokenCount = rewardTokenCount;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.typeId);
			writer.writeByte(this.state);
			writer.writeInt(this.nextVulnerabilityDate);
			writer.writeInt(this.placementDate);
			writer.writeVarInt(this.rewardTokenCount);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.typeId = reader.readByte();
			this.state = reader.readByte();
			this.nextVulnerabilityDate = reader.readInt();
			this.placementDate = reader.readInt();
			this.rewardTokenCount = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("typeId : " + this.typeId);
		//Network.appendDebug("state : " + this.state);
		//Network.appendDebug("nextVulnerabilityDate : " + this.nextVulnerabilityDate);
		//Network.appendDebug("placementDate : " + this.placementDate);
		//Network.appendDebug("rewardTokenCount : " + this.rewardTokenCount);
	//}
}
