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
import protocol.network.types.game.context.roleplay.GroupMonsterStaticInformations;
import protocol.network.types.game.context.roleplay.AlternativeMonstersInGroupLightInformations;

@SuppressWarnings("unused")
public class GroupMonsterStaticInformationsWithAlternatives extends GroupMonsterStaticInformations {
	public static final int ProtocolId = 396;

	private List<AlternativeMonstersInGroupLightInformations> alternatives;

	public List<AlternativeMonstersInGroupLightInformations> getAlternatives() { return this.alternatives; }
	public void setAlternatives(List<AlternativeMonstersInGroupLightInformations> alternatives) { this.alternatives = alternatives; };

	public GroupMonsterStaticInformationsWithAlternatives(){
	}

	public GroupMonsterStaticInformationsWithAlternatives(List<AlternativeMonstersInGroupLightInformations> alternatives){
		this.alternatives = alternatives;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeShort(this.alternatives.size());
			int _loc2_ = 0;
			while( _loc2_ < this.alternatives.size()){
				this.alternatives.get(_loc2_).Serialize(writer);
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
			this.alternatives = new ArrayList<AlternativeMonstersInGroupLightInformations>();
			while( _loc3_ <  _loc2_){
				AlternativeMonstersInGroupLightInformations _loc15_ = new AlternativeMonstersInGroupLightInformations();
				_loc15_.Deserialize(reader);
				this.alternatives.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
