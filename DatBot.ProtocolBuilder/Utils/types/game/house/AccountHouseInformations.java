package protocol.network.types.game.house;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.house.HouseInformations;
import protocol.network.types.game.house.HouseInstanceInformations;

@SuppressWarnings("unused")
public class AccountHouseInformations extends HouseInformations {
	public static final int ProtocolId = 390;

	private HouseInstanceInformations houseInfos;
	private int worldX;
	private int worldY;
	private double mapId;
	private int subAreaId;

	public HouseInstanceInformations getHouseInfos() { return this.houseInfos; };
	public void setHouseInfos(HouseInstanceInformations houseInfos) { this.houseInfos = houseInfos; };
	public int getWorldX() { return this.worldX; };
	public void setWorldX(int worldX) { this.worldX = worldX; };
	public int getWorldY() { return this.worldY; };
	public void setWorldY(int worldY) { this.worldY = worldY; };
	public double getMapId() { return this.mapId; };
	public void setMapId(double mapId) { this.mapId = mapId; };
	public int getSubAreaId() { return this.subAreaId; };
	public void setSubAreaId(int subAreaId) { this.subAreaId = subAreaId; };

	public AccountHouseInformations(){
	}

	public AccountHouseInformations(HouseInstanceInformations houseInfos, int worldX, int worldY, double mapId, int subAreaId){
		this.houseInfos = houseInfos;
		this.worldX = worldX;
		this.worldY = worldY;
		this.mapId = mapId;
		this.subAreaId = subAreaId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(HouseInstanceInformations.ProtocolId);
			writer.writeShort(this.worldX);
			writer.writeShort(this.worldY);
			writer.writeDouble(this.mapId);
			writer.writeVarShort(this.subAreaId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.houseInfos = (HouseInstanceInformations) ProtocolTypeManager.getInstance(reader.readShort());
			this.houseInfos.Deserialize(reader);
			this.worldX = reader.readShort();
			this.worldY = reader.readShort();
			this.mapId = reader.readDouble();
			this.subAreaId = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
