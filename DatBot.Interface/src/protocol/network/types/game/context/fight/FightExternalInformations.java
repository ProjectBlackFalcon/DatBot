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
import protocol.network.types.game.context.fight.FightTeamLightInformations;
import protocol.network.types.game.context.fight.FightOptionsInformations;

@SuppressWarnings("unused")
public class FightExternalInformations extends NetworkMessage {
	public static final int ProtocolId = 117;

	private int fightId;
	private int fightType;
	private int fightStart;
	private boolean fightSpectatorLocked;
	private List<FightTeamLightInformations> fightTeams;
	private List<FightOptionsInformations> fightTeamsOptions;

	public int getFightId() { return this.fightId; }
	public void setFightId(int fightId) { this.fightId = fightId; };
	public int getFightType() { return this.fightType; }
	public void setFightType(int fightType) { this.fightType = fightType; };
	public int getFightStart() { return this.fightStart; }
	public void setFightStart(int fightStart) { this.fightStart = fightStart; };
	public boolean isFightSpectatorLocked() { return this.fightSpectatorLocked; }
	public void setFightSpectatorLocked(boolean fightSpectatorLocked) { this.fightSpectatorLocked = fightSpectatorLocked; };
	public List<FightTeamLightInformations> getFightTeams() { return this.fightTeams; }
	public void setFightTeams(List<FightTeamLightInformations> fightTeams) { this.fightTeams = fightTeams; };
	public List<FightOptionsInformations> getFightTeamsOptions() { return this.fightTeamsOptions; }
	public void setFightTeamsOptions(List<FightOptionsInformations> fightTeamsOptions) { this.fightTeamsOptions = fightTeamsOptions; };

	public FightExternalInformations(){
	}

	public FightExternalInformations(int fightId, int fightType, int fightStart, boolean fightSpectatorLocked, List<FightTeamLightInformations> fightTeams, List<FightOptionsInformations> fightTeamsOptions){
		this.fightId = fightId;
		this.fightType = fightType;
		this.fightStart = fightStart;
		this.fightSpectatorLocked = fightSpectatorLocked;
		this.fightTeams = fightTeams;
		this.fightTeamsOptions = fightTeamsOptions;
	}

	@Override
	public void Serialize(DofusDataWriter writer) {
		try {
			writer.writeVarShort(this.fightId);
			writer.writeByte(this.fightType);
			writer.writeInt(this.fightStart);
			writer.writeBoolean(this.fightSpectatorLocked);
			int _loc2_ = 0;
			while( _loc2_ < 2){
				this.fightTeams.get(_loc2_).Serialize(writer);
				_loc2_++;
			}
			int _loc3_ = 0;
			while( _loc3_ < 2){
				this.fightTeamsOptions.get(_loc3_).Serialize(writer);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public void Deserialize(DofusDataReader reader) {
		try {
			this.fightId = reader.readVarShort();
			this.fightType = reader.readByte();
			this.fightStart = reader.readInt();
			this.fightSpectatorLocked = reader.readBoolean();
			int _loc2_  = 0;
			this.fightTeams = new ArrayList<FightTeamLightInformations>();
			while( _loc2_ < 2){
				this.fightTeams.add(new FightTeamLightInformations());
				this.fightTeams.get( _loc2_).Deserialize(reader);
				_loc2_++;
			}
			int _loc3_  = 0;
			this.fightTeamsOptions = new ArrayList<FightOptionsInformations>();
			while( _loc3_ < 2){
				this.fightTeamsOptions.add(new FightOptionsInformations());
				this.fightTeamsOptions.get( _loc3_).Deserialize(reader);
				_loc3_++;
			}
		} catch (Exception e){
			e.printStackTrace();
		}
	}

}
