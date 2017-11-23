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
import protocol.network.types.game.context.EntityMovementInformations;

@SuppressWarnings("unused")
public class GameContextMoveMultipleElementsMessage extends NetworkMessage {
	public static final int ProtocolId = 254;

	public List<EntityMovementInformations> movements;

	public GameContextMoveMultipleElementsMessage(){
	}

	public GameContextMoveMultipleElementsMessage(List<EntityMovementInformations> movements){
		this.movements = movements;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.movements.size());
			int _loc2_ = 0;
			while( _loc2_ < this.movements.size()){
				this.movements.get(_loc2_).Serialize(writer);
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
			this.movements = new ArrayList<EntityMovementInformations>();
			while( _loc3_ <  _loc2_){
				EntityMovementInformations _loc15_ = new EntityMovementInformations();
				_loc15_.Deserialize(reader);
				this.movements.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//for(EntityMovementInformations a : movements) {
			//Network.appendDebug("movements : " + a);
		//}
	//}
}
