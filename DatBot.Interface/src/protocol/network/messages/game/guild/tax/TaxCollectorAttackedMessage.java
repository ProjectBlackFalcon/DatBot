package protocol.network.messages.game.guild.tax;

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
import protocol.network.types.game.context.roleplay.BasicGuildInformations;

@SuppressWarnings("unused")
public class TaxCollectorAttackedMessage extends NetworkMessage {
	public static final int ProtocolId = 5918;

	private int firstNameId;
	private int lastNameId;
	private int worldX;
	private int worldY;
	private double mapId;
	private int subAreaId;
	private BasicGuildInformations guild;

	public int getFirstNameId() { return this.firstNameId; }
	public void setFirstNameId(int firstNameId) { this.firstNameId = firstNameId; };
	public int getLastNameId() { return this.lastNameId; }
	public void setLastNameId(int lastNameId) { this.lastNameId = lastNameId; };
	public int getWorldX() { return this.worldX; }
	public void setWorldX(int worldX) { this.worldX = worldX; };
	public int getWorldY() { return this.worldY; }
	public void setWorldY(int worldY) { this.worldY = worldY; };
	public double getMapId() { return this.mapId; }
	public void setMapId(double mapId) { this.mapId = mapId; };
	public int getSubAreaId() { return this.subAreaId; }
	public void setSubAreaId(int subAreaId) { this.subAreaId = subAreaId; };
	public BasicGuildInformations getGuild() { return this.guild; }
	public void setGuild(BasicGuildInformations guild) { this.guild = guild; };

	public TaxCollectorAttackedMessage(){
	}

	public TaxCollectorAttackedMessage(int firstNameId, int lastNameId, int worldX, int worldY, double mapId, int subAreaId, BasicGuildInformations guild){
		this.firstNameId = firstNameId;
		this.lastNameId = lastNameId;
		this.worldX = worldX;
		this.worldY = worldY;
		this.mapId = mapId;
		this.subAreaId = subAreaId;
		this.guild = guild;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.firstNameId);
			writer.writeVarShort(this.lastNameId);
			writer.writeShort(this.worldX);
			writer.writeShort(this.worldY);
			writer.writeDouble(this.mapId);
			writer.writeVarShort(this.subAreaId);
			guild.Serialize(writer);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.firstNameId = reader.readVarShort();
			this.lastNameId = reader.readVarShort();
			this.worldX = reader.readShort();
			this.worldY = reader.readShort();
			this.mapId = reader.readDouble();
			this.subAreaId = reader.readVarShort();
			this.guild = new BasicGuildInformations();
			this.guild.Deserialize(reader);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
