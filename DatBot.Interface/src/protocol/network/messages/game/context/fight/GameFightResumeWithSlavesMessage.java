package protocol.network.messages.game.context.fight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.context.fight.GameFightResumeMessage;
import protocol.network.types.game.context.fight.GameFightResumeSlaveInfo;

@SuppressWarnings("unused")
public class GameFightResumeWithSlavesMessage extends GameFightResumeMessage {
	public static final int ProtocolId = 6215;

	public List<GameFightResumeSlaveInfo> slavesInfo;

	public GameFightResumeWithSlavesMessage(){
	}

	public GameFightResumeWithSlavesMessage(List<GameFightResumeSlaveInfo> slavesInfo){
		this.slavesInfo = slavesInfo;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.slavesInfo.size());
			int _loc2_ = 0;
			while( _loc2_ < this.slavesInfo.size()){
				this.slavesInfo.get(_loc2_).Serialize(writer);
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
			this.slavesInfo = new ArrayList<GameFightResumeSlaveInfo>();
			while( _loc3_ <  _loc2_){
				GameFightResumeSlaveInfo _loc15_ = new GameFightResumeSlaveInfo();
				_loc15_.Deserialize(reader);
				this.slavesInfo.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(GameFightResumeSlaveInfo a : slavesInfo) {
			//Network.appendDebug("slavesInfo : " + a);
		//}
	//}
}
