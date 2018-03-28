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
import protocol.network.types.game.context.roleplay.GameRolePlayGroupMonsterInformations;
import protocol.network.types.game.context.roleplay.GroupMonsterStaticInformations;

@SuppressWarnings("unused")
public class GameRolePlayGroupMonsterWaveInformations extends GameRolePlayGroupMonsterInformations {
	public static final int ProtocolId = 464;

	private int nbWaves;
	private List<GroupMonsterStaticInformations> alternatives;

	public int getNbWaves() { return this.nbWaves; }
	public void setNbWaves(int nbWaves) { this.nbWaves = nbWaves; };
	public List<GroupMonsterStaticInformations> getAlternatives() { return this.alternatives; }
	public void setAlternatives(List<GroupMonsterStaticInformations> alternatives) { this.alternatives = alternatives; };

	public GameRolePlayGroupMonsterWaveInformations(){
	}

	public GameRolePlayGroupMonsterWaveInformations(int nbWaves, List<GroupMonsterStaticInformations> alternatives){
		this.nbWaves = nbWaves;
		this.alternatives = alternatives;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			super.Serialize(writer);
			writer.writeByte(this.nbWaves);
			writer.writeShort(this.alternatives.size());
			int _loc2_ = 0;
			while( _loc2_ < this.alternatives.size()){
				writer.writeShort(GroupMonsterStaticInformations.ProtocolId);
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
			this.nbWaves = reader.readByte();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.alternatives = new ArrayList<GroupMonsterStaticInformations>();
			while( _loc3_ <  _loc2_){
				GroupMonsterStaticInformations _loc15_ = (GroupMonsterStaticInformations) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.alternatives.add(_loc15_);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
