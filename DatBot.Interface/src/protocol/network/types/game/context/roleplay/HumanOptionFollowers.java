package protocol.network.types.game.context.roleplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.roleplay.HumanOption;
import protocol.network.types.game.look.IndexedEntityLook;

@SuppressWarnings("unused")
public class HumanOptionFollowers extends HumanOption {
	public static final int ProtocolId = 410;

	public List<IndexedEntityLook> followingCharactersLook;

	public HumanOptionFollowers(){
	}

	public HumanOptionFollowers(List<IndexedEntityLook> followingCharactersLook){
		this.followingCharactersLook = followingCharactersLook;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.followingCharactersLook.size());
			int _loc2_ = 0;
			while( _loc2_ < this.followingCharactersLook.size()){
				this.followingCharactersLook.get(_loc2_).Serialize(writer);
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
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.followingCharactersLook = new ArrayList<IndexedEntityLook>();
			while( _loc3_ <  _loc2_){
				IndexedEntityLook _loc15_ = new IndexedEntityLook();
				_loc15_.Deserialize(reader);
				this.followingCharactersLook.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(IndexedEntityLook a : followingCharactersLook) {
			//Network.appendDebug("followingCharactersLook : " + a);
		//}
	//}
}
