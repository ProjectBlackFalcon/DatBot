package protocol.network.types.game.guild.tax;

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
import protocol.network.types.game.guild.tax.AdditionalTaxCollectorInformations;
import protocol.network.types.game.look.EntityLook;
import protocol.network.types.game.guild.tax.TaxCollectorComplementaryInformations;

@SuppressWarnings("unused")
public class TaxCollectorInformations extends NetworkMessage {
	public static final int ProtocolId = 167;

	private double uniqueId;
	private int firtNameId;
	private int lastNameId;
	private AdditionalTaxCollectorInformations additionalInfos;
	private int worldX;
	private int worldY;
	private int subAreaId;
	private int state;
	private EntityLook look;
	private List<TaxCollectorComplementaryInformations> complements;

	public double getUniqueId() { return this.uniqueId; };
	public void setUniqueId(double uniqueId) { this.uniqueId = uniqueId; };
	public int getFirtNameId() { return this.firtNameId; };
	public void setFirtNameId(int firtNameId) { this.firtNameId = firtNameId; };
	public int getLastNameId() { return this.lastNameId; };
	public void setLastNameId(int lastNameId) { this.lastNameId = lastNameId; };
	public AdditionalTaxCollectorInformations getAdditionalInfos() { return this.additionalInfos; };
	public void setAdditionalInfos(AdditionalTaxCollectorInformations additionalInfos) { this.additionalInfos = additionalInfos; };
	public int getWorldX() { return this.worldX; };
	public void setWorldX(int worldX) { this.worldX = worldX; };
	public int getWorldY() { return this.worldY; };
	public void setWorldY(int worldY) { this.worldY = worldY; };
	public int getSubAreaId() { return this.subAreaId; };
	public void setSubAreaId(int subAreaId) { this.subAreaId = subAreaId; };
	public int getState() { return this.state; };
	public void setState(int state) { this.state = state; };
	public EntityLook getLook() { return this.look; };
	public void setLook(EntityLook look) { this.look = look; };
	public List<TaxCollectorComplementaryInformations> getComplements() { return this.complements; };
	public void setComplements(List<TaxCollectorComplementaryInformations> complements) { this.complements = complements; };

	public TaxCollectorInformations(){
	}

	public TaxCollectorInformations(double uniqueId, int firtNameId, int lastNameId, AdditionalTaxCollectorInformations additionalInfos, int worldX, int worldY, int subAreaId, int state, EntityLook look, List<TaxCollectorComplementaryInformations> complements){
		this.uniqueId = uniqueId;
		this.firtNameId = firtNameId;
		this.lastNameId = lastNameId;
		this.additionalInfos = additionalInfos;
		this.worldX = worldX;
		this.worldY = worldY;
		this.subAreaId = subAreaId;
		this.state = state;
		this.look = look;
		this.complements = complements;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.uniqueId);
			writer.writeVarShort(this.firtNameId);
			writer.writeVarShort(this.lastNameId);
			additionalInfos.Serialize(writer);
			writer.writeShort(this.worldX);
			writer.writeShort(this.worldY);
			writer.writeVarShort(this.subAreaId);
			writer.writeByte(this.state);
			look.Serialize(writer);
			writer.writeShort(this.complements.size());
			int _loc2_ = 0;
			while( _loc2_ < this.complements.size()){
				writer.writeShort(TaxCollectorComplementaryInformations.ProtocolId);
				this.complements.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.uniqueId = reader.readDouble();
			this.firtNameId = reader.readVarShort();
			this.lastNameId = reader.readVarShort();
			this.additionalInfos = new AdditionalTaxCollectorInformations();
			this.additionalInfos.Deserialize(reader);
			this.worldX = reader.readShort();
			this.worldY = reader.readShort();
			this.subAreaId = reader.readVarShort();
			this.state = reader.readByte();
			this.look = new EntityLook();
			this.look.Deserialize(reader);
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.complements = new ArrayList<TaxCollectorComplementaryInformations>();
			while( _loc3_ <  _loc2_){
				TaxCollectorComplementaryInformations _loc15_ = (TaxCollectorComplementaryInformations) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.complements.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
