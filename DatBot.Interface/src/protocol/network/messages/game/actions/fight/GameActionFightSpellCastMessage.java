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
import protocol.network.messages.game.actions.fight.AbstractGameActionFightTargetedAbilityMessage;

@SuppressWarnings("unused")
public class GameActionFightSpellCastMessage extends AbstractGameActionFightTargetedAbilityMessage {
	public static final int ProtocolId = 1010;

	public int spellId;
	public int spellLevel;
	public List<Integer> portalsIds;

	public GameActionFightSpellCastMessage(){
	}

	public GameActionFightSpellCastMessage(int spellId, int spellLevel, List<Integer> portalsIds){
		this.spellId = spellId;
		this.spellLevel = spellLevel;
		this.portalsIds = portalsIds;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeVarShort(this.spellId);
			writer.writeShort(this.spellLevel);
			writer.writeShort(this.portalsIds.size());
			int _loc2_ = 0;
			while( _loc2_ < this.portalsIds.size()){
				writer.writeShort(this.portalsIds.get(_loc2_));
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
			this.spellId = reader.readVarShort();
			this.spellLevel = reader.readShort();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.portalsIds = new ArrayList<Integer>();
			while( _loc3_ <  _loc2_){
				int _loc15_ = reader.readShort();
				this.portalsIds.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("spellId : " + this.spellId);
		//Network.appendDebug("spellLevel : " + this.spellLevel);
		//for(Integer a : portalsIds) {
			//Network.appendDebug("portalsIds : " + a);
		//}
	//}
}
