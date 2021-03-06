package protocol.network.messages.game.context.roleplay;

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
public class CurrentMapMessage extends NetworkMessage {
	public static final int ProtocolId = 220;

	private double mapId;
	private String mapKey;

	public double getMapId() { return this.mapId; }
	public void setMapId(double mapId) { this.mapId = mapId; };
	public String getMapKey() { return this.mapKey; }
	public void setMapKey(String mapKey) { this.mapKey = mapKey; };

	public CurrentMapMessage(){
	}

	public CurrentMapMessage(double mapId, String mapKey){
		this.mapId = mapId;
		this.mapKey = mapKey;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.mapId);
			writer.writeUTF(this.mapKey);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.mapId = reader.readDouble();
			this.mapKey = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
