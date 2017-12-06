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
import protocol.network.types.game.data.items.Item;
import protocol.network.types.game.data.items.effects.ObjectEffect;

@SuppressWarnings("unused")
public class ObjectItemNotInContainer extends Item {
	public static final int ProtocolId = 134;

	public int objectGID;
	public List<ObjectEffect> effects;
	public int objectUID;
	public int quantity;

	public ObjectItemNotInContainer(){
	}

	public ObjectItemNotInContainer(int objectGID, List<ObjectEffect> effects, int objectUID, int quantity){
		this.objectGID = objectGID;
		this.effects = effects;
		this.objectUID = objectUID;
		this.quantity = quantity;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.objectGID);
			writer.writeShort(this.effects.size());
			int _loc2_ = 0;
			while( _loc2_ < this.effects.size()){
				writer.writeShort(ObjectEffect.ProtocolId);
				this.effects.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeVarInt(this.objectUID);
			writer.writeVarInt(this.quantity);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			super.Deserialize(reader);
			this.objectGID = reader.readVarShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.effects = new ArrayList<ObjectEffect>();
			while( _loc3_ <  _loc2_){
				ObjectEffect _loc15_ = (ObjectEffect) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.effects.add(_loc15_);
				_loc3_++;
			}
			this.objectUID = reader.readVarInt();
			this.quantity = reader.readVarInt();
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	//private void append(){
		//Network.appendDebug("objectGID : " + this.objectGID);
		//for(ObjectEffect a : effects) {
			//Network.appendDebug("effects : " + a);
		//}
		//Network.appendDebug("objectUID : " + this.objectUID);
		//Network.appendDebug("quantity : " + this.quantity);
	//}
}
