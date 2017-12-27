package protocol.network.messages.game.inventory.exchanges;

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
import protocol.network.types.game.data.items.effects.ObjectEffect;

@SuppressWarnings("unused")
public class ExchangeBidHouseInListAddedMessage extends NetworkMessage {
	public static final int ProtocolId = 5949;

	private int itemUID;
	private int objGenericId;
	private List<ObjectEffect> effects;
	private List<Long> prices;

	public int getItemUID() { return this.itemUID; };
	public void setItemUID(int itemUID) { this.itemUID = itemUID; };
	public int getObjGenericId() { return this.objGenericId; };
	public void setObjGenericId(int objGenericId) { this.objGenericId = objGenericId; };
	public List<ObjectEffect> getEffects() { return this.effects; };
	public void setEffects(List<ObjectEffect> effects) { this.effects = effects; };
	public List<Long> getPrices() { return this.prices; };
	public void setPrices(List<Long> prices) { this.prices = prices; };

	public ExchangeBidHouseInListAddedMessage(){
	}

	public ExchangeBidHouseInListAddedMessage(int itemUID, int objGenericId, List<ObjectEffect> effects, List<Long> prices){
		this.itemUID = itemUID;
		this.objGenericId = objGenericId;
		this.effects = effects;
		this.prices = prices;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.itemUID);
			writer.writeInt(this.objGenericId);
			writer.writeShort(this.effects.size());
			int _loc2_ = 0;
			while( _loc2_ < this.effects.size()){
				writer.writeShort(ObjectEffect.ProtocolId);
				this.effects.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeShort(this.prices.size());
			int _loc3_ = 0;
			while( _loc3_ < this.prices.size()){
				writer.writeVarLong(this.prices.get(_loc3_));
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.itemUID = reader.readInt();
			this.objGenericId = reader.readInt();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.effects = new ArrayList<ObjectEffect>();
			while( _loc3_ <  _loc2_){
				ObjectEffect _loc15_ = (ObjectEffect) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.effects.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.prices = new ArrayList<Long>();
			while( _loc5_ <  _loc4_){
				long _loc16_ = reader.readVarLong();
				this.prices.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
