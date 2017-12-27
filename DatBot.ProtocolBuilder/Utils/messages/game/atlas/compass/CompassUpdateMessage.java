package protocol.network.messages.game.atlas.compass;

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
import protocol.network.types.game.context.MapCoordinates;

@SuppressWarnings("unused")
public class CompassUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 5591;

	private int type;
	private MapCoordinates coords;

	public int getType() { return this.type; };
	public void setType(int type) { this.type = type; };
	public MapCoordinates getCoords() { return this.coords; };
	public void setCoords(MapCoordinates coords) { this.coords = coords; };

	public CompassUpdateMessage(){
	}

	public CompassUpdateMessage(int type, MapCoordinates coords){
		this.type = type;
		this.coords = coords;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeByte(this.type);
			writer.writeShort(MapCoordinates.ProtocolId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.type = reader.readByte();
			this.coords = (MapCoordinates) ProtocolTypeManager.getInstance(reader.readShort());
			this.coords.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
