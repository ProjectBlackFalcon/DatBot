package protocol.network.messages.game.interactive.zaap;

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
public class ZaapRespawnUpdatedMessage extends NetworkMessage {
	public static final int ProtocolId = 6571;

	private double mapId;

	public double getMapId() { return this.mapId; }
	public void setMapId(double mapId) { this.mapId = mapId; };

	public ZaapRespawnUpdatedMessage(){
	}

	public ZaapRespawnUpdatedMessage(double mapId){
		this.mapId = mapId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.mapId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.mapId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
