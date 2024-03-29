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
import protocol.network.messages.game.context.roleplay.MapComplementaryInformationsDataMessage;

@SuppressWarnings("unused")
public class MapComplementaryInformationsWithCoordsMessage extends MapComplementaryInformationsDataMessage {
	public static final int ProtocolId = 6268;

	private int worldX;
	private int worldY;

	public int getWorldX() { return this.worldX; }
	public void setWorldX(int worldX) { this.worldX = worldX; };
	public int getWorldY() { return this.worldY; }
	public void setWorldY(int worldY) { this.worldY = worldY; };

	public MapComplementaryInformationsWithCoordsMessage(){
	}

	public MapComplementaryInformationsWithCoordsMessage(int worldX, int worldY){
		this.worldX = worldX;
		this.worldY = worldY;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.worldX);
			writer.writeShort(this.worldY);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.worldX = reader.readShort();
			this.worldY = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
