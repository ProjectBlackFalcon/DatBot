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
import protocol.network.NetworkMessage;
import protocol.network.types.game.context.fight.FightTeamInformations;
import protocol.network.types.game.context.fight.FightOptionsInformations;

@SuppressWarnings("unused")
public class FightCommonInformations extends NetworkMessage {
	public static final int ProtocolId = 43;

	public int fightId;
	public int fightType;
	public List<FightTeamInformations> fightTeams;
	public List<Integer> fightTeamsPositions;
	public List<FightOptionsInformations> fightTeamsOptions;

	public FightCommonInformations(){
	}

	public FightCommonInformations(int fightId, int fightType, List<FightTeamInformations> fightTeams, List<Integer> fightTeamsPositions, List<FightOptionsInformations> fightTeamsOptions){
		this.fightId = fightId;
		this.fightType = fightType;
		this.fightTeams = fightTeams;
		this.fightTeamsPositions = fightTeamsPositions;
		this.fightTeamsOptions = fightTeamsOptions;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeInt(this.fightId);
			writer.writeByte(this.fightType);
			writer.writeShort(this.fightTeams.size());
			int _loc2_ = 0;
			while( _loc2_ < this.fightTeams.size()){
				writer.writeShort(FightTeamInformations.ProtocolId);
				this.fightTeams.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			writer.writeShort(this.fightTeamsPositions.size());
			int _loc3_ = 0;
			while( _loc3_ < this.fightTeamsPositions.size()){
				writer.writeVarShort(this.fightTeamsPositions.get(_loc3_));
				_loc3_++;
			}
			writer.writeShort(this.fightTeamsOptions.size());
			int _loc4_ = 0;
			while( _loc4_ < this.fightTeamsOptions.size()){
				this.fightTeamsOptions.get(_loc4_).Serialize(writer);
				_loc4_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.fightId = reader.readInt();
			this.fightType = reader.readByte();
			int _loc2_  = reader.readShort();
			int _loc3_  = 0;
			this.fightTeams = new ArrayList<FightTeamInformations>();
			while( _loc3_ <  _loc2_){
				FightTeamInformations _loc15_ = (FightTeamInformations) ProtocolTypeManager.getInstance(reader.readShort());
				_loc15_.Deserialize(reader);
				this.fightTeams.add(_loc15_);
				_loc3_++;
			}
			int _loc4_  = reader.readShort();
			int _loc5_  = 0;
			this.fightTeamsPositions = new ArrayList<Integer>();
			while( _loc5_ <  _loc4_){
				int _loc16_ = reader.readVarShort();
				this.fightTeamsPositions.add(_loc16_);
				_loc5_++;
			}
			int _loc6_  = reader.readShort();
			int _loc7_  = 0;
			this.fightTeamsOptions = new ArrayList<FightOptionsInformations>();
			while( _loc7_ <  _loc6_){
				FightOptionsInformations _loc17_ = new FightOptionsInformations();
				_loc17_.Deserialize(reader);
				this.fightTeamsOptions.add(_loc17_);
				_loc7_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
		//append();
	}

	//private void append(){
		//Network.appendDebug("fightId : " + this.fightId);
		//Network.appendDebug("fightType : " + this.fightType);
		//for(FightTeamInformations a : fightTeams) {
			//Network.appendDebug("fightTeams : " + a);
		//}
		//for(Integer a : fightTeamsPositions) {
			//Network.appendDebug("fightTeamsPositions : " + a);
		//}
		//for(FightOptionsInformations a : fightTeamsOptions) {
			//Network.appendDebug("fightTeamsOptions : " + a);
		//}
	//}
}
