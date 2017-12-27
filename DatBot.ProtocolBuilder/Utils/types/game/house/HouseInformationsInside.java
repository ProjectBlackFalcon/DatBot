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
public class HouseInformationsInside extends HouseInformations {
	public static final int ProtocolId = 218;

	private HouseInstanceInformations houseInfos;
	private int worldX;
	private int worldY;

	public HouseInstanceInformations getHouseInfos() { return this.houseInfos; };
	public void setHouseInfos(HouseInstanceInformations houseInfos) { this.houseInfos = houseInfos; };
	public int getWorldX() { return this.worldX; };
	public void setWorldX(int worldX) { this.worldX = worldX; };
	public int getWorldY() { return this.worldY; };
	public void setWorldY(int worldY) { this.worldY = worldY; };

	public HouseInformationsInside(){
	}

	public HouseInformationsInside(HouseInstanceInformations houseInfos, int worldX, int worldY){
		this.houseInfos = houseInfos;
		this.worldX = worldX;
		this.worldY = worldY;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(HouseInstanceInformations.ProtocolId);
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
			this.houseInfos = (HouseInstanceInformations) ProtocolTypeManager.getInstance(reader.readShort());
			this.houseInfos.Deserialize(reader);
			this.worldX = reader.readShort();
			this.worldY = reader.readShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
