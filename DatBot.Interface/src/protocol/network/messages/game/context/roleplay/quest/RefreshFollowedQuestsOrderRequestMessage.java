package protocol.network.messages.game.context.roleplay.quest;

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
public class RefreshFollowedQuestsOrderRequestMessage extends NetworkMessage {
	public static final int ProtocolId = 6722;

	public List<Integer> quests;

	public RefreshFollowedQuestsOrderRequestMessage(){
	}

	public RefreshFollowedQuestsOrderRequestMessage(List<Integer> quests){
		this.quests = quests;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.quests.size());
			int _loc2_ = 0;
			while( _loc2_ < this.quests.size()){
				writer.writeVarShort(this.quests.get(_loc2_));
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.quests = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readVarShort();
				this.quests.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(Integer a : quests) {
			//Network.appendDebug("quests : " + a);
		//}
	//}
}
