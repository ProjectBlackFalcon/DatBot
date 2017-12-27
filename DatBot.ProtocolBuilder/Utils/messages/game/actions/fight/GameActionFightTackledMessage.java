package protocol.network.messages.game.actions.fight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.messages.game.actions.AbstractGameActionMessage;

@SuppressWarnings("unused")
public class GameActionFightTackledMessage extends AbstractGameActionMessage {
	public static final int ProtocolId = 1004;

	private List<Double> tacklersIds;

	public List<Double> getTacklersIds() { return this.tacklersIds; };
	public void setTacklersIds(List<Double> tacklersIds) { this.tacklersIds = tacklersIds; };

	public GameActionFightTackledMessage(){
	}

	public GameActionFightTackledMessage(List<Double> tacklersIds){
		this.tacklersIds = tacklersIds;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.tacklersIds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.tacklersIds.size()){
				writer.writeDouble(this.tacklersIds.get(_loc2_));
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
			this.tacklersIds = new ArrayList<Double>();
			while( _loc3_ <  _loc2_){
				double _loc15_ = reader.readDouble();
				this.tacklersIds.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
