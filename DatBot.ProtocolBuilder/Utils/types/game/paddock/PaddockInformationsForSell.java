package protocol.network.types.game.paddock;

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
public class PaddockInformationsForSell extends NetworkMessage {
	public static final int ProtocolId = 222;

	private String guildOwner;
	private int worldX;
	private int worldY;
	private int subAreaId;
	private int nbMount;
	private int nbObject;
	private long price;

	public String getGuildOwner() { return this.guildOwner; };
	public void setGuildOwner(String guildOwner) { this.guildOwner = guildOwner; };
	public int getWorldX() { return this.worldX; };
	public void setWorldX(int worldX) { this.worldX = worldX; };
	public int getWorldY() { return this.worldY; };
	public void setWorldY(int worldY) { this.worldY = worldY; };
	public int getSubAreaId() { return this.subAreaId; };
	public void setSubAreaId(int subAreaId) { this.subAreaId = subAreaId; };
	public int getNbMount() { return this.nbMount; };
	public void setNbMount(int nbMount) { this.nbMount = nbMount; };
	public int getNbObject() { return this.nbObject; };
	public void setNbObject(int nbObject) { this.nbObject = nbObject; };
	public long getPrice() { return this.price; };
	public void setPrice(long price) { this.price = price; };

	public PaddockInformationsForSell(){
	}

	public PaddockInformationsForSell(String guildOwner, int worldX, int worldY, int subAreaId, int nbMount, int nbObject, long price){
		this.guildOwner = guildOwner;
		this.worldX = worldX;
		this.worldY = worldY;
		this.subAreaId = subAreaId;
		this.nbMount = nbMount;
		this.nbObject = nbObject;
		this.price = price;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeUTF(this.guildOwner);
			writer.writeShort(this.worldX);
			writer.writeShort(this.worldY);
			writer.writeVarShort(this.subAreaId);
			writer.writeByte(this.nbMount);
			writer.writeByte(this.nbObject);
			writer.writeVarLong(this.price);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.guildOwner = reader.readUTF();
			this.worldX = reader.readShort();
			this.worldY = reader.readShort();
			this.subAreaId = reader.readVarShort();
			this.nbMount = reader.readByte();
			this.nbObject = reader.readByte();
			this.price = reader.readVarLong();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
