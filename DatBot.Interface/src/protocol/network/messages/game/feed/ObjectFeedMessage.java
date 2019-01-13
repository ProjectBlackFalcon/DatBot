package protocol.network.messages.game.feed;

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
import protocol.network.types.game.data.items.ObjectItemQuantity;

@SuppressWarnings("unused")
public class ObjectFeedMessage extends NetworkMessage {
	public static final int ProtocolId = 6290;

	private int objectUID;
	private List<ObjectItemQuantity> meal;

	public int getObjectUID() { return this.objectUID; }
	public void setObjectUID(int objectUID) { this.objectUID = objectUID; };
	public List<ObjectItemQuantity> getMeal() { return this.meal; }
	public void setMeal(List<ObjectItemQuantity> meal) { this.meal = meal; };

	public ObjectFeedMessage(){
	}

	public ObjectFeedMessage(int objectUID, List<ObjectItemQuantity> meal){
		this.objectUID = objectUID;
		this.meal = meal;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarInt(this.objectUID);
			writer.writeShort(this.meal.size());
			int _loc2_ = 0;
			while( _loc2_ < this.meal.size()){
				this.meal.get(_loc2_).Serialize(writer);
				_loc2_++;
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
			this.meal = new ArrayList<ObjectItemQuantity>();
			while( _loc3_ <  _loc2_){
				ObjectItemQuantity _loc15_ = new ObjectItemQuantity();
				_loc15_.Deserialize(reader);
				this.meal.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
