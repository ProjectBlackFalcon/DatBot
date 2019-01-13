package protocol.network.types.game.context.roleplay.breach;

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
public class BreachReward extends NetworkMessage {
	public static final int ProtocolId = 559;

	private int id;
	private List<Integer> buyLocks;
	private String buyCriterion;
	private boolean bought;
	private int price;

	public int getId() { return this.id; }
	public void setId(int id) { this.id = id; };
	public List<Integer> getBuyLocks() { return this.buyLocks; }
	public void setBuyLocks(List<Integer> buyLocks) { this.buyLocks = buyLocks; };
	public String getBuyCriterion() { return this.buyCriterion; }
	public void setBuyCriterion(String buyCriterion) { this.buyCriterion = buyCriterion; };
	public boolean isBought() { return this.bought; }
	public void setBought(boolean bought) { this.bought = bought; };
	public int getPrice() { return this.price; }
	public void setPrice(int price) { this.price = price; };

	public BreachReward(){
	}

	public BreachReward(int id, List<Integer> buyLocks, String buyCriterion, boolean bought, int price){
		this.id = id;
		this.buyLocks = buyLocks;
		this.buyCriterion = buyCriterion;
		this.bought = bought;
		this.price = price;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.id);
			writer.writeShort(this.buyLocks.size());
			int _loc2_ = 0;
			while( _loc2_ < this.buyLocks.size()){
				writer.writeByte(this.buyLocks.get(_loc2_));
				_loc2_++;
			}
			writer.writeUTF(this.buyCriterion);
			writer.writeBoolean(this.bought);
			writer.writeVarInt(this.price);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.id = reader.readVarInt();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.buyLocks = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readByte();
				this.buyLocks.add(_loc15_);
				_loc3_++;
			}
			this.buyCriterion = reader.readUTF();
			this.bought = reader.readBoolean();
			this.price = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
