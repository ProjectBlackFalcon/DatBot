package protocol.network.messages.game.startup;

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
import protocol.network.types.game.startup.StartupActionAddObject;

@SuppressWarnings("unused")
public class StartupActionsListMessage extends NetworkMessage {
	public static final int ProtocolId = 1301;

	public List<StartupActionAddObject> actions;

	public StartupActionsListMessage(){
	}

	public StartupActionsListMessage(List<StartupActionAddObject> actions){
		this.actions = actions;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.actions.size());
			int _loc2_ = 0;
			while( _loc2_ < this.actions.size()){
				this.actions.get(_loc2_).Serialize(writer);
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
			this.actions = new ArrayList<StartupActionAddObject>();
			while( _loc3_ <  _loc2_){
				StartupActionAddObject _loc15_ = new StartupActionAddObject();
				_loc15_.Deserialize(reader);
				this.actions.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(StartupActionAddObject a : actions) {
			//Network.appendDebug("actions : " + a);
		//}
	//}
}
