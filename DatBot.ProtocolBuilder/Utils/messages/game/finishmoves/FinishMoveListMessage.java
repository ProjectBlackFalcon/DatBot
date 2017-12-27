package protocol.network.messages.game.finishmoves;

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
import protocol.network.types.game.finishmoves.FinishMoveInformations;

@SuppressWarnings("unused")
public class FinishMoveListMessage extends NetworkMessage {
	public static final int ProtocolId = 6704;

	private List<FinishMoveInformations> finishMoves;

	public List<FinishMoveInformations> getFinishMoves() { return this.finishMoves; };
	public void setFinishMoves(List<FinishMoveInformations> finishMoves) { this.finishMoves = finishMoves; };

	public FinishMoveListMessage(){
	}

	public FinishMoveListMessage(List<FinishMoveInformations> finishMoves){
		this.finishMoves = finishMoves;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeShort(this.finishMoves.size());
			int _loc2_ = 0;
			while( _loc2_ < this.finishMoves.size()){
				this.finishMoves.get(_loc2_).Serialize(writer);
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
			this.finishMoves = new ArrayList<FinishMoveInformations>();
			while( _loc3_ <  _loc2_){
				FinishMoveInformations _loc15_ = new FinishMoveInformations();
				_loc15_.Deserialize(reader);
				this.finishMoves.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
