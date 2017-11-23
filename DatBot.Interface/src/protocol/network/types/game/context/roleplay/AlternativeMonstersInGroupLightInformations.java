package protocol.network.types.game.context.roleplay;

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
import protocol.network.types.game.context.roleplay.MonsterInGroupLightInformations;

@SuppressWarnings("unused")
public class AlternativeMonstersInGroupLightInformations extends NetworkMessage {
	public static final int ProtocolId = 394;

	public int playerCount;
	public List<MonsterInGroupLightInformations> monsters;

	public AlternativeMonstersInGroupLightInformations(){
	}

	public AlternativeMonstersInGroupLightInformations(int playerCount, List<MonsterInGroupLightInformations> monsters){
		this.playerCount = playerCount;
		this.monsters = monsters;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.playerCount);
			writer.writeShort(this.monsters.size());
			int _loc2_ = 0;
			while( _loc2_ < this.monsters.size()){
				this.monsters.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.playerCount = reader.readInt();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.monsters = new ArrayList<MonsterInGroupLightInformations>();
			while( _loc3_ <  _loc2_){
				MonsterInGroupLightInformations _loc15_ = new MonsterInGroupLightInformations();
				_loc15_.Deserialize(reader);
				this.monsters.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("playerCount : " + this.playerCount);
		//for(MonsterInGroupLightInformations a : monsters) {
			//Network.appendDebug("monsters : " + a);
		//}
	//}
}
