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
import protocol.network.types.game.context.roleplay.MonsterInGroupInformations;

@SuppressWarnings("unused")
public class GroupMonsterStaticInformations extends NetworkMessage {
	public static final int ProtocolId = 140;

	private MonsterInGroupLightInformations mainCreatureLightInfos;
	private List<MonsterInGroupInformations> underlings;

	public MonsterInGroupLightInformations getMainCreatureLightInfos() { return this.mainCreatureLightInfos; }
	public void setMainCreatureLightInfos(MonsterInGroupLightInformations mainCreatureLightInfos) { this.mainCreatureLightInfos = mainCreatureLightInfos; };
	public List<MonsterInGroupInformations> getUnderlings() { return this.underlings; }
	public void setUnderlings(List<MonsterInGroupInformations> underlings) { this.underlings = underlings; };

	public GroupMonsterStaticInformations(){
	}

	public GroupMonsterStaticInformations(MonsterInGroupLightInformations mainCreatureLightInfos, List<MonsterInGroupInformations> underlings){
		this.mainCreatureLightInfos = mainCreatureLightInfos;
		this.underlings = underlings;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			mainCreatureLightInfos.Serialize(writer);
			writer.writeShort(this.underlings.size());
			int _loc2_ = 0;
			while( _loc2_ < this.underlings.size()){
				this.underlings.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.mainCreatureLightInfos = new MonsterInGroupLightInformations();
			this.mainCreatureLightInfos.Deserialize(reader);
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.underlings = new ArrayList<MonsterInGroupInformations>();
			while( _loc3_ <  _loc2_){
				MonsterInGroupInformations _loc15_ = new MonsterInGroupInformations();
				_loc15_.Deserialize(reader);
				this.underlings.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
