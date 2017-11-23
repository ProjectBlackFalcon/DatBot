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

	public double uniqueId;
	public int firtNameId;
	public int lastNameId;
	public AdditionalTaxCollectorInformations additionalInfos;
	public int worldX;
	public int worldY;
	public int subAreaId;
	public int state;
	public EntityLook look;
	public List<TaxCollectorComplementaryInformations> complements;

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
		//append();
	}

	//private void append(){
		//Network.appendDebug("uniqueId : " + this.uniqueId);
		//Network.appendDebug("firtNameId : " + this.firtNameId);
		//Network.appendDebug("lastNameId : " + this.lastNameId);
		//Network.appendDebug("additionalInfos : " + this.additionalInfos);
		//Network.appendDebug("worldX : " + this.worldX);
		//Network.appendDebug("worldY : " + this.worldY);
		//Network.appendDebug("subAreaId : " + this.subAreaId);
		//Network.appendDebug("state : " + this.state);
		//Network.appendDebug("look : " + this.look);
		//for(TaxCollectorComplementaryInformations a : complements) {
			//Network.appendDebug("complements : " + a);
		//}
	//}
}
