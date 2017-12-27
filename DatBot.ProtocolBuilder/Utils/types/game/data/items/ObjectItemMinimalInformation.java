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
public class ObjectItemMinimalInformation extends Item {
	public static final int ProtocolId = 124;

	private int objectGID;
	private List<ObjectEffect> effects;

	public int getObjectGID() { return this.objectGID; };
	public void setObjectGID(int objectGID) { this.objectGID = objectGID; };
	public List<ObjectEffect> getEffects() { return this.effects; };
	public void setEffects(List<ObjectEffect> effects) { this.effects = effects; };

	public ObjectItemMinimalInformation(){
	}

	public ObjectItemMinimalInformation(int objectGID, List<ObjectEffect> effects){
		this.objectGID = objectGID;
		this.effects = effects;
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
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
