package protocol.network.messages.game.inventory.items;

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
public class SetUpdateMessage extends NetworkMessage {
	public static final int ProtocolId = 5503;

	public int setId;
	public List<Integer> setObjects;
	public List<ObjectEffect> setEffects;

	public SetUpdateMessage(){
	}

	public SetUpdateMessage(int setId, List<Integer> setObjects, List<ObjectEffect> setEffects){
		this.setId = setId;
		this.setObjects = setObjects;
		this.setEffects = setEffects;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.setId);
			writer.writeShort(this.setObjects.size());
			int _loc2_ = 0;
			while( _loc2_ < this.setObjects.size()){
				writer.writeVarShort(this.setObjects.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.setEffects.size());
			int _loc3_ = 0;
			while( _loc3_ < this.setEffects.size()){
				writer.writeShort(ObjectEffect.ProtocolId);
				this.setEffects.get(_loc3_).Serialize(writer);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.setId = reader.readVarShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.setObjects = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.setObjects.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.setEffects = new ArrayList<ObjectEffect>();
			while( _loc5_ <  _loc4_){
				ObjectEffect _loc16_ = (ObjectEffect) ProtocolTypeManager.getInstance(reader.readShort());
				_loc16_.Deserialize(reader);
				this.setEffects.add(_loc16_);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("setId : " + this.setId);
		//for(Integer a : setObjects) {
			//Network.appendDebug("setObjects : " + a);
		//}
		//for(ObjectEffect a : setEffects) {
			//Network.appendDebug("setEffects : " + a);
		//}
	//}
}
