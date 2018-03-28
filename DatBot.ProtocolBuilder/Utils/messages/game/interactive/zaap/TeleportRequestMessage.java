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
public class TeleportRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 5961;

	private int teleporterType;
	private double mapId;

	public int getTeleporterType() { return this.teleporterType; }
	public void setTeleporterType(int teleporterType) { this.teleporterType = teleporterType; };
	public double getMapId() { return this.mapId; }
	public void setMapId(double mapId) { this.mapId = mapId; };

	public TeleportRequestMessage(){
	}

	public TeleportRequestMessage(int teleporterType, double mapId){
		this.teleporterType = teleporterType;
		this.mapId = mapId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.teleporterType);
			writer.writeDouble(this.mapId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.teleporterType = reader.readByte();
			this.mapId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
