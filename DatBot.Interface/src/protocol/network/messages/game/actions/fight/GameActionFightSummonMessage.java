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
import protocol.network.types.game.context.fight.GameFightFighterInformations;

@SuppressWarnings("unused")
public class GameActionFightSummonMessage extends AbstractGameActionMessage {
	public static final int ProtocolId = 5825;

	private List<GameFightFighterInformations> summons;

	public List<GameFightFighterInformations> getSummons() { return this.summons; };
	public void setSummons(List<GameFightFighterInformations> summons) { this.summons = summons; };

	public GameActionFightSummonMessage(){
	}

	public GameActionFightSummonMessage(List<GameFightFighterInformations> summons){
		this.summons = summons;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.summons.size());
			int _loc2_ = 0;
			while( _loc2_ < this.summons.size()){
				writer.writeShort(GameFightFighterInformations.ProtocolId);
				this.summons.get(_loc2_).Serialize(writer);
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
			this.summons = new ArrayList<GameFightFighterInformations>();
			while( _loc3_ <  _loc2_){
				GameFightFighterInformations _loc15_ = (GameFightFighterInformations) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.summons.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
