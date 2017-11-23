package protocol.network.types.game.look;

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
import protocol.network.types.game.look.SubEntity;

@SuppressWarnings("unused")
public class EntityLook extends NetworkMessage {
	public static final int ProtocolId = 55;

	public int bonesId;
	public List<Integer> skins;
	public List<Integer> indexedColors;
	public List<Integer> scales;
	public List<SubEntity> subentities;

	public EntityLook(){
	}

	public EntityLook(int bonesId, List<Integer> skins, List<Integer> indexedColors, List<Integer> scales, List<SubEntity> subentities){
		this.bonesId = bonesId;
		this.skins = skins;
		this.indexedColors = indexedColors;
		this.scales = scales;
		this.subentities = subentities;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.bonesId);
			writer.writeShort(this.skins.size());
			int _loc2_ = 0;
			while( _loc2_ < this.skins.size()){
				writer.writeVarShort(this.skins.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.indexedColors.size());
			int _loc3_ = 0;
			while( _loc3_ < this.indexedColors.size()){
				writer.writeInt(this.indexedColors.get(_loc3_));
				_loc3_++;
			}
			writer.writeShort(this.scales.size());
			int _loc4_ = 0;
			while( _loc4_ < this.scales.size()){
				writer.writeVarShort(this.scales.get(_loc4_));
				_loc4_++;
			}
			writer.writeShort(this.subentities.size());
			int _loc5_ = 0;
			while( _loc5_ < this.subentities.size()){
				this.subentities.get(_loc5_).Serialize(writer);
				_loc5_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.bonesId = reader.readVarShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.skins = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.skins.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.indexedColors = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readInt();
				this.indexedColors.add(_loc16_);
				_loc5_++;
			}
			int _loc6_  = reader.readShort();
			int _loc7_  = 0;
			this.scales = new ArrayList<Integer>();
			while( _loc7_ <  _loc6_){
				int _loc17_ = reader.readVarShort();
				this.scales.add(_loc17_);
				_loc7_++;
			}
			int _loc8_  = reader.readShort();
			int _loc9_  = 0;
			this.subentities = new ArrayList<SubEntity>();
			while( _loc9_ <  _loc8_){
				SubEntity _loc18_ = new SubEntity();
				_loc18_.Deserialize(reader);
				this.subentities.add(_loc18_);
				_loc9_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("bonesId : " + this.bonesId);
		//for(Integer a : skins) {
			//Network.appendDebug("skins : " + a);
		//}
		//for(Integer a : indexedColors) {
			//Network.appendDebug("indexedColors : " + a);
		//}
		//for(Integer a : scales) {
			//Network.appendDebug("scales : " + a);
		//}
		//for(SubEntity a : subentities) {
			//Network.appendDebug("subentities : " + a);
		//}
	//}
}
