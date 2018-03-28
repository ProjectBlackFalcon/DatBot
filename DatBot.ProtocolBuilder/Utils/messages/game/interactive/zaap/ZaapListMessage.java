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
import protocol.network.messages.game.interactive.zaap.TeleportDestinationsListMessage;

@SuppressWarnings("unused")
public class ZaapListMessage extends TeleportDestinationsListMessage {
	public static final int ProtocolId = 1604;

	private double spawnMapId;

	public double getSpawnMapId() { return this.spawnMapId; }
	public void setSpawnMapId(double spawnMapId) { this.spawnMapId = spawnMapId; };

	public ZaapListMessage(){
	}

	public ZaapListMessage(double spawnMapId){
		this.spawnMapId = spawnMapId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeDouble(this.spawnMapId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.spawnMapId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
