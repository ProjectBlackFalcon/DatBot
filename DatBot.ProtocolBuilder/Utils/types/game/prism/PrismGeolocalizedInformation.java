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
import protocol.network.types.game.prism.PrismSubareaEmptyInfo;
import protocol.network.types.game.prism.PrismInformation;

@SuppressWarnings("unused")
public class PrismGeolocalizedInformation extends PrismSubareaEmptyInfo {
	public static final int ProtocolId = 434;

	private int worldX;
	private int worldY;
	private double mapId;
	private PrismInformation prism;

	public int getWorldX() { return this.worldX; };
	public void setWorldX(int worldX) { this.worldX = worldX; };
	public int getWorldY() { return this.worldY; };
	public void setWorldY(int worldY) { this.worldY = worldY; };
	public double getMapId() { return this.mapId; };
	public void setMapId(double mapId) { this.mapId = mapId; };
	public PrismInformation getPrism() { return this.prism; };
	public void setPrism(PrismInformation prism) { this.prism = prism; };

	public PrismGeolocalizedInformation(){
	}

	public PrismGeolocalizedInformation(int worldX, int worldY, double mapId, PrismInformation prism){
		this.worldX = worldX;
		this.worldY = worldY;
		this.mapId = mapId;
		this.prism = prism;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.worldX);
			writer.writeShort(this.worldY);
			writer.writeDouble(this.mapId);
			writer.writeShort(PrismInformation.ProtocolId);
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
			this.mapId = reader.readDouble();
			this.prism = (PrismInformation) ProtocolTypeManager.getInstance(reader.readShort());
			this.prism.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
