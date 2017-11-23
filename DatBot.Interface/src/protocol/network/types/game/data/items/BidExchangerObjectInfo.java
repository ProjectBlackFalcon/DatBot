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
import protocol.network.types.game.data.items.effects.ObjectEffect;

@SuppressWarnings("unused")
public class BidExchangerObjectInfo extends NetworkMessage {
	public static final int ProtocolId = 122;

	public int objectUID;
	public List<ObjectEffect> effects;
	public List<Long> prices;

	public BidExchangerObjectInfo(){
	}

	public BidExchangerObjectInfo(int objectUID, List<ObjectEffect> effects, List<Long> prices){
		this.objectUID = objectUID;
		this.effects = effects;
		this.prices = prices;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.objectUID);
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
			this.objectUID = reader.readVarInt();
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
		//append();
	}

	//private void append(){
		//Network.appendDebug("objectUID : " + this.objectUID);
		//for(ObjectEffect a : effects) {
			//Network.appendDebug("effects : " + a);
		//}
		//for(Long a : prices) {
			//Network.appendDebug("prices : " + a);
		//}
	//}
}
