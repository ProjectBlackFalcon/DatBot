package protocol.network.types.game.data.items;

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
public class SellerBuyerDescriptor extends NetworkMessage {
	public static final int ProtocolId = 121;

	private List<Integer> quantities;
	private List<Integer> types;
	private double taxPercentage;
	private double taxModificationPercentage;
	private int maxItemLevel;
	private int maxItemPerAccount;
	private int npcContextualId;
	private int unsoldDelay;

	public List<Integer> getQuantities() { return this.quantities; };
	public void setQuantities(List<Integer> quantities) { this.quantities = quantities; };
	public List<Integer> getTypes() { return this.types; };
	public void setTypes(List<Integer> types) { this.types = types; };
	public double getTaxPercentage() { return this.taxPercentage; };
	public void setTaxPercentage(double taxPercentage) { this.taxPercentage = taxPercentage; };
	public double getTaxModificationPercentage() { return this.taxModificationPercentage; };
	public void setTaxModificationPercentage(double taxModificationPercentage) { this.taxModificationPercentage = taxModificationPercentage; };
	public int getMaxItemLevel() { return this.maxItemLevel; };
	public void setMaxItemLevel(int maxItemLevel) { this.maxItemLevel = maxItemLevel; };
	public int getMaxItemPerAccount() { return this.maxItemPerAccount; };
	public void setMaxItemPerAccount(int maxItemPerAccount) { this.maxItemPerAccount = maxItemPerAccount; };
	public int getNpcContextualId() { return this.npcContextualId; };
	public void setNpcContextualId(int npcContextualId) { this.npcContextualId = npcContextualId; };
	public int getUnsoldDelay() { return this.unsoldDelay; };
	public void setUnsoldDelay(int unsoldDelay) { this.unsoldDelay = unsoldDelay; };

	public SellerBuyerDescriptor(){
	}

	public SellerBuyerDescriptor(List<Integer> quantities, List<Integer> types, double taxPercentage, double taxModificationPercentage, int maxItemLevel, int maxItemPerAccount, int npcContextualId, int unsoldDelay){
		this.quantities = quantities;
		this.types = types;
		this.taxPercentage = taxPercentage;
		this.taxModificationPercentage = taxModificationPercentage;
		this.maxItemLevel = maxItemLevel;
		this.maxItemPerAccount = maxItemPerAccount;
		this.npcContextualId = npcContextualId;
		this.unsoldDelay = unsoldDelay;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.quantities.size());
			int _loc2_ = 0;
			while( _loc2_ < this.quantities.size()){
				writer.writeVarInt(this.quantities.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.types.size());
			int _loc3_ = 0;
			while( _loc3_ < this.types.size()){
				writer.writeVarInt(this.types.get(_loc3_));
				_loc3_++;
			}
			writer.writeDouble(this.taxPercentage);
			writer.writeDouble(this.taxModificationPercentage);
			writer.writeByte(this.maxItemLevel);
			writer.writeVarInt(this.maxItemPerAccount);
			writer.writeInt(this.npcContextualId);
			writer.writeVarShort(this.unsoldDelay);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.quantities = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarInt();
				this.quantities.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.types = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readVarInt();
				this.types.add(_loc16_);

				_loc5_++;
			}
			this.taxPercentage = reader.readFloat(); 
			this.taxModificationPercentage = reader.readFloat(); 
			this.maxItemLevel = reader.readByte(); 	
			this.maxItemPerAccount = reader.readVarInt(); 
			this.npcContextualId = reader.readInt(); 
			this.unsoldDelay = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
