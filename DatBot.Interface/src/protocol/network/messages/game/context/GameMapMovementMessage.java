package protocol.network.messages.game.context;

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
public class GameMapMovementMessage extends NetworkMessage {
	public static final int ProtocolId = 951;

	public List<Integer> keyMovements;
	public int forcedDirection;
	public double actorId;

	public GameMapMovementMessage(){
	}

	public GameMapMovementMessage(List<Integer> keyMovements, int forcedDirection, double actorId){
		this.keyMovements = keyMovements;
		this.forcedDirection = forcedDirection;
		this.actorId = actorId;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.keyMovements.size());
			int _loc2_ = 0;
			while( _loc2_ < this.keyMovements.size()){
				writer.writeShort(this.keyMovements.get(_loc2_));
				_loc2_++;
			}
			writer.writeShort(this.forcedDirection);
			writer.writeDouble(this.actorId);
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.keyMovements = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readShort();
				this.keyMovements.add(_loc15_);
				_loc3_++;
			}
			this.forcedDirection = reader.readShort();
			this.actorId = reader.readDouble();
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(Integer a : keyMovements) {
			//Network.appendDebug("keyMovements : " + a);
		//}
		//Network.appendDebug("forcedDirection : " + this.forcedDirection);
		//Network.appendDebug("actorId : " + this.actorId);
	//}
}
