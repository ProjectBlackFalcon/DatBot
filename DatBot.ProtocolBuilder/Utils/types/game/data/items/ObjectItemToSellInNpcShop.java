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
import protocol.network.types.game.data.items.ObjectItemMinimalInformation;

@SuppressWarnings("unused")
public class ObjectItemToSellInNpcShop extends ObjectItemMinimalInformation {
	public static final int ProtocolId = 352;

	private long objectPrice;
	private String buyCriterion;

	public long getObjectPrice() { return this.objectPrice; }
	public void setObjectPrice(long objectPrice) { this.objectPrice = objectPrice; };
	public String getBuyCriterion() { return this.buyCriterion; }
	public void setBuyCriterion(String buyCriterion) { this.buyCriterion = buyCriterion; };

	public ObjectItemToSellInNpcShop(){
	}

	public ObjectItemToSellInNpcShop(long objectPrice, String buyCriterion){
		this.objectPrice = objectPrice;
		this.buyCriterion = buyCriterion;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarLong(this.objectPrice);
			writer.writeUTF(this.buyCriterion);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.objectPrice = reader.readVarLong();
			this.buyCriterion = reader.readUTF();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
