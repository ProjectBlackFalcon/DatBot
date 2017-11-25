package protocol.network.types.game.context.fight;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import protocol.utils.ProtocolTypeManager;
import protocol.network.util.types.BooleanByteWrapper;

import protocol.network.NetworkMessage;
import protocol.network.util.DofusDataReader;
import protocol.network.util.DofusDataWriter;
import protocol.network.Network;
import protocol.network.types.game.context.fight.FightResultFighterListEntry;
import protocol.network.types.game.context.fight.FightResultAdditionalData;

@SuppressWarnings("unused")
public class FightResultPlayerListEntry extends FightResultFighterListEntry {
	public static final int ProtocolId = 24;

	public int level;
	public List<FightResultAdditionalData> additional;

	public FightResultPlayerListEntry(){
	}

	public FightResultPlayerListEntry(int level, List<FightResultAdditionalData> additional){
		this.level = level;
		this.additional = additional;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.level);
			writer.writeShort(this.additional.size());
			int _loc2_ = 0;
			while( _loc2_ < this.additional.size()){
				writer.writeShort(FightResultAdditionalData.ProtocolId);
				this.additional.get(_loc2_).Serialize(writer);
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
			this.level = reader.readByte();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.additional = new ArrayList<FightResultAdditionalData>();
			while( _loc3_ <  _loc2_){
				FightResultAdditionalData _loc15_ = (FightResultAdditionalData) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.additional.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("level : " + this.level);
		//for(FightResultAdditionalData a : additional) {
			//Network.appendDebug("additional : " + a);
		//}
	//}
}