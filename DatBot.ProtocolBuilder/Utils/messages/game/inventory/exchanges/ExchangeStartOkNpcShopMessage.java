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
import protocol.network.types.game.data.items.ObjectItemToSellInNpcShop;

@SuppressWarnings("unused")
public class ExchangeStartOkNpcShopMessage extends NetworkMessage {
	public static final int ProtocolId = 5761;

	private double npcSellerId;
	private int tokenId;
	private List<ObjectItemToSellInNpcShop> objectsInfos;

	public double getNpcSellerId() { return this.npcSellerId; }
	public void setNpcSellerId(double npcSellerId) { this.npcSellerId = npcSellerId; };
	public int getTokenId() { return this.tokenId; }
	public void setTokenId(int tokenId) { this.tokenId = tokenId; };
	public List<ObjectItemToSellInNpcShop> getObjectsInfos() { return this.objectsInfos; }
	public void setObjectsInfos(List<ObjectItemToSellInNpcShop> objectsInfos) { this.objectsInfos = objectsInfos; };

	public ExchangeStartOkNpcShopMessage(){
	}

	public ExchangeStartOkNpcShopMessage(double npcSellerId, int tokenId, List<ObjectItemToSellInNpcShop> objectsInfos){
		this.npcSellerId = npcSellerId;
		this.tokenId = tokenId;
		this.objectsInfos = objectsInfos;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeDouble(this.npcSellerId);
			writer.writeVarShort(this.tokenId);
			writer.writeShort(this.objectsInfos.size());
			int _loc2_ = 0;
			while( _loc2_ < this.objectsInfos.size()){
				this.objectsInfos.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.npcSellerId = reader.readDouble();
			this.tokenId = reader.readVarShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.objectsInfos = new ArrayList<ObjectItemToSellInNpcShop>();
			while( _loc3_ <  _loc2_){
				ObjectItemToSellInNpcShop _loc15_ = new ObjectItemToSellInNpcShop();
				_loc15_.Deserialize(reader);
				this.objectsInfos.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
