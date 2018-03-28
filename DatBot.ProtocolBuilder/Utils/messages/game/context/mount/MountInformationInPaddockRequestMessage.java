package protocol.network.messages.game.context.mount;

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
public class MountInformationInPaddockRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5975;

	private int mapRideId;

	public int getMapRideId() { return this.mapRideId; }
	public void setMapRideId(int mapRideId) { this.mapRideId = mapRideId; };

	public MountInformationInPaddockRequestMessage(){
	}

	public MountInformationInPaddockRequestMessage(int mapRideId){
		this.mapRideId = mapRideId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.mapRideId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.mapRideId = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
