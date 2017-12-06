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

	public List<Integer> quantities;
	public List<Integer> types;
	public double taxPercentage;
	public double taxModificationPercentage;
	public int maxItemLevel;
	public int maxItemPerAccount;
	public int npcContextualId;
	public int unsoldDelay;

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
			this.taxPercentage = reader.readDouble();
			this.taxModificationPercentage = reader.readDouble();
			this.maxItemLevel = reader.readByte();
			this.maxItemPerAccount = reader.readVarInt();
			this.npcContextualId = reader.readInt();
			this.unsoldDelay = reader.readVarShort();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//for(Integer a : quantities) {
			//Network.appendDebug("quantities : " + a);
		//}
		//for(Integer a : types) {
			//Network.appendDebug("types : " + a);
		//}
		//Network.appendDebug("taxPercentage : " + this.taxPercentage);
		//Network.appendDebug("taxModificationPercentage : " + this.taxModificationPercentage);
		//Network.appendDebug("maxItemLevel : " + this.maxItemLevel);
		//Network.appendDebug("maxItemPerAccount : " + this.maxItemPerAccount);
		//Network.appendDebug("npcContextualId : " + this.npcContextualId);
		//Network.appendDebug("unsoldDelay : " + this.unsoldDelay);
	//}
}
